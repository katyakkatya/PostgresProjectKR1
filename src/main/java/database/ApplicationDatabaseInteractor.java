package database;

import database.model.DbTaskDetail;
import database.model.DbTaskItem;
import database.model.DbTaskStatus;
import database.model.UserWithTaskCount;
import database.request.*;
import database.result.Result;
import jdk.jfr.Unsigned;

import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationDatabaseInteractor implements DatabaseInteractor{

    private Optional<Connection> connection = Optional.empty();

    private Consumer<String> consumerForStatement = System.out::println;
    private Consumer<Exception> consumerForException = System.err::println;

    private Pattern numberPattern = Pattern.compile("\\d+");

    @Override
    public void setConsumers(Consumer<String> consumerForStatement,
                             Consumer<Exception> consumerForException){
        this.consumerForStatement = consumerForStatement;
        this.consumerForException = consumerForException;
    }

    @Override
    public Boolean tryConnect(ConnectionRequest request) { // DONE
        /**
         jdbc:postgresql://localhost:9876/postgres
         postgres
        * */
        if(this.connection.isPresent())
            return true;
        try{
            Connection conn = DriverManager.getConnection(request.url(),
                    request.username(), request.password());

            this.connection = Optional.of(conn);
            return true;
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Boolean databaseExists() { // DONE
        if(!this.isConnected())
            return false;
        Map<String, Boolean> createdTables = new HashMap<>(3);

        createdTables.put("task", false);
        createdTables.put("connected_task", false);
        createdTables.put("users", false);

        try(Statement statement = this.connection.get().createStatement()){
            ResultSet set = statement.executeQuery("SELECT * FROM pg_tables WHERE schemaname = 'public'");
            while(set.next()){
                createdTables.put(set.getString("tablename"), true);
            }

            return createdTables.values().stream().allMatch(value -> value == true);
        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Boolean createDatabase() { // DONE
        if(!this.isConnected())
            return false;
        List<String> commands = List.of(
                "DROP TABLE IF EXISTS connected_task",
                "DROP TABLE IF EXISTS task",
                "DROP TABLE IF EXISTS users",
                "DROP TYPE IF EXISTS state CASCADE;");
        try(Statement statement = this.connection.get().createStatement()){
            for(String cmd : commands){
                this.pushToConsumer(this.consumerForStatement, cmd);
                statement.execute(cmd);
            }
            createTables(statement);
            this.createUser(new CreateUserRequest("admin"));

            return true;
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    // TODO: Add trim to task name
    // TODO: support new options in [TaskListRequest]
    @Override
    public Result<List<DbTaskItem>> getTaskList(TaskListRequest request) { // DONE
        if(!this.isConnected())
            return new Result<>(null, null, false);
        if (request.statuses().isEmpty())
            return new Result<>(new ArrayList<>(), null, true);

        StringBuilder builder = new StringBuilder("SELECT * FROM task WHERE status IN ('");
        builder.append(request.statuses().getFirst().getName()).append("'");

        for(int i = 1; i < request.statuses().size(); ++i){
            builder.append(" ,'")
                    .append(request.statuses().get(i).getName())
                    .append("'");
        }
        builder.append(')');

        try(Statement statement = this.connection.get().createStatement()){
            ResultSet resultSet = statement.executeQuery(builder.toString());
            this.pushToConsumer(this.consumerForStatement, builder.toString());
            List<DbTaskItem> dbTaskItems = new LinkedList<>();

            while(resultSet.next()){
                Boolean[] subtasks = (Boolean[]) resultSet.getArray("subtasks_status").getArray();
                dbTaskItems.add(new DbTaskItem(
                        resultSet.getLong("id"), resultSet.getString("title"),
                        resultSet.getDate("date"), DbTaskStatus.converter(resultSet.getString("status")),
                        subtasks.length, (int) Arrays.stream(subtasks).filter(b -> b == true).count()
                ));
            }

            return new Result<>(dbTaskItems, null, true);
        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return new Result<>(null, null, false);
        }
    }

    @Override
    public Result<DbTaskDetail> getTaskDetail(Long taskId) { // DONE
        if(!this.isConnected())
            return new Result<>(null, null, false);

        try(PreparedStatement statementForConnected = this.
                connection.get().prepareStatement("SELECT t.id, t.title, t.date, t.status, t.subtasks, t.subtasks_status, ct.task_id  \n" +
                        "FROM connected_task ct INNER JOIN task t ON ct.another_task_id = t.id WHERE ct.task_id = ?");
            PreparedStatement statementForTask = this.connection.get().prepareStatement("SELECT * FROM task WHERE id = ?")){
            // CONNECTED TASKS
            statementForConnected.setLong(1, taskId);

            ResultSet resultFroConnected = statementForConnected.executeQuery();
            List<DbTaskItem> dbTaskItems = new LinkedList<>();
            while(resultFroConnected.next()){
                    Boolean[] subtasks = (Boolean[]) resultFroConnected.getArray("subtasks_status").getArray();
                    dbTaskItems.add(new DbTaskItem(
                            resultFroConnected.getLong("id"), resultFroConnected.getString("title"),
                            resultFroConnected.getDate("date"), DbTaskStatus.converter(resultFroConnected.getString("status")),
                            subtasks.length, (int) Arrays.stream(subtasks).filter(b -> b == true).count()
                    ));
            }
            resultFroConnected.close();

            // THIS TASK
            statementForTask.setLong(1, taskId);
            this.pushToConsumer(this.consumerForStatement,
                    statementForTask.toString(), statementForConnected.toString());
            ResultSet resultForTask = statementForTask.executeQuery();

            DbTaskDetail dbTaskDetail = null;

            if(resultForTask.next()){
                dbTaskDetail = new DbTaskDetail(resultForTask.getLong("id"),
                        resultForTask.getString("title"), resultForTask.getDate("date"),
                        DbTaskStatus.converter(resultForTask.getString("status")),
                        List.of((String[]) resultForTask.getArray("subtasks").getArray()),
                        List.of((Boolean[]) resultForTask.getArray("subtasks_status").getArray()),
                        dbTaskItems);
            }


            return new Result<>(dbTaskDetail, null, true);

        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return new Result<>(null, null, false);
        }

    }

    @Override
    public Boolean deleteTask(Long taskId) { // DONE
        if(!this.isConnected())
            return false;

        try(PreparedStatement statement = this.connection.get().prepareStatement("DELETE FROM task WHERE id = ?")){
            statement.setLong(1, taskId);

            this.pushToConsumer(this.consumerForStatement, statement.toString());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Result<Long> createTask(CreateTaskRequest request) { // DONE
        if(!this.isConnected())
            return new Result<>(-1L, null, false);

        try(PreparedStatement statement = this.connection.get().prepareStatement("INSERT INTO task " +
                "(title, date, subtasks, subtasks_status, author_id) VALUES (?, ?, ?, ?, ?) RETURNING id")){
            this.connection.get().setAutoCommit(false);

            statement.setString(1, request.title());
            statement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            statement.setArray(3,
                    this.connection.get().createArrayOf(
                            "VARCHAR", request.subtasks().toArray(new String[0])));
            Boolean[] boolArr = new Boolean[request.subtasks().size()];
            Arrays.fill(boolArr, false);

            statement.setArray(4, this.connection.get().createArrayOf("BOOLEAN",
                    boolArr));
            if(request.authorId() != null)
                statement.setLong(5, request.authorId());
            else
                statement.setNull(5, Types.INTEGER);

            this.pushToConsumer(this.consumerForStatement, statement.toString());
            ResultSet res = statement.executeQuery();

            long id = res.next() ? res.getLong("id") : -1;

            res.close();
            if(!request.tasksId().isEmpty()){
                try(
                    PreparedStatement statementById = this.connection.get()
                            .prepareStatement("INSERT INTO connected_task (task_id, another_task_id) VALUES (?, ?)")){

                    for (long taskId : request.tasksId()) {
                        statementById.setLong(1, id);
                        statementById.setLong(2, taskId);
                        this.pushToConsumer(this.consumerForStatement, statementById.toString());
                        statementById.executeUpdate();
                    }

                    for(long taskId : request.tasksId()){
                        statementById.setLong(1, taskId);
                        statementById.setLong(2, id);
                        statementById.executeUpdate();
                    }

                }
            }
            this.connection.get().commit();

            return new Result<>(id, null, id != -1);
        }catch (SQLException e){
            System.out.println(e.getMessage());
                try{
                    this.connection.get().rollback();
                } catch (SQLException ex) {
                    this.pushToConsumer(this.consumerForException, e);
                    return new Result<>(-1L, null, false);
                }
            this.pushToConsumer(this.consumerForException, e);
            return new Result<>(-1L, null, false);
        }finally {
            try {
                this.connection.get().setAutoCommit(true);
            } catch (SQLException e) {
                this.pushToConsumer(this.consumerForException, e);
            }
        }
    }

    @Override
    public Boolean createConnection(Long taskA, Long taskB) { // DONE
        if(!this.isConnected())
            return false;

        try(PreparedStatement statement = this.connection.get().prepareStatement("INSERT INTO connected_task VALUES (?, ?)")){
            statement.setLong(1, taskA);
            statement.setLong(2, taskB);
            this.pushToConsumer(this.consumerForStatement,  statement.toString());
            int first = statement.executeUpdate();

            statement.setLong(2, taskA);
            statement.setLong(1, taskB);

            return 2 == (first + statement.executeUpdate());
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Boolean changeSubtaskCompletion(Long taskId, Integer index) { // DONE
        if(!this.isConnected())
            return false;

        try(PreparedStatement statement = this.connection.get()
                .prepareStatement("UPDATE task SET subtasks_status[?] = NOT subtasks_status[?] WHERE id = ?")){
            statement.setLong(1, index + 1);
            statement.setLong(2, index + 1);
            statement.setLong(3, taskId);
            this.pushToConsumer(this.consumerForStatement, statement.toString());

            return statement.executeUpdate() == 1;
        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Boolean addSubtask(Long taskId, String subtask) { // DONE
        if(!this.isConnected() || subtask.isEmpty())
            return false;

        try(PreparedStatement statement = this.connection.get()
                .prepareStatement("UPDATE task SET subtasks = subtasks || ?, subtasks_status = subtasks_status || FALSE WHERE id = ?")){
            statement.setString(1, subtask);
            statement.setLong(2, taskId);
            this.pushToConsumer(this.consumerForStatement, statement.toString());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Boolean updateStatus(Long taskId, DbTaskStatus status) { // DONE
        if(!this.isConnected())
            return false;

        try(PreparedStatement statement = this.connection.get()
                .prepareStatement("UPDATE task SET status = ?::state WHERE id = ?")){
            statement.setString(1, status.getName());
            statement.setLong(2, taskId);

            this.pushToConsumer(this.consumerForStatement, statement.toString());
            return statement.executeUpdate() == 1;
        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Boolean setShouldForceUniqueName(boolean shouldForceUniqueName) { // DONE
        if(!this.isConnected())
            return false;
        try{
            alterStatement("ALTER TABLE task DROP CONSTRAINT IF EXISTS title_unique;");
            if(shouldForceUniqueName){
                this.connection.get().setAutoCommit(false);

                Statement statement = this.connection.get().createStatement();
                statement.executeUpdate("DELETE FROM task WHERE title IN \n" +
                        "(SELECT title FROM task\n" +
                        "EXCEPT ALL\n" +
                        "SELECT DISTINCT title FROM task);");
                statement.close();

                alterStatement("ALTER TABLE task ADD CONSTRAINT title_unique UNIQUE (title);");

                this.connection.get().commit();
            }

            return true;
        } catch (SQLException e) {
            try {
                this.connection.get().rollback();
            } catch (SQLException ex) {
                pushToConsumer(this.consumerForException, ex);
            }
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }finally {
            try {
                this.connection.get().setAutoCommit(true);
            } catch (SQLException e) {
                pushToConsumer(this.consumerForException, e);
            }
        }
    }

    @Override
    public Boolean setTaskTitleMinLength(int minLength) { // DONE
        if(!this.isConnected())
            return false;

        return setConstraintsAndDelete(List.of("DELETE FROM task WHERE LENGTH(title) < %d".formatted(minLength)),
                List.of("ALTER TABLE task DROP CONSTRAINT IF EXISTS min_length",
                        "ALTER TABLE task ADD CONSTRAINT min_length CHECK(LENGTH(title) >= %d)".formatted(minLength)));
    }

    @Override
    public Result<Long> createUser(CreateUserRequest request) { // DONE
        if(!this.isConnected())
            return new Result<>(null, "Not connected", false);

        try(PreparedStatement statement = this.connection.get().prepareStatement("INSERT INTO users(name) VALUES(?) RETURNING id")){
            statement.setString(1, request.name());

            ResultSet resultSet = statement.executeQuery();

            long id = resultSet.next() ? resultSet.getLong("id") : -1;
            this.pushToConsumer(this.consumerForStatement, statement.toString());
            return new Result<>(id, "", id != -1);
        }catch (SQLException e){
            this.pushToConsumer(this.consumerForException, e);
            return new Result<>(null, e.getMessage(), false);
        }
    }

    @Override
    public Boolean addUserToTask(Long userId, Long taskId) { // DONE
        if(!this.isConnected())
            return false;
        try(PreparedStatement statement = this.connection.get().prepareStatement("UPDATE task SET author_id = ? WHERE id = ?")){
            statement.setLong(1, userId);
            statement.setLong(2, taskId);

            this.pushToConsumer(this.consumerForStatement, statement.toString());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public Result<List<UserWithTaskCount>> getUsersWithTasks(GetUsersWithTasksRequest request) {
        return null;
    }

    @Override
    public boolean getForceUniqueTaskTitle() { // DONE
        if(!this.isConnected())
            return false;

        try {
            return isConstraintExists("title_unique");
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

    @Override
    public int getMinTaskTitleLength() { // DONE
        try {
            Optional<String> constraintContent = this.getConstraintContent("min_length");
            return constraintContent.map(s ->
                    Integer.parseInt(this.getNumberFromConstraint(s, numberPattern))).orElse(0);
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return 0;
        }
    }

    @Override
    public Boolean setTaskTitleMaxLength(int maxLength) { // DONE
        if(!this.isConnected())
            return false;

        return setConstraintsAndDelete(List.of("DELETE FROM task WHERE LENGTH(title) > %d".formatted(maxLength)),
                List.of("ALTER TABLE task DROP CONSTRAINT IF EXISTS max_length",
                        "ALTER TABLE task ADD CONSTRAINT max_length CHECK(LENGTH(title) <= %d)".formatted(maxLength)));
    }

    @Override
    public int getMaxTaskTitleLength() { // DONE
        try {
            Optional<String> constraintContent = this.getConstraintContent("max_length");
            return constraintContent.map(s ->
                    Integer.parseInt(this.getNumberFromConstraint(s, numberPattern))).orElse(100);
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return 100;
        }
    }

    /** Установка ограничений и удаление записей, которые под них не попадают
     * @param deleteRegexes Выражения, которые содержат в себе команду DELETE
     * @param alterRegexes Вырежения, которые содержат в себе команду ALTER
     * @return boolean - успешность транзакции
     * */
    public boolean setConstraintsAndDelete(List<String> deleteRegexes, List<String> alterRegexes){
        try{
            this.connection.get().setAutoCommit(false); // начало транзакции

            Statement statement = this.connection.get().createStatement();
            for(String deleteRegex : deleteRegexes){ // сначала выполняются команды удаления
                statement.executeUpdate(deleteRegex);
                this.pushToConsumer(this.consumerForStatement, deleteRegex);
            }
            statement.close();

            for(String alterRegex : alterRegexes){ // только после удаления уже ставятся изменения
                alterStatement(alterRegex);
            }

            this.connection.get().commit(); // все ок - коммитим
            return true;
        } catch (SQLException e) {
            try {
                this.connection.get().rollback(); // откатываем транзакицию в случае ошибки
            } catch (SQLException ex) {
                this.pushToConsumer(consumerForException, ex);
            }
            this.pushToConsumer(consumerForException, e);
            return false;
        }finally {
            try {
                this.connection.get().setAutoCommit(true); // завершаем транзакцию
            } catch (SQLException e) {
                this.pushToConsumer(consumerForException, e);
            }
        }
    }

    /** Использование ALTER в Query
     * @param query Выражение
     * */
    private void alterStatement(String query) throws SQLException {
        Statement s = this.connection.get().createStatement();
        s.execute(query);
        this.pushToConsumer(this.consumerForStatement, query);
        s.close();
    }

    /** Использование системной таблицы для получения содержимого ограничения
     * @param constraintName Название ограничения
     * @return Optional<String> - содержимое ограничения
     * */
    private Optional<String> getConstraintContent(String constraintName) throws SQLException {
        try(PreparedStatement statement = this.connection.get().prepareStatement("SELECT cc.check_clause FROM information_schema.table_constraints tc " +
                "JOIN information_schema.check_constraints cc ON tc.constraint_name = cc.constraint_name " +
                "WHERE tc.constraint_name = ?")) {
            statement.setString(1, constraintName);
            ResultSet resultSet = statement.executeQuery();

            return Optional.ofNullable(resultSet.next() ? resultSet.getString("check_clause") : null);
        }
    }

    /** Использование системной таблицы для получения информации об ограничении типа UNIQUE/PRIMARY KEY
     * @param constraintName Название ограничения
     * @return boolean - содержится ли ограничение
     * */
    private boolean isConstraintExists(String constraintName) throws SQLException {
        try(PreparedStatement statement = this.connection.get().prepareStatement("SELECT tc.table_name, tc.constraint_name, tc.constraint_type " +
                "FROM information_schema.table_constraints tc " +
                "WHERE  tc.constraint_name = ?;")) {
            statement.setString(1, constraintName);

            return statement.executeQuery().next();
        }
    }

    private <T> void pushToConsumer(Consumer<T> consumer, T ...elements){
        for(T el : elements)
            consumer.accept(el);
    }

    private boolean isConnected(){
        return connection.isPresent();
    }

    private void createTables(Statement statement) throws SQLException{
        List<String> createCommands = List.of("CREATE TYPE state AS enum ('Бэклог', 'В процессе', 'На проверке', 'Выполненное', 'Отменено')",
                "CREATE TABLE IF NOT EXISTS users (\n" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "name VARCHAR(100) NOT NULL CHECK (LENGTH(name) > 0)\n" +
                        ");",
                "CREATE TABLE IF NOT EXISTS task (\n" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "title VARCHAR(100) NOT NULL,\n" +
                        "date DATE,\n" +
                        "status state DEFAULT 'Бэклог',\n" +
                        "subtasks VARCHAR(100)[],\n" +
                        "subtasks_status BOOLEAN[],\n" +
                        "author_id INTEGER," +
                        "FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL);",
                "CREATE TABLE IF NOT EXISTS connected_task (\n" +
                        "task_id INTEGER NOT NULL,\n" +
                        "another_task_id INTEGER NOT NULL,\n" +
                        "PRIMARY KEY (task_id, another_task_id),\n" +
                        "FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE,\n" +
                        "FOREIGN KEY (another_task_id) REFERENCES task (id) ON DELETE CASCADE);");
        for(String create : createCommands){
            this.pushToConsumer(this.consumerForStatement, create);
            statement.execute(create);
        }
    }

    /** Возвращает число, которое заложено в ограничении
     * @param constraintContent Содержимое ограничения
     * @param pattern regex-выражение
     * @return Строка - число
     * */
    private String getNumberFromConstraint(String constraintContent, Pattern pattern){
        Matcher matcher = pattern.matcher(constraintContent);
        if(matcher.find())
            return matcher.group();
        throw new IllegalArgumentException("Заданного паттерна %s не найдено.".formatted(pattern.toString()));
    }
}
