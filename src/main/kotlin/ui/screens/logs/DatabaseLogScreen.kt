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
      DatabaseLogTopBar(onBack = onBack)
    }
  ) {
    DatabaseLogContent(
      logs = logs.filter { if (showOnlyErrors) it.type == LogType.ERROR else true },
      showOnlyErrors = showOnlyErrors,
      onOnlyErrorsToggled = { viewModel.toggleOnlyErrors() }
    )
  }
}

@Composable
private fun DatabaseLogTopBar(onBack: () -> Unit){
  TopAppBar(
    modifier = Modifier.height(70.dp),
    backgroundColor = Color.Gray,
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Text(
        text = "Логирование",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        modifier = Modifier.align(Alignment.Center)
      )

      Box(
        modifier = Modifier.align(Alignment.CenterStart)
      ) {
        IconButton(
          onClick = onBack // Используем переданную функцию
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Назад",
            modifier = Modifier.size(48.dp),
            tint = Color.White
          )
        }
      }
    }
  }
}

@Composable
private fun DatabaseLogContent(
  logs: List<LogModel>,
  showOnlyErrors: Boolean,
  onOnlyErrorsToggled: () -> Unit,
) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.LightGray)
  ){
    LogHeader(
      checked = showOnlyErrors,
      onCheckedChange = onOnlyErrorsToggled
    )
    LogList(logs = logs)
  }
}

@Preview
@Composable
private fun LogHeader(
  checked: Boolean,
  onCheckedChange: () -> Unit
){
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 32.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ){
    Text(
      text = "Показывать только ошибки: ",
      fontFamily = FontFamily.SansSerif,
      fontSize = 28.sp,
      fontWeight = FontWeight.W400
    )
    Checkbox(
      checked = checked,
      onCheckedChange = { onCheckedChange() }
    )
  }
}

@Composable
private fun LogList(
  logs: List<LogModel>
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 32.dp)
      .padding(bottom = 40.dp)
      .clip(RoundedCornerShape(24.dp))
      .background(color = Color.White)
      .verticalScroll(rememberScrollState())
  ) {
    if (logs.isEmpty()) {
      Text(
        text = "Нет логов для отображения",
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
      )
    } else {
      logs.forEachIndexed { index, log ->
        LogItem(
          log = log,
          isLast = index == logs.size - 1
        )
      }
    }
  }
}

@Composable
private fun LogItem(
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