package ui.screens.task_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Subtask
import models.TaskDetail
import models.TaskItemModel
import models.TaskStatus
import ui.components.SubTaskList
import ui.components.TaskList
import ui.dialogs.AddSubTaskDialog

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
                TaskScreenTopBar(onBack = onBack)
            },
            floatingActionButton = {
                AddSubTaskFloatingButton(
                    onClick = { showDialog = true }
                )
            }
        ) { innerPadding ->
            TaskScreenContent(
                innerPadding = innerPadding,
                task = task,
                showDialog = showDialog,
                onDismissDialog = { showDialog = false },
                onRelatedTaskClick = onRelatedTaskClick
            )
        }
    }
}

@Composable
private fun TaskScreenTopBar(onBack: () -> Unit) {
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
                onClick = { /* Логика комментариев */ },
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Комментарии",
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )
            }
        }

        LinearProgressIndicator(
            progress = 0.5f,
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

// TODO: раскомментировать когда соединю с репозиторием
@Composable
private fun TaskScreenContent(
  innerPadding: PaddingValues,
  task: TaskDetail,
  showDialog: Boolean,
  onDismissDialog: () -> Unit,
  onRelatedTaskClick: (Long) -> Unit
) {
    //val allTasks = remember { TestData.sampleTasks }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .padding(innerPadding)
            .padding(24.dp)
    ) {
        TaskHeader(task = task)
        TaskStatusInfo(task = task)
        SubtasksSection(subtasks = task.subtasks)
        RelatedTasksSection(
            relatedTasks = task.relatedTasks,
            onRelatedTaskClick = onRelatedTaskClick
        )
        AddSubTaskDialog(
            showDialog = showDialog,
            onDismiss = onDismissDialog,
            onAddNewSubtask = { title ->
            },
            onLinkExistingTask = { task ->
            },
            allTasks = listOf()
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
private fun TaskStatusInfo(task: TaskDetail) {
    Text(
        text = "Статус: ${getStatusName(task.status)}",
        fontFamily = FontFamily.Serif,
        fontSize = 20.sp,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
private fun SubtasksSection(subtasks: List<Subtask>) {
    Text(
        text = "Подзадачи:",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    SubTaskList(subtasks = subtasks)
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