package stub

import database.DatabaseInteractor
import database.model.*
import database.request.*
import database.result.Result
import java.util.*
import java.util.function.Consumer

class StubDatabaseInteractor : DatabaseInteractor {
  override fun tryConnect(request: ConnectionRequest?): Boolean? {
    println("StubDatabaseInteractor: tryConnect called with request: $request")
    return true
  }

  override fun databaseExists(): Boolean {
    println("StubDatabaseInteractor: databaseExists called")
    return true
  }

  override fun createDatabase(): Boolean {
    println("StubDatabaseInteractor: createDatabase called")
    return true
  }

  override fun getTaskList(request: TaskListRequest?): Result<List<DbTaskItem?>?>? {
    println("StubDatabaseInteractor: getTaskList called with request: $request")
    return Result(
      listOf(
        DbTaskItem(1, "Тестовая таска", Date.from(java.time.Instant.now()), DbTaskStatus.IN_PROGRESS, 10, 5),
        DbTaskItem(2, "Тестовая таска 2", Date.from(java.time.Instant.now()), DbTaskStatus.BACKLOG, 10, 5),
        DbTaskItem(3, "Тестовая таска 24534", Date.from(java.time.Instant.now()), DbTaskStatus.DROPPED, 10, 5),
        DbTaskItem(4, "Тестовая таска 24534", Date.from(java.time.Instant.now()), DbTaskStatus.DROPPED, 10, 5),
        DbTaskItem(5, "Тестовая таска 24534", Date.from(java.time.Instant.now()), DbTaskStatus.DROPPED, 10, 5),
        DbTaskItem(6, "Тестовая таска 24534", Date.from(java.time.Instant.now()), DbTaskStatus.DROPPED, 10, 5),
        DbTaskItem(7, "Тестовая таска 24534", Date.from(java.time.Instant.now()), DbTaskStatus.DROPPED, 10, 5),
      ),
      null,
      true
    )
  }

  override fun getTaskDetail(taskId: Long): Result<DbTaskDetail> {
    println("StubDatabaseInteractor: getTaskDetail called with taskId: $taskId")
    return if (taskId == 1L) {
      Result(
        DbTaskDetail(
          1,
          "Тестовая таска 1",
          Date.from(java.time.Instant.now()),
          DbTaskStatus.IN_PROGRESS,
          listOf("сделать одно", "сделать два", "сделать три"),
          listOf(false, false, false),
          listOf(
            DbTaskItem(2, "Тестовая таска 2", Date.from(java.time.Instant.now()), DbTaskStatus.BACKLOG, 2, 1)
          )
        ),
        null,
        true
      )
    } else {
      Result(
        DbTaskDetail(
          2,
          "Тестовая таска 2",
          Date.from(java.time.Instant.now()),
          DbTaskStatus.BACKLOG,
          listOf("сделать одно", "сделать два"),
          listOf(true, false),
          listOf(
            DbTaskItem(1, "Тестовая таска 1", Date.from(java.time.Instant.now()), DbTaskStatus.IN_PROGRESS, 3, 0)
          )
        ),
        null,
        true
      )
    }
  }

  override fun deleteTask(taskId: Long?): Boolean? {
    println("StubDatabaseInteractor: deleteTask called with taskId: $taskId")
    return true
  }

  override fun createTask(request: CreateTaskRequest?): Result<Long?>? {
    println("StubDatabaseInteractor: createTask called with request: $request")
    return Result(1L, null, true)
  }

  override fun createConnection(taskA: Long?, taskB: Long?): Boolean {
    println("StubDatabaseInteractor: createConnection called with taskA: $taskA, taskB: $taskB")
    return true
  }

  override fun changeSubtaskCompletion(taskId: Long?, index: Int?): Boolean? {
    println("StubDatabaseInteractor: changeSubtaskCompletion called with taskId: $taskId, index: $index")
    return true
  }

  override fun addSubtask(taskId: Long?, subtask: String?): Boolean? {
    println("StubDatabaseInteractor: addSubtask called with taskId: $taskId, subtask: $subtask")
    return true
  }

  override fun updateStatus(taskId: Long?, status: DbTaskStatus?): Boolean? {
    println("StubDatabaseInteractor: updateStatus called with taskId: $taskId, status: $status")
    return true
  }

  override fun setConsumers(
    consumerForStatement: Consumer<String?>?,
    consumerForException: Consumer<Exception?>?
  ) {

  }

  override fun setShouldForceUniqueName(shouldForceUniqueName: Boolean): Boolean? {
    return true
  }

  override fun setTaskTitleMinLength(minLength: Int): Boolean? {
    return false;
  }

  override fun createUser(request: CreateUserRequest?): Result<Long?>? {
    return Result(1L, null, true)
  }

  override fun addUserToTask(userId: Long?, taskId: Long?): Boolean? {
    return true
  }

  override fun getUsersWithTasks(request: GetUsersWithTasksRequest?): Result<List<UserWithTaskCount?>?>? {
    return Result(
      listOfNotNull(
        UserWithTaskCount(User(1, "Артем"), 5),
        UserWithTaskCount(User(2, "Артем"), 5),
        UserWithTaskCount(User(3, "Артем"), 5),
        UserWithTaskCount(User(4, "Артем"), 5),
        if (request!!.regexQuery != "") UserWithTaskCount(User(5, "Артем 2"), 0) else null
      ), null, true
    )
  }

  override fun getForceUniqueTaskTitle(): Boolean {
    return false
  }

  override fun getMinTaskTitleLength(): Int {
    return 5
  }

  override fun setTaskTitleMaxLength(maxLength: Int): Boolean? {
    return false;
  }

  override fun getMaxTaskTitleLength(): Int {
    return 10
  }
}
