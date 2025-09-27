package ui.screens.task_detail

import database.model.DbTaskStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import models.TaskDetail
import models.TaskItemModel
import models.TaskStatus
import models.asTaskStatus
import repository.TodoRepository
import ui.GlobalNavigator

class TaskDetailViewModel(
  private val id: Long,
  private val todoRepository: TodoRepository,
) {

  private val _taskFlow: MutableStateFlow<TaskDetail?> = MutableStateFlow(null)
  val taskFlow: Flow<TaskDetail?> = _taskFlow

  private val _newSubtaskState: MutableStateFlow<NewSubtaskState> = MutableStateFlow(NewSubtaskState.Closed)
  val newSubtaskState: Flow<NewSubtaskState> = _newSubtaskState

  private val _deletionWindowOpenedFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val deletionWindowOpenedFlow: Flow<Boolean> = _deletionWindowOpenedFlow

  private val _relatedTasksStateFlow: MutableStateFlow<NewRelatedTaskState> =
    MutableStateFlow(NewRelatedTaskState.Closed)
  val relatedTasksStateFlow: Flow<NewRelatedTaskState> = _relatedTasksStateFlow

  fun onInit() {
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

  fun openDeletionWindow() {
    _deletionWindowOpenedFlow.value = true
  }

  fun deleteTask() {
    val result = todoRepository.deleteTask(_taskFlow.value!!.id)
    if (result) {
      GlobalNavigator.navigateToListOrPreviousTask()
    }
  }

  fun closeDeletionWindow() {
    _deletionWindowOpenedFlow.value = false
  }

  fun openRelatedTaskWindow() {
    runBlocking {
      _relatedTasksStateFlow.value = NewRelatedTaskState.Opened(
        todoRepository.tasksListFlow.first()
          .filter { task ->
            task.id != id &&
              task.id !in _taskFlow.value!!.relatedTasks.map { relatedTask -> relatedTask.id }
          }
      )
    }
  }

  fun closeRelatedTaskWindow() {
    _relatedTasksStateFlow.value = NewRelatedTaskState.Closed
  }

  fun addRelatedTask(taskId: Long) {
    val result = todoRepository.addRelatedTask(id, taskId)
    if (result) {
      closeRelatedTaskWindow()
      requestTaskDetail()
    }
  }
}

sealed interface NewSubtaskState {
  object Closed : NewSubtaskState
  data class Opened(val subtask: String = "", val error: String? = null) : NewSubtaskState
}

sealed interface NewRelatedTaskState {
  object Closed : NewRelatedTaskState
  data class Opened(val tasks: List<TaskItemModel>) : NewRelatedTaskState
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