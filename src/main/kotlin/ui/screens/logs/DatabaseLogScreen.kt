import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.LogModel
import models.LogType
import ui.screens.logs.LogsViewModel

@Composable
fun DatabaseLogScreen(
  viewModel: LogsViewModel,
  onBack: () -> Unit,
) {
  val logs by viewModel.logsFlow.collectAsState(emptyList())
  val showOnlyErrors by viewModel.onlyErrorsFlow.collectAsState(false)
  Scaffold(
    topBar = {
      DatabaseLogTopAppBar(onBack = onBack)
    }
  ) {
    DatabaseLogContent(
      logs = logs.filter { if (showOnlyErrors) it.type == LogType.ERROR else true },
      showOnlyErrors = showOnlyErrors,
      onOnlyErrorsToggled = { viewModel.toggleOnlyErrors() }
    )
  }
}