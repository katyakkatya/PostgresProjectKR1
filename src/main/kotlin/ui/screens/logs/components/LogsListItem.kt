import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
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
    log.type == LogType.ERROR -> Color.Red
    else -> Color.Black
  }

  Column {
    Text(
      text = log.message,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      fontSize = 18.sp,
      color = textColor,
      fontFamily = FontFamily.Monospace
    )

    if (!isLast) {
      Divider(
        modifier = Modifier.padding(horizontal = 16.dp),
        color = Color.LightGray,
        thickness = 1.dp
      )
    }
  }
}