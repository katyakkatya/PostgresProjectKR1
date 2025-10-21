import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogsHeader(
  checked: Boolean,
  onCheckedChange: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 32.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = "Показывать только ошибки: ",
      fontFamily = MaterialTheme.typography.h6.fontFamily,
      fontSize = 28.sp,
      fontWeight = FontWeight.W400,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h6
    )
    Checkbox(
      checked = checked,
      onCheckedChange = { onCheckedChange() },
      colors = androidx.compose.material.CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colors.primary,
        uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        checkmarkColor = MaterialTheme.colors.onPrimary
      )
    )
  }
}