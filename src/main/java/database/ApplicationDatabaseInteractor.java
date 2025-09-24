package database;

import database.model.DbTaskDetail;
import database.model.DbTaskItem;
import database.request.ConnectionRequest;
import database.request.CreateTaskRequest;
import database.request.TaskListRequest;
import database.result.Result;

import java.sql.*;
import java.util.*;

public class ApplicationDatabaseInteractor implements DatabaseInteractor{

    private Optional<Connection> connection = Optional.empty();

    private boolean isConnected(){
        return connection.isPresent();
    }

    @Override
    public Boolean tryConnect(ConnectionRequest request) {
        /*
        String url1 = "jdbc:postgresql://localhost:9876/postgres"; // одключение к бд
        String user = "postgres";
        String password = "postgres";
        * */
        if(this.connection.isPresent())
            return true;
        try{
            Connection conn = DriverManager.getConnection(request.url(),
                    request.username(), request.password());

            this.connection = Optional.of(conn);
            return true;
        } catch (SQLException e) {
            System.err.printf("Coudn`t connect to database '%s'\n", request.url());
            return false;
        }
    }

    @Override
    public Boolean databaseExists() {
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

            return createdTables.values().stream().allMatch(value -> value.booleanValue() == true);
        }catch (SQLException e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean createDatabase() {
        if(!this.isConnected())
            return false;
        List<String> commands = List.of(
                "DROP TYPE IF EXISTS state CASCADE;", // FOR TEST
                "CREATE TYPE state AS enum ('Бэклог', 'В процессе', 'На проверке', 'Выполненное', 'Отменено')",
                "CREATE TABLE IF NOT EXISTS task (\n" +
                        "id SERIAL PRIMARY KEY,\n" +
                        "title VARCHAR(100) NOT NULL UNIQUE CHECK (LENGTH(title) > 0),\n" +
                        "date DATE,\n" +
                        "status state,\n" +
                        "subtasks VARCHAR(100)[],\n" +
                        "subtasks_status BOOLEAN[]);",
                "CREATE TABLE IF NOT EXISTS connected_task (\n" +
                        "task_id INTEGER NOT NULL,\n" +
                        "another_task_id INTEGER NOT NULL,\n" +
                        "PRIMARY KEY (task_id, another_task_id),\n" +
                        "FOREIGN KEY (task_id) REFERENCES task (id) ON DELETE CASCADE,\n" +
                        "FOREIGN KEY (another_task_id) REFERENCES task (id) ON DELETE CASCADE);");
        try(Statement statement = this.connection.get().createStatement()){
            for(String cmd : commands)
                statement.execute(cmd);

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Result<List<DbTaskItem>> getTaskList(TaskListRequest request) {
        return null;
    }

    @Override
    public Result<DbTaskDetail> getTaskDetail(Long taskId) {
        return null;
    }

    @Override
    public Boolean deleteTask(Long taskId) {
        return null;
    }

    @Override
    public Result<Long> createTask(CreateTaskRequest request) {
        return null;
    }

    @Override
    public Boolean createConnection(Long taskA, Long taskB) {
        return null;
    }

    @Override
    public Boolean markSubtaskCompletion(Long taskId, Integer index) {
        return null;
    }

}
