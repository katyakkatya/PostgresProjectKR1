import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.TaskItemModel
import ui.screens.task_list.components.TaskList

@Composable
fun RelatedTasksSection(
  relatedTasks: List<TaskItemModel>,
  onRelatedTaskClick: (Long) -> Unit,
  onAddRelatedTaskClick: () -> Unit,
) {
  Text(
    text = "Связанные задачи:",
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = MaterialTheme.typography.h5.fontFamily,
    modifier = Modifier.padding(vertical = 16.dp),
    color = MaterialTheme.colors.onSurface,
    style = MaterialTheme.typography.h5
  )
  if (relatedTasks.isNotEmpty()) {
    TaskList(
      tasks = relatedTasks,
      onTaskClick = onRelatedTaskClick,
    )
  } else {
    Text(
      text = "Нет связанных задач",
      textAlign = TextAlign.Center,
      fontSize = 20.sp,
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
      style = MaterialTheme.typography.body1
    )
  }
  Button(
    onClick = onAddRelatedTaskClick,
    shape = RoundedCornerShape(16.dp),
    modifier = Modifier.padding(24.dp),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = MaterialTheme.colors.secondary,
      contentColor = MaterialTheme.colors.onSecondary
    ),
    elevation = ButtonDefaults.elevation(
      defaultElevation = 4.dp,
      pressedElevation = 8.dp
    )
  ) {
    Text(
      text = "Добавить связанную задачу",
      fontSize = 24.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
      style = MaterialTheme.typography.button
    )
  }
}