package ui.screens.task_list

import AddTaskDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus
import models.TaskItemModel
import ui.components.TaskList

@Composable
fun TaskListScreen(
  viewModel: TaskListViewModel,
  onTaskClick: (Long) -> Unit
) {
  val tasks by viewModel.tasksListFlow.collectAsState(emptyList())
  var showDialog by remember { mutableStateOf(false) }
  val appliedFilters by viewModel.statusFilterFlow.collectAsState(emptySet())

  Scaffold(
    topBar = {
      TaskListTopBar(
        appliedFilters,
        onFilterToggled = {status -> viewModel.toggleStatusFilter(status)},
        onFilterReset = { viewModel.resetFilters() }
      )
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

@Composable
fun FilterStatusItem(
  enabled: Boolean,
  status: DbTaskStatus,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp, horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = status.name.replace("_", " ").replaceFirstChar { it.uppercase() },
      fontSize = 20.sp
    )

    Checkbox(
      checked = enabled,
      onCheckedChange = { onClick() },
      modifier = Modifier.size(36.dp),
      colors = CheckboxDefaults.colors(
        checkedColor = Color.DarkGray,
        uncheckedColor = Color.Gray
      )
    )
  }
}
@Composable
private fun TaskListTopBar(
  appliedFilters: Set<DbTaskStatus>,
  onFilterToggled: (DbTaskStatus) -> Unit,
  onFilterReset: () -> Unit
) {
  var expanded by remember { mutableStateOf(false) }

  TopAppBar(
    modifier = Modifier.height(70.dp),
    backgroundColor = Color.Gray,
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Text(
        text = "TODO",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        modifier = Modifier.align(Alignment.Center)
      )

      Box(
        modifier = Modifier.align(Alignment.CenterEnd)
      ) {
        IconButton(
          onClick = { expanded = true }
        ) {
          Icon(
            imageVector = Icons.Outlined.FilterList,
            contentDescription = "Фильтр",
            modifier = Modifier.size(48.dp),
            tint = Color.White
          )
        }

        DropdownMenu(
          expanded = expanded,
          onDismissRequest = { expanded = false },
          modifier = Modifier.width(380.dp).clip(RoundedCornerShape(8.dp))
        ) {
          FiltersPopupContent(
            appliedFilters = appliedFilters,
            onFilterToggled = onFilterToggled,
            onFilterReset = onFilterReset
          )
        }
      }
    }
  }
}

@Composable
fun FiltersPopupContent(
  appliedFilters: Set<DbTaskStatus>,
  onFilterToggled: (DbTaskStatus) -> Unit,
  onFilterReset: () -> Unit
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Text(
      text = "Фильтры по статусу",
      fontSize = 28.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Center
    )

    Divider(modifier = Modifier.padding(bottom = 8.dp))

    val statuses = listOf(
      DbTaskStatus.BACKLOG, DbTaskStatus.IN_PROGRESS, DbTaskStatus.IN_REVIEW,
      DbTaskStatus.DONE, DbTaskStatus.DROPPED
    )

    statuses.forEach { status ->
      FilterStatusItem(
        enabled = (status in appliedFilters),
        status = status,
        onClick = { onFilterToggled(status) }
      )
    }

    Divider(modifier = Modifier.padding(top = 8.dp))
    Button(
      onClick = {onFilterReset()},
      modifier = Modifier
        .fillMaxWidth().padding(12.dp),
      shape = RoundedCornerShape(16.dp),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Gray,
        contentColor = Color.White
      )
    ) {
      Text(
        text = "Сбросить",
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(vertical = 8.dp)
      )
    }
  }
}

@Composable
private fun AddTaskFloatingButton(
  onClick: () -> Unit
) {
  FloatingActionButton(
    onClick = onClick,
    modifier = Modifier
      .size(150.dp)
      .padding(24.dp),
    backgroundColor = Color.Gray,
    contentColor = Color.White
  ) {
    Icon(
      modifier = Modifier.size(50.dp),
      imageVector = Icons.Default.Add,
      contentDescription = "Добавить задачу"
    )
  }
}

@Composable
private fun TaskListContent(
  innerPadding: PaddingValues,
  tasks: List<TaskItemModel>,
  onTaskClick: (Long) -> Unit,
  showDialog: Boolean,
  onDismissDialog: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.LightGray)
      .padding(innerPadding)
  ) {
    TaskList(
      tasks = tasks,
      onTaskClick = onTaskClick
    )

    if (showDialog) {
      AddTaskDialog(
        showDialog = showDialog,
        onDismiss = onDismissDialog,
        onConfirm = { /* TODO: Handle task creation */ }
      )
    }
  }
}