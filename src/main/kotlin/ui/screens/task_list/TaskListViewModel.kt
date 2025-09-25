package ui.screens.task_list

import database.model.DbTaskStatus
import database.request.TaskListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import models.TaskItemModel
import repository.TodoRepository

class TaskListViewModel(
  private val todoRepository: TodoRepository,
) {
  val tasksListFlow = todoRepository.tasksListFlow

  private val _newTaskWindowStateFlow: MutableStateFlow<NewTaskWindowState> =
    MutableStateFlow(NewTaskWindowState.Closed)
  val newTaskWindowStateFlow = _newTaskWindowStateFlow

  private val _taskSelectWindowState: MutableStateFlow<TaskSelectWindowState> =
    MutableStateFlow(TaskSelectWindowState.Closed)
  val taskSelectWindowState = _taskSelectWindowState

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

  fun openNewTaskWindow(taskId: Long) {
    _newTaskWindowStateFlow.value = NewTaskWindowState.Opened()
  }

  fun closeNewTaskWindow() {
    _newTaskWindowStateFlow.value = NewTaskWindowState.Closed
  }

  fun setNewTaskName(name: String) {
    _newTaskWindowStateFlow.value = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened).copy(taskName = name)
  }

  fun editSubtask(subtask: String, index: Int) {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      subtasks = state.subtasks.toMutableList().apply { set(index, subtask) }
    )
  }

  fun addSubtask() {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      subtasks = state.subtasks.toMutableList().apply { add("") }
    )
  }

  fun addConnectedTask(task: TaskItemModel) {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      connectedTasks = state.connectedTasks.toMutableList().apply { add(task) }
    )
  }

  fun openTaskSelectWindow() {
    runBlocking {
      val state = _newTaskWindowStateFlow.value as NewTaskWindowState.Opened
      _taskSelectWindowState.value = TaskSelectWindowState.Opened(
        tasks = tasksListFlow.first()
          .filter { task -> task.id !in state.connectedTasks.map { connectedTask -> connectedTask.id } }
      )
    }
  }

  fun closeTaskSelectWindow() {
    _taskSelectWindowState.value = TaskSelectWindowState.Closed
  }
}

sealed interface NewTaskWindowState {
  object Closed : NewTaskWindowState
  data class Opened(
    val taskName: String = "",
    val subtasks: List<String> = emptyList(),
    val connectedTasks: List<TaskItemModel> = emptyList(),
    val error: String? = null,
  ) : NewTaskWindowState
}

sealed interface TaskSelectWindowState {
  object Closed : TaskSelectWindowState
  data class Opened(
    val tasks: List<TaskItemModel>,
  ) : TaskSelectWindowState
}