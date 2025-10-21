import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.LogModel
import models.LogType

@Composable
fun LogsListItem(
  log: LogModel,
  isLast: Boolean
) {
  val textColor = when {
    log.type == LogType.ERROR -> MaterialTheme.colors.error
    else -> MaterialTheme.colors.onSurface
  }

  Column {
    Text(
      text = log.message,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      fontSize = 18.sp,
      color = textColor,
      fontFamily = MaterialTheme.typography.body2.fontFamily,
      style = MaterialTheme.typography.body2
    )

    if (!isLast) {
      Divider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        thickness = 1.dp
      )
    }
  }
}