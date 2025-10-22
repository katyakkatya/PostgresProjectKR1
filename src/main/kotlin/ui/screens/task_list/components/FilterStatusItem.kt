import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus

@Composable
fun FilterStatusItem(
  enabled: Boolean,
  status: DbTaskStatus,
  onClick: () -> Unit
) {
  val text = status.name.replace("_", " ").replaceFirstChar { it.uppercase() }
  FilterStatusItem(
    enabled = enabled,
    text = text,
    onClick = onClick
  )
}

@Composable
fun FilterStatusItem(
  enabled: Boolean,
  text: String,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = 12.dp, horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = text,
      fontSize = 20.sp,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.body1
    )

    Checkbox(
      checked = enabled,
      onCheckedChange = { onClick() },
      modifier = Modifier.size(36.dp),
      colors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colors.secondary,
        uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        checkmarkColor = MaterialTheme.colors.onSecondary
      )
    )
  }
}