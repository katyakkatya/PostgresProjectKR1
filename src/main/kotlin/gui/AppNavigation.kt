// Navigation.kt
package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import gui.screens.TaskListScreen
import models.Task
import screens.TaskScreen

sealed class Screen {
    object TaskList : Screen()
    data class TaskDetail(val task: Task) : Screen()
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.TaskList) }

    when (val screen = currentScreen) {
        is Screen.TaskList -> {
            TaskListScreen(
                onTaskClick = { task ->
                    currentScreen = Screen.TaskDetail(task)
                }
            )
        }
        is Screen.TaskDetail -> {
            TaskScreen(
                task = screen.task,
                onBack = { currentScreen = Screen.TaskList },
                onRelatedTaskClick = { relatedTask ->
                    currentScreen = Screen.TaskDetail(relatedTask)
                }
            )
        }
    }
}