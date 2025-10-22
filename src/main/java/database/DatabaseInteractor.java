package database;

import database.model.DbTaskDetail;
import database.model.DbTaskItem;
import database.model.DbTaskStatus;
import database.model.UserWithTaskCount;
import database.request.*;
import database.result.Result;

import java.util.List;
import java.util.function.Consumer;

public interface DatabaseInteractor {
  /**
   * Try to connetc to DB
   * */
  Boolean tryConnect(ConnectionRequest request);

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

  /**
   * Adds subtask to task and returns true on success
   */
  Boolean addSubtask(Long taskId, String subtask);

  /**
   * Changes task status and returns true on success
   */
  Boolean updateStatus(Long taskId, DbTaskStatus status);

  /**
   * Sets consumers for logs
   */
  void setConsumers(Consumer<String> consumerForStatement,
                    Consumer<Exception> consumerForException);

  /**
   * If true passed, sets unique constraint on task.title using alter table
   */
  Boolean setShouldForceUniqueName(boolean shouldForceUniqueName);

  /**
   * Sets minimal length task.title using alter table
   */
  Result<Boolean> setTaskTitleMinLength(int minLength);

  /**
   * Sets maximum length task.title using alter table
   */
  Result<Boolean> setTaskTitleMaxLength(int maxLength);

  /**
   * Creates user and returns its id on success
   */
  Result<Long> createUser(CreateUserRequest request);

  /**
   * Adds user to task and returns true on success
   */
  Boolean addUserToTask(Long userId, Long taskId);

  /**
   * Searches for users by its name and filters.
   * When search is empty, return all users
   */
  Result<List<UserWithTaskCount>> getUsersWithTasks(GetUsersWithTasksRequest request);

  boolean getForceUniqueTaskTitle();

  int getMinTaskTitleLength();

  int getMaxTaskTitleLength();
}
