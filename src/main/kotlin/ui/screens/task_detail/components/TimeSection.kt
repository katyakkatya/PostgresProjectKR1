package ui.screens.task_detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeSection(
  time: String,
) {
  Text(
    text = time,
    fontSize = 24.sp,
    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
    style = MaterialTheme.typography.body1,
    modifier = Modifier.padding(top = 16.dp),
  )
}