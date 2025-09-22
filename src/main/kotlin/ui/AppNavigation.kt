// Navigation.kt
package ui

import MainViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import ui.screens.task_list.TaskListScreen
import screens.TaskScreen

sealed class Screen {
  object TaskList : Screen()
  data class TaskDetail(val taskId: Long) : Screen()
}

@Composable
fun AppNavigation(
  mainViewModel: MainViewModel,
) {
  var currentScreen by remember { mutableStateOf<Screen>(Screen.TaskList) }
  val errorMessage by mainViewModel.errorMessageFlow.collectAsState(null)

  if (errorMessage != null) {
    // TODO: Show error message popup
  }

  when (val screen = currentScreen) {
    is Screen.TaskList -> {
      TaskListScreen(
        viewModel = Globals.taskListViewModel,
        onTaskClick = { id ->
          currentScreen = Screen.TaskDetail(id)
        }
      )
    }

    is Screen.TaskDetail -> {
      val viewModel = remember(screen.taskId) { Globals.taskDetailViewModelFactory(screen.taskId) }
      TaskScreen(
        viewModel = viewModel,
        onBack = { currentScreen = Screen.TaskList },
        onRelatedTaskClick = { relatedTask ->

        }
      )
    }
  }
}