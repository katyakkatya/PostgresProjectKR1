package stub

import database.DatabaseInteractor
import database.model.DbTaskDetail
import database.model.DbTaskItem
import database.model.DbTaskStatus
import database.request.ConnectionRequest
import database.request.CreateTaskRequest
import database.request.TaskListRequest
import database.result.Result
import java.time.LocalDate
import java.util.Date
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class StubDatabaseInteractor: DatabaseInteractor {
  override fun tryConnect(request: ConnectionRequest?): Boolean? {
    TODO("Not yet implemented")
  }

  override fun databaseExists(): Boolean? {
    TODO("Not yet implemented")
  }

  override fun createDatabase(): Boolean? {
    TODO("Not yet implemented")
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

  override fun getTaskDetail(taskId: Long?): Result<DbTaskDetail?>? {
    TODO("Not yet implemented")
  }

  override fun deleteTask(taskId: Long?): Boolean? {
    TODO("Not yet implemented")
  }

  override fun createTask(request: CreateTaskRequest?): Result<Long?>? {
    TODO("Not yet implemented")
  }

  override fun createConnection(taskA: Long?, taskB: Long?): Boolean? {
    TODO("Not yet implemented")
  }

  override fun changeSubtaskCompletion(taskId: Long?, index: Int?): Boolean? {
    TODO("Not yet implemented")
  }
}