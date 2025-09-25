package ui.screens.task_list

import AddTaskDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        onClick = { showDialog = true }
      )
    }
  ) { innerPadding ->
    TaskListContent(
      innerPadding = innerPadding,
      tasks = tasks,
      onTaskClick = onTaskClick,
      showDialog = showDialog,
      onDismissDialog = { showDialog = false }
    )
  }

  Column {
    // TODO: заменить бокс на нормальную кнопку фильтров
    Text(
      modifier = Modifier.clickable {
        filtersModalOpened = !filtersModalOpened
      },
      text = "открыть фильтр"
    )
    if (filtersModalOpened) {
      listOf(
        DbTaskStatus.BACKLOG, DbTaskStatus.IN_PROGRESS, DbTaskStatus.IN_REVIEW,
        DbTaskStatus.DONE, DbTaskStatus.DROPPED
      ).forEach { status ->
        val color = status.asTaskStatus().color
        FilterStatusItem((status in appliedFilters), status) { viewModel.toggleStatusFilter(status) }
      }
    }
  }
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
  TopAppBar(
    modifier = Modifier.height(70.dp),
    backgroundColor = Color.Gray,
    content = {
      Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "TODO",
          fontSize = 32.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color.White
        )
      }
    }
  )
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