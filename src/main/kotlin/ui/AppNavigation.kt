// Navigation.kt
package ui

import Globals
import MainViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.Screen.*
import ui.screens.connection.ConnectionScreen
import ui.screens.database_creation.DatabaseCreationScreen
import ui.screens.task_detail.TaskScreen
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

  val taskStack = remember { mutableStateListOf<Long>() }

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
          taskStack.add(id)
          currentScreen = TaskDetail(id)
        }
      )
    }

    is Screen.TaskDetail -> {
      val viewModel = remember(screen.taskId) { Globals.taskDetailViewModelFactory(screen.taskId) }
      TaskScreen(
        viewModel = viewModel,
        onBack = {
          if (taskStack.isNotEmpty()) {
            taskStack.removeAt(taskStack.size - 1)
            if (taskStack.isNotEmpty()) {
              currentScreen = TaskDetail(taskStack.last())
            } else {
              currentScreen = TaskList
            }
          } else {
            currentScreen = TaskList
          }
        },
        onRelatedTaskClick = { relatedTaskId ->
          taskStack.add(relatedTaskId)
          currentScreen = TaskDetail(relatedTaskId)
        }
      )
    }
  }

  if (errorMessage != null) {
    // TODO: Артем проверяй
    Dialog(onDismissRequest = {}) {
      Surface(
        modifier = Modifier
          .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
      ) {
        Column(
          modifier = Modifier.padding(vertical = 16.dp, horizontal = 64.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "Ошибка",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = Color.DarkGray,
            modifier = Modifier.padding(vertical = 32.dp)
          )

          Text(
            text = errorMessage!!,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(vertical = 32.dp)
          )

          Button(
            onClick = { mainViewModel.onMessageSeen() },
            modifier = Modifier.padding(vertical = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              backgroundColor = Color.Gray,
              contentColor = Color.White
            )
          ) {
            Text(
              text = "OK",
              fontFamily = FontFamily.SansSerif,
              fontSize = 18.sp,
              fontWeight = FontWeight.W400,
              modifier = Modifier.padding(vertical = 12.dp, horizontal = 48.dp)
            )
          }
        }
      }
    }
  }
}