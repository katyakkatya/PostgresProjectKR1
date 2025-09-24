// Navigation.kt
package ui

import Globals
import MainViewModel
import androidx.compose.runtime.*
import screens.TaskScreen
import ui.Screen.*
import ui.screens.connection.ConnectionScreen
import ui.screens.database_creation.DatabaseCreationScreen
import ui.screens.task_list.TaskListScreen

sealed interface Screen {
  object Connection : Screen
  object DatabaseCreation : Screen
  object TaskList : Screen
  data class TaskDetail(val taskId: Long) : Screen
}

@Composable
fun AppNavigation(
  mainViewModel: MainViewModel,
) {
  var currentScreen by remember { mutableStateOf<Screen>(Screen.Connection) }
  val errorMessage by mainViewModel.errorMessageFlow.collectAsState(null)

  when (val screen = currentScreen) {
    Screen.Connection -> {
      ConnectionScreen(
        viewModel = Globals.connectionViewModel,
        onConnectionSuccess = { currentScreen = DatabaseCreation }
      )
    }

    Screen.DatabaseCreation -> {
      DatabaseCreationScreen(
        viewModel = Globals.databaseCreationViewModel,
        onDatabaseCreated = { currentScreen = TaskList }
      )
    }

    is Screen.TaskList -> {
      TaskListScreen(
        viewModel = Globals.taskListViewModel,
        onTaskClick = { id ->
          currentScreen = TaskDetail(id)
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

  if (errorMessage != null) {
    // TODO: Показывать модалку с текстом ошибки и кнопкой ок, при закрытии вызвать mainViewModel.onMessageSeen()
  }
}