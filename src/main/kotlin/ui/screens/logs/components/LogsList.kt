import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.LogModel

@Composable
fun LogsList(
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
        LogsListItem(
          log = log,
          isLast = index == logs.size - 1
        )
      }
    }
  }
}