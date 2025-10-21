package ui.screens.task_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Subtask

@Composable
fun SubTaskCard(
  subtask: Subtask,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(4.dp))
      .clickable { onClick() },
    shape = RoundedCornerShape(4.dp),
    elevation = 2.dp,
    backgroundColor = if (subtask.isCompleted)
      MaterialTheme.colors.primary.copy(alpha = 0.1f)
    else
      MaterialTheme.colors.surface
  ) {
    Row(
      modifier = Modifier.padding(24.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Checkbox(
        checked = subtask.isCompleted,
        onCheckedChange = { onClick() },
        modifier = Modifier.size(48.dp),
        colors = CheckboxDefaults.colors(
          checkedColor = MaterialTheme.colors.primary,
          uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
          checkmarkColor = MaterialTheme.colors.onPrimary
        )
      )
      Text(
        text = subtask.title,
        modifier = Modifier
          .weight(1f)
          .padding(start = 16.dp),
        fontSize = 28.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = MaterialTheme.typography.h6.fontFamily,
        color = if (subtask.isCompleted)
          MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        else
          MaterialTheme.colors.onSurface,
        textDecoration = if (subtask.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
        style = MaterialTheme.typography.h6
      )
    }
  }
}