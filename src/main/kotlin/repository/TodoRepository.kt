package repository

import database.DatabaseInteractor
import database.model.DbTaskStatus
import database.request.CreateTaskRequest
import database.request.GetUsersWithTasksRequest
import database.request.TaskListRequest
import database.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import models.*

class TodoRepository(
  private val interactor: DatabaseInteractor
) {

  private val _tasksListFlow = MutableStateFlow(emptyList<TaskItemModel>())
  val tasksListFlow: Flow<List<TaskItemModel>> = _tasksListFlow

  private val _usersListFlow = MutableStateFlow(emptyList<UserModel>())
  val usersListFlow: Flow<List<UserModel>> = _usersListFlow

  private val _usersWithTasksListFlow = MutableStateFlow(emptyList<UserWithTasksModel>())
  val usersWithTasksListFlow: Flow<List<UserWithTasksModel>> = _usersWithTasksListFlow

  private val _errorMessageFlow = MutableStateFlow<String?>(null)
  val errorMessageFlow: Flow<String?> = _errorMessageFlow

  private val _logsFlow = MutableStateFlow(emptyList<LogModel>())
  val logsFlow: Flow<List<LogModel>> = _logsFlow

  private val _settingsFlow = MutableStateFlow(Settings.DEFAULT)
  val settingsFlow: Flow<Settings> = _settingsFlow

  init {
    interactor.setConsumers(
      { message ->
        _logsFlow.value = _logsFlow.value + LogModel(LogType.INFO, message)
      },
      { exception ->
        _logsFlow.value = _logsFlow.value + LogModel(LogType.ERROR, exception.message ?: "Unknown error occured")
      }
    )
  }

  fun getTasksList(request: TaskListRequest) {
    val result = interactor.getTaskList(request)
    if (result.success) {
      _tasksListFlow.value = result.data!!.map { it.asTask() }
    } else {
      showErrorMessage(result.errorMessage ?: "Произошла ошибка при получении списка задач")
    }
  }

  fun getTaskDetail(id: Long): TaskDetail? {
    val result = interactor.getTaskDetail(id)
    if (result.success) {
      return result.data!!.asTaskDetail()
    } else {
      showErrorMessage(result.errorMessage ?: "Произошла ошибка при получении деталей задачи")
      return null
    }
  }

  fun getAllUsers() {
    val result = interactor.getAllUsers()
    if (result.success) {
      _usersListFlow.value = result.data!!.map { it.asUser() }
    } else {
      showErrorMessage(result.errorMessage ?: "Произошла ошибка при получении пользователь")
    }
  }

  fun showErrorMessage(message: String) {
    _errorMessageFlow.value = message
  }

  fun hideErrorMessage() {
    _errorMessageFlow.value = null
  }

  fun tryCreateDatabase(): Boolean {
    val result = interactor.createDatabase()
    if (result == false) {
      showErrorMessage("Произошла ошибка при создании базы данных")
    }
    return result
  }

  fun addSubtask(taskId: Long, subtask: String): Boolean {
    val result = interactor.addSubtask(taskId, subtask)
    if (result == false) {
      showErrorMessage("Произошла ошибка при добавлении подзадачи")
    }
    return result
  }

  fun toggleSubtask(taskId: Long, index: Int): Boolean {
    val result = interactor.changeSubtaskCompletion(taskId, index)
    if (result == false) {
      showErrorMessage("Произошла ошибка при переключении подзадачи")
    }
    return result
  }

  fun updateStatus(taskId: Long, status: DbTaskStatus): Boolean {
    val result = interactor.updateStatus(taskId, status)
    if (result == false) {
      showErrorMessage("Произошла ошибка при обновлении статуса задачи")
    }
    return result
  }

  fun deleteTask(taskId: Long): Boolean {
    val result = interactor.deleteTask(taskId)
    if (result == false) {
      showErrorMessage("Произошла ошибка при удалении задачи")
    }
    return result
  }

  fun addRelatedTask(taskId: Long, relatedTaskId: Long): Boolean {
    val result = interactor.createConnection(taskId, relatedTaskId)
    if (result == false) {
      showErrorMessage("Произошла ошибка при добавлении связанной задачи")
    }
    return result
  }

  fun saveNewTask(title: String, subtasks: List<String>, connectedTasks: List<Long>): Result<Long> {
    val userId = 1
    val result = interactor.createTask(CreateTaskRequest(title, subtasks, connectedTasks, 1))
    if (result.success == false) {
      showErrorMessage(result.errorMessage ?: "Произошла ошибка при создании новой задачи")
    }
    return result
  }

  fun loadSettings() {
    _settingsFlow.value = Settings(
      forceUniqueTaskTitle = interactor.getForceUniqueTaskTitle(),
      minTaskTitleLength = interactor.getMinTaskTitleLength(),
      maxTaskTitleLength = interactor.getMaxTaskTitleLength(),
    )
  }

  fun applySettings(settings: Settings) {
    interactor.setShouldForceUniqueName(settings.forceUniqueTaskTitle)
    interactor.setTaskTitleMinLength(settings.minTaskTitleLength)
    interactor.setTaskTitleMaxLength(settings.maxTaskTitleLength)
  }

  fun getUsersWithTasksList(request: GetUsersWithTasksRequest) {
    val result = interactor.getUsersWithTasks(request)
    if (result.success) {
      _usersWithTasksListFlow.value = result.data!!.map { it.asUserWithTasks() }
    } else {
      showErrorMessage(result.errorMessage ?: "Произошла ошибка при получении списка задач")
    }
  }
}

data class Settings(
  val forceUniqueTaskTitle: Boolean,
  val minTaskTitleLength: Int,
  val maxTaskTitleLength: Int,
) {
  companion object {
    val DEFAULT = Settings(false, 0, 10)
  }
}