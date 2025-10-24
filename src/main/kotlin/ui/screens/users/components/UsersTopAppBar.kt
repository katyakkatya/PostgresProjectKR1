import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UsersTopAppBar(
  onBack: () -> Unit,
  onNewUserClicked: () -> Unit,
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
        Spacer(modifier = Modifier.width(16.dp))
        Text(
          "Пользователи",
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
      actions = {
        IconButton(onClick = onNewUserClicked) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colors.onPrimary
          )
        }
      },
      contentColor = MaterialTheme.colors.onPrimary
    )
  }
}

