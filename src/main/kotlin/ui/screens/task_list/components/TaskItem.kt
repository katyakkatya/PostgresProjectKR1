package ui.screens.task_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.TaskItemModel

@Composable
fun TaskItem(
  task: TaskItemModel,
  onTaskClick: (TaskItemModel) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clickable { onTaskClick(task) },
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
    elevation = 4.dp,
    backgroundColor = MaterialTheme.colors.surface
  ) {
    Row(
      modifier = Modifier.padding(24.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(20.dp)
          .clip(CircleShape)
          .background(color = task.color)
      )
      Column(
        modifier = Modifier
          .weight(1f)
          .padding(start = 16.dp),
      ) {
        Text(
          text = task.title,
          fontSize = 28.sp,
          fontFamily = MaterialTheme.typography.h6.fontFamily,
          fontWeight = FontWeight.W400,
          color = MaterialTheme.colors.onSurface,
          style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = task.time,
          fontSize = 22.sp,
          fontFamily = MaterialTheme.typography.h6.fontFamily,
          fontWeight = FontWeight.W400,
          color = MaterialTheme.colors.onSurface,
          style = MaterialTheme.typography.h6
        )
      }
      CircularProgressIndicator(
        progress = task.progress,
        modifier = Modifier.padding(end = 24.dp),
        color = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        strokeWidth = 5.dp
      )
      IconButton(onClick = { onTaskClick(task) }) {
        Icon(
          modifier = Modifier.size(36.dp),
          imageVector = Icons.AutoMirrored.Filled.ArrowRight,
          contentDescription = "Перейти к задаче",
          tint = MaterialTheme.colors.onSurface
        )
      }
    }
  }
}