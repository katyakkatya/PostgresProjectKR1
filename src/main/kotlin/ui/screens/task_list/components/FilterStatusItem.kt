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
  FilterStatusItem(
    enabled = enabled,
    text = getStatusName(status),
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
      .padding(vertical = 16.dp, horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = text,
      fontSize = 24.sp,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.body1,
      modifier = Modifier.weight(3f)
    )

    Checkbox(
      checked = enabled,
      onCheckedChange = { onClick() },
      modifier = Modifier.size(48.dp).weight(1f),
      colors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colors.secondary,
        uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        checkmarkColor = MaterialTheme.colors.onSecondary
      )
    )
  }
}

private fun getStatusName(status: DbTaskStatus): String = when (status) {
  DbTaskStatus.BACKLOG -> "В бэклоге"
  DbTaskStatus.IN_PROGRESS -> "В процессе"
  DbTaskStatus.IN_REVIEW -> "На проверке"
  DbTaskStatus.DONE -> "Выполнено"
  DbTaskStatus.DROPPED -> "Не будет выполнено"
}