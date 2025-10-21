import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatabaseLogTopAppBar(onBack: () -> Unit){
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