package ui.screens.task_detail

import database.model.DbTaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import models.TaskDetail
import models.TaskStatus
import models.asTaskStatus
import repository.TodoRepository

class TaskDetailViewModel(
  private val id: Long,
  private val todoRepository: TodoRepository
) {

  private val _taskFlow: MutableStateFlow<TaskDetail?> = MutableStateFlow(null)
  val taskFlow: Flow<TaskDetail?> = _taskFlow

  private val _newSubtaskState: MutableStateFlow<NewSubtaskState> = MutableStateFlow(NewSubtaskState.Closed)
  val newSubtaskState: Flow<NewSubtaskState> = _newSubtaskState

  init {
    requestTaskDetail()
  }

  fun openSubtaskWindow() {
    _newSubtaskState.value = NewSubtaskState.Opened()
  }

  fun editSubtask(subtask: String) {
    _newSubtaskState.value = (_newSubtaskState.value as NewSubtaskState.Opened).copy(subtask = subtask)
  }

  fun addSubtask(taskName: String) {
    val state = _newSubtaskState.value as NewSubtaskState.Opened
    val result = todoRepository.addSubtask(_taskFlow.value!!.id, state.subtask)
    if (result) {
      requestTaskDetail()
    }
    closeSubtaskWindow()
  }

  fun closeSubtaskWindow() {
    _newSubtaskState.value = NewSubtaskState.Closed
  }

  private fun requestTaskDetail() {
    _taskFlow.value = todoRepository.getTaskDetail(id)
  }

  fun toggleSubtask(index: Int) {
    val result = todoRepository.toggleSubtask(_taskFlow.value!!.id, index)
    if (result) {
      requestTaskDetail()
    }
  }

  fun updateStatus(status: DbTaskStatus) {
    val result = todoRepository.updateStatus(_taskFlow.value!!.id, status)
    if (result) {
      requestTaskDetail()
    }
  }
}

sealed interface NewSubtaskState {
  object Closed : NewSubtaskState
  data class Opened(val subtask: String = "") : NewSubtaskState
}

sealed class StatusUpdateButton(
  val text: String,
  val toStatus: DbTaskStatus,
) {
  val color = toStatus.asTaskStatus().color

  object ToBacklog : StatusUpdateButton("Вернуть в беклог", DbTaskStatus.BACKLOG)
  object ToWork : StatusUpdateButton("Взять в работу", DbTaskStatus.IN_PROGRESS)
  object ToReview : StatusUpdateButton("Отправить на проверку", DbTaskStatus.IN_REVIEW)
  object ToDone : StatusUpdateButton("Завершить", DbTaskStatus.DONE)
  object Drop : StatusUpdateButton("Не будет сделано", DbTaskStatus.DROPPED)

  companion object {
    val buttonMappings = mapOf(
      TaskStatus.BACKLOG to listOf(ToWork, Drop),
      TaskStatus.IN_PROGRESS to listOf(ToReview, Drop),
      TaskStatus.IN_REVIEW to listOf(ToDone, Drop),
      TaskStatus.DONE to listOf(),
      TaskStatus.DROPPED to listOf(ToBacklog)
    )
  }
}