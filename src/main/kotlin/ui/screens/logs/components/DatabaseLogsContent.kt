import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import models.LogModel

@Composable
fun DatabaseLogContent(
  logs: List<LogModel>,
  showOnlyErrors: Boolean,
  onOnlyErrorsToggled: () -> Unit,
) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.LightGray)
  ){
    LogsHeader(
      checked = showOnlyErrors,
      onCheckedChange = onOnlyErrorsToggled
    )
    LogsList(logs = logs)
  }
}