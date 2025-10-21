package ui.screens.task_list

import database.model.DbTaskStatus
import database.request.FormattingOptions
import database.request.TaskListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import models.TaskItemModel
import repository.TodoRepository

class TaskListViewModel(
  private val todoRepository: TodoRepository,
) {
  val tasksListFlow = todoRepository.tasksListFlow

  private val _expandedTopAppBarStateFlow: MutableStateFlow<ExpandedTopAppBarState> =
    MutableStateFlow(ExpandedTopAppBarState.Closed)

  val expandedTopAppBarStateFlow = _expandedTopAppBarStateFlow

  private val _newTaskWindowStateFlow: MutableStateFlow<NewTaskWindowState> =
    MutableStateFlow(NewTaskWindowState.Closed)
  val newTaskWindowStateFlow = _newTaskWindowStateFlow

  private val _taskSelectWindowState: MutableStateFlow<TaskSelectWindowState> =
    MutableStateFlow(TaskSelectWindowState.Closed)
  val taskSelectWindowState = _taskSelectWindowState

  private val initialFilters = mutableSetOf(
    DbTaskStatus.BACKLOG, DbTaskStatus.IN_PROGRESS, DbTaskStatus.IN_REVIEW, DbTaskStatus.DONE, DbTaskStatus.DROPPED
  )
  private val _statusFilterFlow = MutableStateFlow(initialFilters)
  val statusFilterFlow = _statusFilterFlow

  fun openExpandedTopAppBar(){
    _expandedTopAppBarStateFlow.value = ExpandedTopAppBarState.Opened
  }

  fun closeExpandedTopAppBar(){
    _expandedTopAppBarStateFlow.value = ExpandedTopAppBarState.Closed
  }

  fun onInit() {
    updateList()
  }

  private fun updateList() {
    todoRepository.getTasksList(TaskListRequest(_statusFilterFlow.value.toList(), null, null, FormattingOptions(false, false, false, false, false)))
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

  fun openNewTaskWindow() {
    _newTaskWindowStateFlow.value = NewTaskWindowState.Opened()
  }

  fun closeNewTaskWindow() {
    _newTaskWindowStateFlow.value = NewTaskWindowState.Closed
  }

  fun setNewTaskName(name: String) {
    _newTaskWindowStateFlow.value = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened).copy(taskName = name)
  }

  fun editSubtask(index: Int, subtask: String) {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      subtasks = state.subtasks.toMutableList().apply { set(index, subtask) })
  }

  fun addSubtask() {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      subtasks = state.subtasks.toMutableList().apply { add("") })
  }

  fun addConnectedTask(task: TaskItemModel) {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      connectedTasks = state.connectedTasks.toMutableList().apply { add(task) })
  }

  fun removeConnectedTask(index: Int) {
    val state = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened)
    _newTaskWindowStateFlow.value = state.copy(
      connectedTasks = state.connectedTasks.toMutableList().apply { removeAt(index) })
  }

  fun openTaskSelectWindow() {
    runBlocking {
      val state = _newTaskWindowStateFlow.value as NewTaskWindowState.Opened
      _taskSelectWindowState.value = TaskSelectWindowState.Opened(
        tasks = tasksListFlow.first()
          .filter { task -> task.id !in state.connectedTasks.map { connectedTask -> connectedTask.id } })
    }
  }

  fun closeTaskSelectWindow() {
    _taskSelectWindowState.value = TaskSelectWindowState.Closed
  }

  fun onSubtaskDeleted(index: Int) {
    _newTaskWindowStateFlow.value = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened).copy(
      subtasks = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened).subtasks.toMutableList()
        .apply { removeAt(index) })
  }

  fun saveNewTask() {
    val state = _newTaskWindowStateFlow.value as NewTaskWindowState.Opened
    _newTaskWindowStateFlow.value = state.copy(error = null)
    if (!validateNewTask(state)) {
      return
    }
    val result = todoRepository.saveNewTask(state.taskName, state.subtasks, state.connectedTasks.map { it.id })
    if (result.success) {
      closeNewTaskWindow()
      updateList()
    }
  }

  fun resetFilters() {
    _statusFilterFlow.value = initialFilters
    updateList()
  }

  private fun validateNewTask(state: NewTaskWindowState.Opened): Boolean {
    state.subtasks.forEach { subtask ->
      if (subtask.trim().length < 3) {
        _newTaskWindowStateFlow.value = state.copy(error = "Подзача должна быть минимум 3 символа")
        return false
      } else if (subtask.trim().length >= 100) {
        _newTaskWindowStateFlow.value = state.copy(error = "Подзача должна быть максимум 100 символов")
        return false
      }
    }
    if (state.taskName.trim().length < 1) {
      _newTaskWindowStateFlow.value = state.copy(error = "Название должно быть минимум 3 символа")
      return false
    } else if (state.taskName.trim().length >= 100) {
      _newTaskWindowStateFlow.value = state.copy(error = "Название должно быть максимум 100 символов")
      return false
    }
    return true
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

sealed interface ExpandedTopAppBarState{
  object Closed : ExpandedTopAppBarState
  object Opened : ExpandedTopAppBarState
}