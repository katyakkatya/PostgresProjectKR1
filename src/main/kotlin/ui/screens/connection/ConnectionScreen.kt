package ui.screens.connection

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ConnectionScreen(
  viewModel: ConnectionViewModel,
  onConnectionSuccess: () -> Unit,
) {
  val url by viewModel.urlFlow.collectAsState()
  Text("подключение", style = TextStyle(fontSize = 48.sp))
  LaunchedEffect(Unit) {
    delay(500)
    onConnectionSuccess()
  }
  /**
   * TODO: экран подключения к базе данных
   * Должен иметь поле и кнопку "Подключиться"
   * Когда идет подключение, нужно скрывать кнопкку и вместо нее писать текст загрузки
   * Если подключился успешно, то показывать модалку с текстом "Подключение успешно" и кнопкой ок
   */
}