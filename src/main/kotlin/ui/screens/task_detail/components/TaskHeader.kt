import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.TaskDetail

@Composable
fun TaskHeader(task: TaskDetail) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = task.title,
      fontSize = 32.sp,
      fontFamily = FontFamily.Serif,
      fontWeight = FontWeight.Bold
    )

    Text(
      text = "${(task.progress * 100).toInt()}%",
      fontSize = 28.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.Serif,
      color = when {
        task.progress >= 1f -> Color(0xFF27AE60)
        task.progress > 0f -> Color(0xFF3498DB)
        else -> Color(0xFFE74C3C)
      }
    )
  }
}