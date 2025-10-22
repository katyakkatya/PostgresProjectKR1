package ui.screens.task_list

import AddTaskFloatingButton
import DefaultTopAppBar
import ExpandedTopAppBar
import FiltersSidebar
import TaskListContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import models.FormattingOptionsModel
import ui.screens.task_list.components.FormattingOptionsPayload

@Composable
fun TaskListScreen(
  viewModel: TaskListViewModel,
  onSettingsClick: () -> Unit,
  onTaskClick: (Long) -> Unit,
  onLogsClicked: () -> Unit,
  onUsersClicked: () -> Unit,
) {
  val tasks by viewModel.tasksListFlow.collectAsState(emptyList())
  val expandedTopAppBarState by viewModel.expandedTopAppBarStateFlow.collectAsState(false)
  var searchQuery by remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }
  val appliedFilters by viewModel.statusFilterFlow.collectAsState(emptySet())
  val formattingOptionsModel by viewModel.formattingOptionsModelFlow.collectAsState(FormattingOptionsModel())

  BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
    val isFullScreen = maxWidth >= 1200.dp

    var showFiltersSidebar by remember { mutableStateOf(isFullScreen) }

    LaunchedEffect(isFullScreen) {
      showFiltersSidebar = isFullScreen
    }

    Scaffold(
      topBar = {
        if (expandedTopAppBarState == ExpandedTopAppBarState.Opened) {
          ExpandedTopAppBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
            focusRequester = focusRequester,
            onClose = { viewModel.closeExpandedTopAppBar() }
          )
        } else {
          DefaultTopAppBar(
            appliedFilters,
            onFilterToggled = { status -> viewModel.toggleStatusFilter(status) },
            onFilterReset = { viewModel.resetFilters() },
            onLogsClicked = onLogsClicked,
            onSearchClicked = { viewModel.openExpandedTopAppBar() },
            onSettingsClicked = onSettingsClick,
            onFiltersSidebarToggle = {
              if (!isFullScreen) {
                showFiltersSidebar = !showFiltersSidebar
              }
            },
            onUsersClicked = onUsersClicked,
          )
        }
      },
    ) { paddingValues ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(paddingValues)
      ) {
        Row(modifier = Modifier.fillMaxSize()) {

          Box(
            modifier = Modifier
              .weight(if (showFiltersSidebar) 3f else 1f)
              .fillMaxHeight()
          ) {
            TaskListContent(
              innerPadding = PaddingValues(0.dp),
              tasks = tasks,
              onTaskClick = onTaskClick,
            )

            Box(
              modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
              contentAlignment = Alignment.BottomEnd
            ) {
              AddTaskFloatingButton(
                onClick = { viewModel.openNewTaskWindow() }
              )
            }
          }

          if (showFiltersSidebar) {
            FiltersSidebar(
              onClose = {
                if (!isFullScreen) {
                  showFiltersSidebar = false
                }
              },
              modifier = Modifier.weight(1.5f),
              isPermanent = isFullScreen,
              appliedFilters = appliedFilters,
              onFilterToggled = { status -> viewModel.toggleStatusFilter(status) },
              onFilterReset = { viewModel.resetFilters() },
              formattingOptionsPayload = FormattingOptionsPayload(
                onHeightTransformationClicked = { viewModel.setHeightTransformation(it) },
                formattingOptionsModel = formattingOptionsModel,
                onShowShortClicked = { viewModel.onShowShortToggled() },
                onDisplayIdClicked = { viewModel.onDisplayIdToggled() },
                onDisplayFullStatusClicked = { viewModel.onDisplayFullStatusToggled() },
              )
            )
          }
        }
      }
    }
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

