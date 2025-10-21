import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatabaseLogTopAppBar(onBack: () -> Unit) {
  TopAppBar(
    modifier = Modifier.height(70.dp),
    backgroundColor = MaterialTheme.colors.primary,
    contentColor = MaterialTheme.colors.onPrimary
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      Text(
        text = "Логирование",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier.align(Alignment.Center),
        style = MaterialTheme.typography.h4
      )

      Box(
        modifier = Modifier.align(Alignment.CenterStart)
      ) {
        IconButton(
          onClick = onBack
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "Назад",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colors.onPrimary
          )
        }
      }
    }
  }
}