import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import models.TaskItemModel
import ui.screens.task_list.components.TaskList

@Composable
fun TaskListContent(
  innerPadding: PaddingValues,
  tasks: List<TaskItemModel>,
  onTaskClick: (Long) -> Unit,
  showDialog: Boolean,
  onDismissDialog: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background)
      .padding(innerPadding)
  ) {
    if (tasks.isEmpty()) {
      Text(
        text = "Вы еще не создали задач",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        style = MaterialTheme.typography.body1
      )
    }
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