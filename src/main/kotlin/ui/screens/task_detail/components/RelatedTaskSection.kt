import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.TaskItemModel
import ui.components.TaskList

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
    fontFamily = FontFamily.Serif,
    modifier = Modifier.padding(vertical = 16.dp)
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
    )
  }
  Button(
    onClick = onAddRelatedTaskClick,
    shape = RoundedCornerShape(16.dp),
    modifier = Modifier.padding(24.dp),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = Color.Gray,
      contentColor = Color.Transparent
    ),
    elevation = null
  ) {
    Text(
      text = "Добавить связанную задачу",
      fontFamily = FontFamily.SansSerif,
      fontSize = 24.sp,
      fontWeight = FontWeight.W500,
      color = Color.White,
      modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
    )
  }
}