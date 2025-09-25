package database;

import database.model.DbTaskDetail;
import database.model.DbTaskItem;
import database.request.ConnectionRequest;
import database.request.CreateTaskRequest;
import database.request.TaskListRequest;
import database.result.Result;

import java.util.List;

public interface DatabaseInteractor {
  /**
   * Try to connetc to DB
   * */
  Boolean tryConnect(ConnectionRequest request);

  /**
   * Tries to connect to database and returns true on success
   * Url should be set before this method is called
   */
  Boolean tryConnect();// TODO: текст проверка наличия базы данных

  /**
   * Checks if BOTH Task and TaskConnection tables exists
   */
  Boolean databaseExists();

  /**
   * Creates database and returns true on success
   */
  Boolean createDatabase();

  /**
   * Gets a list if task items from database with filters
   * If none of the filters are applied, all tasks should be returned
   */
  Result<List<DbTaskItem>> getTaskList(TaskListRequest request);

  /**
   * Gets detailed task info by id
   */
  Result<DbTaskDetail> getTaskDetail(Long taskId);

  /**
   * Deletes task by id
   */
  Boolean deleteTask(Long taskId);

  /**
   * Creates task and returns its id on success
   */
  Result<Long> createTask(CreateTaskRequest request);

  /**
   * Creates connection between tasks and returns true on success
   */
  Boolean createConnection(Long taskA, Long taskB);

  /**
   * Marks subtaskCount as completed and returns true on success
   */
  Boolean changeSubtaskCompletion(Long taskId, Integer index);
}
