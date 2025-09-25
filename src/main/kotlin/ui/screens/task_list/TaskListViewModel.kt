package ui.screens.task_list

import database.model.DbTaskStatus
import database.request.TaskListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository

class TaskListViewModel(
  private val todoRepository: TodoRepository,
) {
  val tasksListFlow = todoRepository.tasksListFlow

  private val _statusFilterFlow = MutableStateFlow(
    mutableSetOf(
      DbTaskStatus.BACKLOG, DbTaskStatus.IN_PROGRESS, DbTaskStatus.IN_REVIEW,
      DbTaskStatus.DONE, DbTaskStatus.DROPPED
    )
  )
  val statusFilterFlow = _statusFilterFlow

  init {
    updateList()
  }

  private fun updateList() {
    todoRepository.getTasksList(TaskListRequest(_statusFilterFlow.value.toList()))
  }

  fun toggleStatusFilter(status: DbTaskStatus) {
    val current = _statusFilterFlow.value
    _statusFilterFlow.value = if (status in current) {
      current.toMutableSet().apply { remove(status) }
    } else {
      current.toMutableSet().apply { add(status) }
    }
    updateList()
  }

}