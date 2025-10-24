package ui.screens.task_list

import database.model.DbTaskStatus
import database.request.FormattingOptions
import database.request.TaskListRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.FormattingOptionsModel
import models.HeightTransformation
import models.TaskItemModel
import models.UserModel
import repository.Settings
import repository.TodoRepository
import ui.screens.common.dialogs.UserSelectDialogState

class TaskListViewModel(
  private val todoRepository: TodoRepository,
) {
  val tasksListFlow = todoRepository.tasksListFlow

  private var settings = Settings.DEFAULT

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

  private val _formattingOptionsModelFlow = MutableStateFlow(FormattingOptionsModel())
  val formattingOptionsModelFlow = _formattingOptionsModelFlow

  private val _usersSelectDialogStateFlow: MutableStateFlow<UserSelectDialogState> =
    MutableStateFlow(UserSelectDialogState.Closed)
  val usersSelectDialogStateFlow = _usersSelectDialogStateFlow

  fun openExpandedTopAppBar(){
    _expandedTopAppBarStateFlow.value = ExpandedTopAppBarState.Opened
  }

  fun closeExpandedTopAppBar(){
    _expandedTopAppBarStateFlow.value = ExpandedTopAppBarState.Closed
  }

  fun onInit() {
    updateList()
    updateSettings()
  }

  private fun updateList() {
    val formattingOptionsModel = FormattingOptions(
      _formattingOptionsModelFlow.value.heightTransformation == HeightTransformation.UPPERCASE,
      _formattingOptionsModelFlow.value.heightTransformation == HeightTransformation.LOWERCASE,
      _formattingOptionsModelFlow.value.showShort,
      _formattingOptionsModelFlow.value.displayId,
      _formattingOptionsModelFlow.value.displayFullStatus
    )
    todoRepository.getTasksList(TaskListRequest(_statusFilterFlow.value.toList(), null, null, formattingOptionsModel))
  }

  private fun updateSettings() {
    CoroutineScope(Dispatchers.IO).launch {
      settings = todoRepository.settingsFlow.first()
    }
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

  fun openAuthorSelectDialog() {
    todoRepository.getAllUsers()
    CoroutineScope(Dispatchers.IO).launch {
      val users = todoRepository.usersListFlow.first()
      _usersSelectDialogStateFlow.value = UserSelectDialogState.Opened(users)
    }
  }

  fun closeAuthorSelectDialog() {
    _usersSelectDialogStateFlow.value = UserSelectDialogState.Closed
  }

  fun setNewTaskAuthor(author: UserModel) {
    _newTaskWindowStateFlow.value = (_newTaskWindowStateFlow.value as NewTaskWindowState.Opened).copy(author = author)
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
      if (subtask.trim().length < settings.minTaskTitleLength) {
        _newTaskWindowStateFlow.value = state.copy(error = "Минимальная длина ${settings.minTaskTitleLength}")
        return false
      } else if (subtask.trim().length > settings.maxTaskTitleLength) {
        _newTaskWindowStateFlow.value = state.copy(error = "Максимальная длина ${settings.maxTaskTitleLength}")
        return false
      }
    }
    if (state.taskName.trim().length < settings.minTaskTitleLength) {
      _newTaskWindowStateFlow.value = state.copy(error = "Минимальная длина ${settings.minTaskTitleLength}")
      return false
    } else if (state.taskName.trim().length > settings.maxTaskTitleLength) {
      _newTaskWindowStateFlow.value = state.copy(error = "Максимальная длина ${settings.maxTaskTitleLength}")
      return false
    }
    return true
  }

  fun setHeightTransformation(heightTransformation: HeightTransformation) {
    _formattingOptionsModelFlow.value =
      _formattingOptionsModelFlow.value.copy(heightTransformation = heightTransformation)
    updateList()
  }

  fun onShowShortToggled() {
    _formattingOptionsModelFlow.value =
      _formattingOptionsModelFlow.value.copy(showShort = !_formattingOptionsModelFlow.value.showShort)
    updateList()
  }

  fun onDisplayIdToggled() {
    _formattingOptionsModelFlow.value =
      _formattingOptionsModelFlow.value.copy(displayId = !_formattingOptionsModelFlow.value.displayId)
    updateList()
  }

  fun onDisplayFullStatusToggled() {
    _formattingOptionsModelFlow.value =
      _formattingOptionsModelFlow.value.copy(displayFullStatus = !_formattingOptionsModelFlow.value.displayFullStatus)
    updateList()
  }
}

sealed interface NewTaskWindowState {
  object Closed : NewTaskWindowState
  data class Opened(
    val taskName: String = "",
    val subtasks: List<String> = emptyList(),
    val connectedTasks: List<TaskItemModel> = emptyList(),
    val error: String? = null,
    val author: UserModel? = null,
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