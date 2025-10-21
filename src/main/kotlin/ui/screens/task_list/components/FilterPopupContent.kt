import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus
import ui.screens.task_list.FilterStatusItem

@Composable
fun FiltersPopupContent(
  appliedFilters: Set<DbTaskStatus>,
  onFilterToggled: (DbTaskStatus) -> Unit,
  onFilterReset: () -> Unit
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Text(
      text = "Фильтры по статусу",
      fontSize = 28.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Center
    )

    Divider(modifier = Modifier.padding(bottom = 8.dp))

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

    Divider(modifier = Modifier.padding(top = 8.dp))
    Button(
      onClick = {onFilterReset()},
      modifier = Modifier
        .fillMaxWidth().padding(12.dp),
      shape = RoundedCornerShape(16.dp),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Gray,
        contentColor = Color.White
      )
    ) {
      Text(
        text = "Сбросить",
        fontFamily = FontFamily.SansSerif,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(vertical = 8.dp)
      )
    }
  }
}