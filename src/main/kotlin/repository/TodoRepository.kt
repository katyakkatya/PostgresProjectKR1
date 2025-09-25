package repository

import database.DatabaseInteractor
import database.request.TaskListRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import models.TaskDetail
import models.TaskItemModel
import models.asTask
import models.asTaskDetail

class TodoRepository(
  private val interactor: DatabaseInteractor
) {

  private val _tasksListFlow = MutableStateFlow(emptyList<TaskItemModel>())
  val tasksListFlow: Flow<List<TaskItemModel>> = _tasksListFlow

  private val _errorMessageFlow = MutableStateFlow<String?>(null)
  val errorMessageFlow: Flow<String?> = _errorMessageFlow

  init {
    _errorMessageFlow.value = "403"
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
}