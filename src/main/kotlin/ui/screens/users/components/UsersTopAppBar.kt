import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UsersTopAppBar(
  onBack: () -> Unit
) {
  TopAppBar(
    modifier = Modifier.height(70.dp),
    backgroundColor = Color.Gray,
    title = { Text("Пользователи") },
    navigationIcon = {
      IconButton(onClick = onBack) {
        Icon(Icons.Default.ArrowBack, "Назад")
      }
    }
  )
}