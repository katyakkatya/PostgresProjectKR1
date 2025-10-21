import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
      .background(color = MaterialTheme.colors.background)
  ) {
    LogsHeader(
      checked = showOnlyErrors,
      onCheckedChange = onOnlyErrorsToggled
    )
    LogsList(logs = logs)
  }
}