import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.TaskDetail

@Composable
fun TaskTopAppBar(
  task: TaskDetail,
  onDeleteClicked: () -> Unit,
  onBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(MaterialTheme.colors.primary)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Назад",
          modifier = Modifier.size(48.dp),
          tint = MaterialTheme.colors.onPrimary
        )
      }

      Text(
        text = "Детали задачи",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onPrimary,
        style = MaterialTheme.typography.h4
      )

      IconButton(
        onClick = onDeleteClicked,
        modifier = Modifier.size(64.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Delete,
          contentDescription = "Удалить задачу",
          modifier = Modifier.size(48.dp),
          tint = MaterialTheme.colors.onPrimary
        )
      }
    }

    LinearProgressIndicator(
      progress = task.progress,
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp),
      color = MaterialTheme.colors.secondary,
      backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.3f)
    )
  }
}