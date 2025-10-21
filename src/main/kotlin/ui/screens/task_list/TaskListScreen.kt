package ui.screens.task_list

import AddTaskFloatingButton
import DefaultTopAppBar
import ExpandedTopAppBar
import TaskListContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester

@Composable
fun TaskListScreen(
  viewModel: TaskListViewModel,
  onSettingsClick: () -> Unit,
  onTaskClick: (Long) -> Unit,
  onLogsClicked: () -> Unit,
) {
  val tasks by viewModel.tasksListFlow.collectAsState(emptyList())
  var showDialog by remember { mutableStateOf(false) }
  val expandedTopAppBarState by viewModel.expandedTopAppBarStateFlow.collectAsState(false)
  var searchQuery by remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }
  val appliedFilters by viewModel.statusFilterFlow.collectAsState(emptySet())

  Scaffold(
    topBar = {
      if (expandedTopAppBarState == ExpandedTopAppBarState.Opened){
        ExpandedTopAppBar(
          searchQuery = searchQuery,
          onSearchQueryChanged = { searchQuery = it },
          focusRequester = focusRequester,
          onClose = {viewModel.closeExpandedTopAppBar()}
        )
      } else{
        DefaultTopAppBar(
          appliedFilters,
          onFilterToggled = {status -> viewModel.toggleStatusFilter(status)},
          onFilterReset = { viewModel.resetFilters() },
          onLogsClicked = onLogsClicked,
          onSearchClicked = {viewModel.openExpandedTopAppBar()},
          onSettingsClicked = onSettingsClick
        )
      }
    },
    floatingActionButton = {
      AddTaskFloatingButton(
        onClick = { viewModel.openNewTaskWindow() }
      )
    }
  ) { innerPadding ->
    TaskListContent(
      innerPadding = innerPadding,
      tasks = tasks,
      onTaskClick = onTaskClick,
      showDialog = showDialog,
      onDismissDialog = { }
    )
  }

  val newTaskState by viewModel.newTaskWindowStateFlow.collectAsState()
  NewTaskDialog(
    newTaskState,
    onTaskNameChanged = viewModel::setNewTaskName,
    onSubtaskDeleted = viewModel::onSubtaskDeleted,
    onSubtaskChanged = viewModel::editSubtask,
    onSubtaskAdded = viewModel::addSubtask,
    onTaskSelectWindowOpened = viewModel::openTaskSelectWindow,
    onConnectedTaskDeleted = viewModel::removeConnectedTask,
    onNewTaskSaved = viewModel::saveNewTask,
    onNewTaskClosed = viewModel::closeNewTaskWindow,
  )

  val taskSelectionState by viewModel.taskSelectWindowState.collectAsState()
  SelectConnectedTaskDialog(
    taskSelectionState,
    onWindowClosed = viewModel::closeTaskSelectWindow,
    onTaskSelected = viewModel::addConnectedTask,
  )
}