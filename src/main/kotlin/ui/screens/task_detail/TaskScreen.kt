package ui.screens.task_detail

import AddSubtaskWindow
import DeletionWindow
import TaskScreenContent
import TaskTopAppBar
import androidx.compose.material.*
import androidx.compose.runtime.*

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