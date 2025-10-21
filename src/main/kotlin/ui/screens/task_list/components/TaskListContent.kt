import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import models.TaskItemModel
import ui.components.TaskList

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
      .background(color = Color.LightGray)
      .padding(innerPadding)
  ) {
    if (tasks.isEmpty()) {
      Text(
        text = "Вы еще не создали задач",
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
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