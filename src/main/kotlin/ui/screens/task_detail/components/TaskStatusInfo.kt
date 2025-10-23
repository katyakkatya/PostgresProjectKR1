import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus
import models.TaskDetail
import models.TaskStatus
import ui.screens.task_detail.StatusUpdateButton
import kotlin.collections.forEachIndexed

@Composable
fun TaskStatusInfo(
  task: TaskDetail,
  onStatusChangeClicked: (status: DbTaskStatus) -> Unit,
) {
  Spacer(modifier = Modifier.height(32.dp))
  Text(
    text = "Статус: ${getStatusName(task.status)}",
    fontFamily = MaterialTheme.typography.h6.fontFamily,
    fontSize = 24.sp,
    color = MaterialTheme.colors.onSurface,
    style = MaterialTheme.typography.h6
  )
  Spacer(modifier = Modifier.height(24.dp))
  val buttons = StatusUpdateButton.buttonMappings[task.status]!!
  buttons.forEachIndexed { index, button ->
    Card(
      modifier = Modifier
        .padding(horizontal = 24.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onStatusChangeClicked(button.toStatus) },
      shape = RoundedCornerShape(8.dp),
      border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
      elevation = 8.dp,
      backgroundColor = MaterialTheme.colors.surface
    ) {
      Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(button.color)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = button.text,
          modifier = Modifier,
          fontSize = 24.sp,
          fontWeight = FontWeight.Normal,
          fontFamily = MaterialTheme.typography.body1.fontFamily,
          color = MaterialTheme.colors.onSurface,
          textDecoration = TextDecoration.None,
          style = MaterialTheme.typography.body1
        )
      }
    }
    if (index < buttons.size - 1) {
      Spacer(modifier = Modifier.height(16.dp))
    }
  }
}

private fun getStatusName(status: TaskStatus): String = when (status) {
  TaskStatus.BACKLOG -> "В бэклоге"
  TaskStatus.IN_PROGRESS -> "В процессе"
  TaskStatus.IN_REVIEW -> "На проверке"
  TaskStatus.DONE -> "Выполнено"
  TaskStatus.DROPPED -> "Не будет выполнено"
}