package ui.screens.task_list

import AddTaskDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import database.model.DbTaskStatus
import models.TaskItemModel
import models.asTaskStatus
import ui.components.TaskList

@Composable
fun TaskListScreen(
  viewModel: TaskListViewModel,
  onTaskClick: (Long) -> Unit
) {
  val tasks by viewModel.tasksListFlow.collectAsState(emptyList())
  var showDialog by remember { mutableStateOf(false) }
  var filtersModalOpened by remember { mutableStateOf(false) }
  val appliedFilters by viewModel.statusFilterFlow.collectAsState(emptySet())

  Scaffold(
    topBar = {
      TaskListTopBar()
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

// TODO: компонент для фильтра
@Composable
fun FilterStatusItem(
  enabled: Boolean,
  status: DbTaskStatus,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier.clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (enabled) {
      Text("вкл")
    }
    Text(status.name)
  }
}

@Composable
private fun TaskListTopBar() {
  var expanded by remember { mutableStateOf(false) }
  var anchorBounds by remember { mutableStateOf<Rect?>(null) }

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
          onClick = { expanded = true },
          modifier = Modifier.onGloballyPositioned { coordinates ->
            anchorBounds = coordinates.boundsInRoot()
          }
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
          modifier = Modifier.width(280.dp)
        ) {
          FiltersPopupContent( onDismiss = { expanded = false })
        }
      }
    }
  }
}

@Composable
fun FiltersPopupContent(
  onDismiss: () -> Unit
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    // Заголовок
    Text(
      text = "Фильтры по статусу",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
    )

    Divider(modifier = Modifier.padding(bottom = 8.dp))

    // Список статусов
    val statuses = listOf(
      DbTaskStatus.BACKLOG, DbTaskStatus.IN_PROGRESS, DbTaskStatus.IN_REVIEW,
      DbTaskStatus.DONE, DbTaskStatus.DROPPED
    )

    statuses.forEach { status ->
      FilterStatusItem(
        enabled = false,
        status = status,
        onClick = {
//          viewModel.toggleStatusFilter(status)
          // onDismiss() // можно закрывать после выбора или оставлять открытым
        }
      )
    }

    Divider(modifier = Modifier.padding(top = 8.dp))

    // Кнопки действий
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      TextButton(onClick = {
      }) {
        Text("Сбросить")
      }

      Button(onClick = onDismiss) {
        Text("Готово")
      }
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