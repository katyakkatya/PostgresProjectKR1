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
import models.Subtask
import ui.components.SubTaskList

@Composable
fun SubtasksSection(subtasks: List<Subtask>, onItemClick: (Int) -> Unit, onAddSubtaskClick: () -> Unit) {
  Text(
    text = "Подзадачи:",
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.Serif,
    modifier = Modifier.padding(vertical = 8.dp)
  )
  if (subtasks.isNotEmpty()) {
    SubTaskList(subtasks = subtasks, onItemClick = onItemClick)
  } else {
    Text(
      text = "Нет подзадач",
      fontSize = 20.sp,
      textAlign = TextAlign.Center
    )
  }
  Button(
    onClick = onAddSubtaskClick,
    shape = RoundedCornerShape(16.dp),
    modifier = Modifier.padding(24.dp),
    colors = ButtonDefaults.buttonColors(
      backgroundColor = Color.Gray,
      contentColor = Color.Transparent
    ),
    elevation = null
  ) {
    Text(
      text = "Добавить подзадачу",
      fontFamily = FontFamily.SansSerif,
      fontSize = 24.sp,
      fontWeight = FontWeight.W500,
      color = Color.White,
      modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp)
    )
  }
}