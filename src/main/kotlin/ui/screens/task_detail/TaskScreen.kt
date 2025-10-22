package ui.screens.task_detail

import AddSubtaskWindow
import DeletionWindow
import TaskScreenContent
import TaskTopAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TaskScreen(
  viewModel: TaskDetailViewModel,
  onBack: () -> Unit,
  onRelatedTaskClick: (Long) -> Unit
) {
  val task by viewModel.taskFlow.collectAsState(null)
  Scaffold(
    topBar = {
      TaskTopAppBar(
        taskProgress = task?.progress ?: 0f,
        onDeleteClicked = { viewModel.openDeletionWindow() },
        onBack = onBack
      )
    }
  ) { innerPadding ->
    task?.let { task ->
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