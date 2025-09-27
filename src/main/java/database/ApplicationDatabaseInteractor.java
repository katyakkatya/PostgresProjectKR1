package database;

import database.model.DbTaskDetail;
import database.model.DbTaskItem;
import database.model.DbTaskStatus;
import database.request.ConnectionRequest;
import database.request.CreateTaskRequest;
import database.request.TaskListRequest;
import database.result.Result;

import java.sql.*;
import java.util.*;
import java.util.function.Consumer;

public class ApplicationDatabaseInteractor implements DatabaseInteractor{

    private Optional<Connection> connection = Optional.empty();

    private Consumer<String> consumerForStatement = System.out::println;
    private Consumer<Exception> consumerForException = System.err::println;

    @Override
    public void setConsumers(Consumer<String> consumerForStatement,
                             Consumer<Exception> consumerForException){
        this.consumerForStatement = consumerForStatement;
        this.consumerForException = consumerForException;
    }

    private <T> void pushToConsumer(Consumer<T> consumer, T ...elements){
        for(T el : elements)
            consumer.accept(el);
    }

    private boolean isConnected(){
        return connection.isPresent();
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
        Map<String, Boolean> createdTables = new HashMap<>(2);

        createdTables.put("task", false);
        createdTables.put("connected_task", false);

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
                "DROP TYPE IF EXISTS state CASCADE;", // FOR TEST
                "CREATE TYPE state AS enum ('Бэклог', 'В процессе', 'На проверке', 'Выполненное', 'Отменено')",
                "CREATE TABLE IF NOT EXISTS task (\n" +
                        "id SERIAL PRIMARY KEY,\n" +
                  "title VARCHAR(100) NOT NULL UNIQUE CHECK (LENGTH(title) > 2),\n" +
                        "date DATE,\n" +
                        "status state DEFAULT 'Бэклог',\n" +
                        "subtasks VARCHAR(100)[],\n" +
                        "subtasks_status BOOLEAN[]);",
                "CREATE TABLE IF NOT EXISTS connected_task (\n" +
                        "task_id INTEGER NOT NULL,\n" +
                        "another_task_id INTEGER NOT NULL,\n" +
                        "PRIMARY KEY (task_id, another_task_id),\n" +
                        "FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE,\n" +
                        "FOREIGN KEY (another_task_id) REFERENCES task (id) ON DELETE CASCADE);");
        try(Statement statement = this.connection.get().createStatement()){
            for(String cmd : commands) {
                this.pushToConsumer(this.consumerForStatement, cmd);
                statement.execute(cmd);
            }

            return true;
        } catch (SQLException e) {
            this.pushToConsumer(this.consumerForException, e);
            return false;
        }
    }

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
                "(title, date, subtasks, subtasks_status) VALUES (?, ?, ?, ?) RETURNING id")){
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
            this.pushToConsumer(this.consumerForStatement, statement.toString());
            ResultSet res = statement.executeQuery();

            long id = res.next() ? res.getLong("id") : -1;


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
                    return new Result<Long>(-1L, null, false);
                }
            this.pushToConsumer(this.consumerForException, e);
            return new Result<Long>(-1L, null, false);
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
}
