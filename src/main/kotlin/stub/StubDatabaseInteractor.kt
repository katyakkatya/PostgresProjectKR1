package stub

import database.DatabaseInteractor
import database.model.DbTaskDetail
import database.model.DbTaskItem
import database.model.DbTaskStatus
import database.request.ConnectionRequest
import database.request.CreateTaskRequest
import database.request.TaskListRequest
import database.result.Result
import java.util.*

class StubDatabaseInteractor: DatabaseInteractor {
  override fun tryConnect(request: ConnectionRequest?): Boolean? {
    TODO("Not yet implemented")
  }

  override fun databaseExists(): Boolean {
    return true
  }

  override fun createDatabase(): Boolean {
    return true
  }

  override fun getTaskList(request: TaskListRequest?): Result<List<DbTaskItem?>?>? {
    return Result(
      listOf(
        DbTaskItem(1, "Тестовая таска", Date.from(java.time.Instant.now()), DbTaskStatus.IN_PROGRESS, 10, 5)
      ),
      null,
      true
    )
  }

  override fun getTaskDetail(taskId: Long): Result<DbTaskDetail> {
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
    TODO("Not yet implemented")
  }

  override fun createTask(request: CreateTaskRequest?): Result<Long?>? {
    TODO("Not yet implemented")
  }

  override fun createConnection(taskA: Long?, taskB: Long?): Boolean {
    TODO("Not yet implemented")
  }

  override fun changeSubtaskCompletion(taskId: Long?, index: Int?): Boolean? {
    TODO("Not yet implemented")
  }

  override fun tryConnect(): Boolean {
    return true
  }
}