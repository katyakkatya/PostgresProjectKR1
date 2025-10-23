import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Subtask
import ui.screens.task_detail.components.SubTaskList

@Composable
fun SubtasksSection(
  subtasks: List<Subtask>,
  onItemClick: (Int) -> Unit,
  onAddSubtaskClick: () -> Unit
) {
  Spacer(modifier = Modifier.height(32.dp))
  Text(
    text = "Подзадачи:",
    fontSize = 32.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = MaterialTheme.typography.h5.fontFamily,
    color = MaterialTheme.colors.onSurface,
    style = MaterialTheme.typography.h5
  )
  if (subtasks.isNotEmpty()) {
    SubTaskList(subtasks = subtasks, onItemClick = onItemClick)
  } else {
    Text(
      text = "Нет подзадач",
      fontSize = 24.sp,
      textAlign = TextAlign.Center,
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
      style = MaterialTheme.typography.body1
    )
  }
  Row(
    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
    horizontalArrangement = Arrangement.End
  ){
    Button(
      onClick = onAddSubtaskClick,
      shape = RoundedCornerShape(16.dp),
      modifier = Modifier.padding(24.dp),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
      ),
      elevation = ButtonDefaults.elevation(
        defaultElevation = 4.dp,
        pressedElevation = 8.dp
      )
    ) {
      Text(
        text = "Добавить подзадачу",
        fontSize = 32.sp,
        fontWeight = FontWeight.W500,
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
        style = MaterialTheme.typography.button
      )
    }
  }

}