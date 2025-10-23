import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.LogModel

@Composable
fun LogsList(
  logs: List<LogModel>
) {
  if (logs.isEmpty()) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp, vertical = 32.dp)
        .padding(bottom = 24.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(color = MaterialTheme.colors.surface)
        .border(2.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f),RoundedCornerShape(24.dp)),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ){
      Text(
        text = "Нет логов для отображения",
        fontSize = 32.sp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.body2
      )
    }
  }else{
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 32.dp, vertical = 48.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(color = MaterialTheme.colors.surface)
        .border(2.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f),RoundedCornerShape(24.dp))
        .verticalScroll(rememberScrollState())
    ){
      MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
      logs.forEachIndexed { index, log ->
        LogsListItem(
          log = log,
          isLast = index == logs.size - 1
        )
      }
    }
  }
}