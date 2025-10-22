import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
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
  BoxWithConstraints(
    modifier = Modifier.fillMaxWidth()
  ) {
    val calculatedHeight = remember(maxHeight) {
      val tenPercent = maxHeight * 0.1f
      tenPercent.coerceIn(60.dp, 120.dp)
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.primary)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(calculatedHeight)
          .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
          onClick = onBack,
          modifier = Modifier.size(48.dp)
        ) {
          Icon(
            Icons.Default.ArrowBack,
            "Назад",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(48.dp)
          )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          text = "Детали задачи",
          fontSize = 32.sp,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Left,
          color = MaterialTheme.colors.onPrimary,
          style = MaterialTheme.typography.h4
        )

        IconButton(
          onClick = onDeleteClicked,
          modifier = Modifier.size(48.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Удалить задачу",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(48.dp)
          )
        }
        Spacer(modifier = Modifier.width(16.dp))
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
}