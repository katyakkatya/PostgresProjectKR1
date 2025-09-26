import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screens.database_log.LogEntry

@Composable
fun DatabaseLogScreen(
  onBack: () -> Unit = {} // Добавил параметр для навигации назад
){
  Scaffold(
    topBar = {
      DatabaseLogTopBar(onBack = onBack) // Убрал лишний TopAppBar
    }
  ){ innerPadding ->
    DatabaseLogContent(innerPadding = innerPadding)
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
private fun DatabaseLogContent(innerPadding: PaddingValues){
  var showOnlyErrors by remember { mutableStateOf(false) }

  val testLogs = listOf(
    LogEntry.fromString("2024-01-15 10:30:25 INFO: Application started successfully"),
    LogEntry.fromString("2024-01-15 10:30:26 DEBUG: Database connection established"),
    LogEntry.fromString("2024-01-15 10:30:27 INFO: User authentication successful"),
    LogEntry.fromString("2024-01-15 10:30:28 WARN: Cache size approaching limit"),
    LogEntry.fromString("2024-01-15 10:30:29 ERROR: Failed to load user preferences"),
    LogEntry.fromString("2024-01-15 10:30:30 INFO: Task synchronization completed"),
    LogEntry.fromString("2024-01-15 10:30:31 DEBUG: API request sent to server"),
    LogEntry.fromString("2024-01-15 10:30:32 INFO: Received 15 tasks from server"),
    LogEntry.fromString("2024-01-15 10:30:33 ERROR: Network timeout occurred"),
    LogEntry.fromString("2024-01-15 10:30:34 INFO: Retrying connection attempt 1/3")
  )

  val filteredLogs = if (showOnlyErrors) {
    testLogs.filter { it.isError }
  } else {
    testLogs
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(innerPadding)
      .background(color = Color.LightGray)
  ){
    LogHeader(
      checked = showOnlyErrors,
      onCheckedChange = { showOnlyErrors = it }
    )
    LogList(logs = filteredLogs) // Используем отфильтрованные логи
  }
}

@Preview
@Composable
private fun LogHeader(
  checked: Boolean = false,
  onCheckedChange: (Boolean) -> Unit = {}
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
      onCheckedChange = onCheckedChange
    )
  }
}

// ОСТАВЛЯЕМ ТОЛЬКО ОДНУ ФУНКЦИЮ LogList (удаляем дубликат)

@Composable
private fun LogList(
  logs: List<LogEntry>
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
  log: LogEntry,
  isLast: Boolean
) {
  val textColor = when {
    log.isError -> Color.Red
    log.message.contains("WARN", ignoreCase = true) -> Color(0xFFFFA500) // Orange
    log.message.contains("DEBUG", ignoreCase = true) -> Color.Blue
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