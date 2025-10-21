package ui.screens.task_detail

import AddSubtaskWindow
import DeletionWindow
import RelatedTasksSection
import SubtasksSection
import TaskHeader
import TaskScreenContent
import TaskStatusInfo
import TaskTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
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
  val task by viewModel.taskFlow.collectAsState(null)

  task?.let { task ->
    Scaffold(
      topBar = {
        TaskTopAppBar(task = task, onDeleteClicked = { viewModel.openDeletionWindow() }, onBack = onBack)
      }
    ) { innerPadding ->
      TaskScreenContent(
        innerPadding = innerPadding,
        task = task,
        onRelatedTaskClick = onRelatedTaskClick,
        onSubtaskToggled = { index ->
          viewModel.toggleSubtask(index)
        },
        onStatusChanged = { status ->
          viewModel.updateStatus(status)
        },
        onAddSubtaskClick = { viewModel.openSubtaskWindow() },
        onAddRelatedTaskClick = { viewModel.openRelatedTaskWindow() }
      )
    }
  }

  val subtaskState by viewModel.newSubtaskState.collectAsState(NewSubtaskState.Closed)

  when (subtaskState) {
    is NewSubtaskState.Opened -> {
      AddSubtaskWindow(viewModel)
    }

    NewSubtaskState.Closed -> Unit
  }

  val relatedTasksState by viewModel.relatedTasksStateFlow.collectAsState(NewRelatedTaskState.Closed)
  when (relatedTasksState) {
    is NewRelatedTaskState.Opened -> {
      AddRelatedTaskWindow(
        state = relatedTasksState as NewRelatedTaskState.Opened,
        onWindowClosed = {
          viewModel.closeRelatedTaskWindow()
        },
        onTaskSelected = { task ->
          viewModel.addRelatedTask(task.id)
        }
      )
    }

    NewRelatedTaskState.Closed -> Unit
  }

  val deletionWindowOpened by viewModel.deletionWindowOpenedFlow.collectAsState(false)
  if (deletionWindowOpened) {
    DeletionWindow(viewModel)
  }
}