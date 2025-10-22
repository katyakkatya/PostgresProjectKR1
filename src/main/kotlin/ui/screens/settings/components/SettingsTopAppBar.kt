import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus

@Composable
fun SettingsTopAppBar(
  onBack: () -> Unit
) {
  BoxWithConstraints(
    modifier = Modifier.fillMaxWidth()
  ) {
    val calculatedHeight = remember(maxHeight) {
      val tenPercent = maxHeight * 0.1f
      tenPercent.coerceIn(60.dp, 120.dp)
    }

    TopAppBar(
      modifier = Modifier.height(calculatedHeight),
      backgroundColor = MaterialTheme.colors.primary,
      title = {
        //todo: поменять отступы
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          "Настройки",
          fontSize = 32.sp,
          fontWeight = FontWeight.SemiBold,
          textAlign = TextAlign.Left,
          color = MaterialTheme.colors.onPrimary,
          style = MaterialTheme.typography.h4
        )
      },
      navigationIcon = {
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(onClick = onBack) {
          Icon(
            Icons.Default.ArrowBack,
            "Назад",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(48.dp)
          )
        }
      },
      contentColor = MaterialTheme.colors.onPrimary
    )
  }
}

