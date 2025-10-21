import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddSubTaskFloatingActionButton(onClick: () -> Unit) {
  FloatingActionButton(
    onClick = onClick,
    modifier = Modifier
      .size(150.dp)
      .padding(24.dp),
    backgroundColor = Color.Gray,
    contentColor = Color.White
  ) {
    Icon(
      modifier = Modifier.size(50.dp),
      imageVector = Icons.Default.Add,
      contentDescription = "Добавить подзадачу"
    )
  }
}