package ui.screens.task_detail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import models.TaskDetail
import repository.TodoRepository

class TaskDetailViewModel(
  private val id: Long,
  private val todoRepository: TodoRepository
) {

  private val _taskFlow: MutableStateFlow<TaskDetail?> = MutableStateFlow(null)
  val taskFlow: Flow<TaskDetail?> = _taskFlow

  init {
    requestTaskDetail()
  }

  private fun requestTaskDetail() {
    _taskFlow.value = todoRepository.getTaskDetail(id)
  }
}