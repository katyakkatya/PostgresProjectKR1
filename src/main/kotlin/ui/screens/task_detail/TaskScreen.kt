package ui.screens.task_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import database.model.DbTaskStatus
import models.Subtask
import models.TaskDetail
import models.TaskItemModel
import models.TaskStatus
import ui.components.SubTaskList
import ui.components.TaskList

@Composable
fun TaskScreen(
  viewModel: TaskDetailViewModel,
  onBack: () -> Unit,
  onRelatedTaskClick: (Long) -> Unit
) {
  var showDialog by remember { mutableStateOf(false) }
  val task by viewModel.taskFlow.collectAsState(null)

  task?.let { task ->
    Scaffold(
      topBar = {
        TaskScreenTopBar(task = task, onDeleteClicked = { viewModel.openDeletionWindow() }, onBack = onBack)
      }
    ) { innerPadding ->
      TaskScreenContent(
        innerPadding = innerPadding,
        task = task,
        showDialog = showDialog,
        onDismissDialog = { showDialog = false },
        onRelatedTaskClick = onRelatedTaskClick,
        onSubtaskToggled = { index ->
          viewModel.toggleSubtask(index)
        },
        onStatusChanged = { status ->
          viewModel.updateStatus(status)
        }
      )
    }
  }

  val subtaskState by viewModel.newSubtaskState.collectAsState(NewSubtaskState.Closed)
  // TODO: отрисовать окошко добавления подзадачи

  val deletionWindowOpened by viewModel.deletionWindowOpenedFlow.collectAsState(false)
  if (deletionWindowOpened) {
    Dialog(onDismissRequest = {}) {
      Surface(
        modifier = Modifier
          .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
      ) {
        Column(
          modifier = Modifier.padding(vertical = 16.dp, horizontal = 48.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "Удаление",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = 32.dp)
          )

          Text(
            text = "Задача будет удалена без возможности восстановления.\n\nВы уверены, что хотите это сделать?",
            fontSize = 28.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(vertical = 32.dp),
            textAlign = TextAlign.Center
          )

          Row {
            Button(
              onClick = {viewModel.closeDeletionWindow()},
              modifier = Modifier
                .padding(24.dp).weight(1f),
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Gray,
                contentColor = Color.White
              )
            ) {
              Text(
                text = "Отмена",
                fontFamily = FontFamily.SansSerif,
                fontSize = 22.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(vertical = 12.dp)
              )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
              onClick = {viewModel.deleteTask()},
              modifier = Modifier
                .padding(24.dp).weight(1f),
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
              )
            ) {
              Text(
                text = "Удалить",
                fontFamily = FontFamily.SansSerif,
                fontSize = 22.sp,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(vertical = 12.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun TaskScreenTopBar(
  task: TaskDetail,
  onDeleteClicked: () -> Unit,
  onBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color.Gray)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Назад",
          modifier = Modifier.size(48.dp),
          tint = Color.White
        )
      }

      Text(
        text = "Детали задачи",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center,
        color = Color.White
      )

      IconButton(
        onClick = onDeleteClicked,
        modifier = Modifier.size(64.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = null,
          modifier = Modifier.size(48.dp),
          tint = Color.White
        )
      }
    }

    LinearProgressIndicator(
      progress = task.progress,
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp),
      color = Color(0xFF3498DB),
      backgroundColor = Color.DarkGray.copy(alpha = 0.3f)
    )
  }
}

@Composable
private fun AddSubTaskFloatingButton(onClick: () -> Unit) {
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
      contentDescription = "Добавить подзадачу"
    )
  }
}

@Composable
private fun TaskScreenContent(
  innerPadding: PaddingValues,
  task: TaskDetail,
  showDialog: Boolean,
  onDismissDialog: () -> Unit,
  onSubtaskToggled: (Int) -> Unit,
  onRelatedTaskClick: (Long) -> Unit,
  onStatusChanged: (DbTaskStatus) -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.LightGray)
      .padding(innerPadding)
      .padding(24.dp)
  ) {
    TaskHeader(task = task)
    TaskStatusInfo(task = task) { status ->
      onStatusChanged(status)
    }
    SubtasksSection(subtasks = task.subtasks) { index ->
      onSubtaskToggled(index)
    }
    RelatedTasksSection(
      relatedTasks = task.relatedTasks,
      onRelatedTaskClick = onRelatedTaskClick
    )
  }
}

@Composable
private fun TaskHeader(task: TaskDetail) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = task.title,
      fontSize = 32.sp,
      fontFamily = FontFamily.Serif,
      fontWeight = FontWeight.Bold
    )

    Text(
      text = "${(task.progress * 100).toInt()}%",
      fontSize = 28.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Serif,
      color = when {
        task.progress >= 1f -> Color(0xFF27AE60)
        task.progress > 0f -> Color(0xFF3498DB)
        else -> Color(0xFFE74C3C)
      }
    )
  }
}

@Composable
private fun TaskStatusInfo(
  task: TaskDetail,
  onStatusChangeClicked: (status: DbTaskStatus) -> Unit,
) {
  Text(
    text = "Статус: ${getStatusName(task.status)}",
    fontFamily = FontFamily.Serif,
    fontSize = 20.sp,
    modifier = Modifier.padding(bottom = 16.dp)
  )
  val buttons = StatusUpdateButton.buttonMappings[task.status]!!
  buttons.forEachIndexed { index, button ->
    Card(
      modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .clickable { onStatusChangeClicked(button.toStatus) },
      shape = RoundedCornerShape(4.dp),
      elevation = 8.dp,
      backgroundColor = Color.White
    ) {
      Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier.size(24.dp).clip(CircleShape).background(button.color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = button.text,
          modifier = Modifier,
          fontSize = 20.sp,
          fontWeight = FontWeight.Normal,
          fontFamily = FontFamily.Serif,
          color = Color.Black,
          textDecoration = TextDecoration.None
        )
      }
    }
    if (index < buttons.size - 1) {
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
private fun SubtasksSection(subtasks: List<Subtask>, onItemClick: (Int) -> Unit) {
  Text(
    text = "Подзадачи:",
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.Serif,
    modifier = Modifier.padding(vertical = 8.dp)
  )
  SubTaskList(subtasks = subtasks, onItemClick = onItemClick)
}

@Composable
private fun RelatedTasksSection(
  relatedTasks: List<TaskItemModel>,
  onRelatedTaskClick: (Long) -> Unit
) {
  if (relatedTasks.isNotEmpty()) {
    Text(
      text = "Связные задачи:",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Serif,
      modifier = Modifier.padding(vertical = 16.dp)
    )
    TaskList(
      tasks = relatedTasks,
      onTaskClick = onRelatedTaskClick
    )
  }
}

private fun getStatusName(status: TaskStatus): String = when (status) {
  TaskStatus.BACKLOG -> "В бэклоге"
  TaskStatus.IN_PROGRESS -> "В процессе"
  TaskStatus.IN_REVIEW -> "На проверке"
  TaskStatus.DONE -> "Выполнено"
  TaskStatus.DROPPED -> "Не будет выполнено"
}