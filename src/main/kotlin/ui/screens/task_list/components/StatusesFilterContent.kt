import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus
import models.TaskStatus

@Composable
fun StatusesFilterContent(
  appliedFilters: Set<DbTaskStatus>,
  onFilterToggled: (DbTaskStatus) -> Unit,
  onFilterReset: () -> Unit
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Text(
      text = "Фильтры по статусу",
      fontSize = 32.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Left,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h5,
    )

    Divider(
      modifier = Modifier.padding(bottom = 8.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    val statuses = listOf(
      DbTaskStatus.BACKLOG, DbTaskStatus.IN_PROGRESS, DbTaskStatus.IN_REVIEW,
      DbTaskStatus.DONE, DbTaskStatus.DROPPED
    )

    statuses.forEach { status ->
      FilterStatusItem(
        enabled = (status in appliedFilters),
        status = status,
        onClick = { onFilterToggled(status) }
      )
    }

    Divider(
      modifier = Modifier.padding(top = 8.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    Button(
      onClick = { onFilterReset() },
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      shape = RoundedCornerShape(16.dp),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = MaterialTheme.colors.onSecondary
      )
    ) {
      Text(
        text = "Сбросить",
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(vertical = 8.dp),
        style = MaterialTheme.typography.button
      )
    }
  }
}