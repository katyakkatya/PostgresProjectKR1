import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.DbTaskStatus

@Composable
fun DefaultTopAppBar(
  appliedFilters: Set<DbTaskStatus>,
  onFilterToggled: (DbTaskStatus) -> Unit,
  onFilterReset: () -> Unit,
  onLogsClicked: () -> Unit,
  onSearchClicked: () -> Unit,
  onSettingsClicked: () -> Unit,
) {
  var filterPopupOpened by remember { mutableStateOf(false) }

  TopAppBar(
    modifier = Modifier.height(70.dp),
    backgroundColor = Color.Gray,
    title = {
      Text(
        text = "TODO",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
      )
    },
    navigationIcon = {
      IconButton(onClick = onSettingsClicked) {
        Icon(
          imageVector = Icons.Filled.Settings,
          contentDescription = "Настройки",
          modifier = Modifier.size(48.dp),
          tint = Color.White
        )
      }
    },
    actions = {
      IconButton(onClick = onSearchClicked) {
        Icon(
          Icons.Filled.Search,
          contentDescription = "Поиск",
          tint = Color.White
        )
      }

      IconButton(onClick = onLogsClicked) {
        Icon(
          imageVector = Icons.Outlined.List,
          contentDescription = "Логи",
          modifier = Modifier.size(48.dp),
          tint = Color.White
        )
      }

      Box {
        IconButton(
          onClick = { filterPopupOpened = true }
        ) {
          Icon(
            imageVector = Icons.Outlined.FilterList,
            contentDescription = "Фильтр",
            modifier = Modifier.size(48.dp),
            tint = Color.White
          )
        }

        DropdownMenu(
          expanded = filterPopupOpened,
          onDismissRequest = { filterPopupOpened = false },
          modifier = Modifier
            .width(380.dp)
            .clip(RoundedCornerShape(8.dp))
        ) {
          FiltersPopupContent(
            appliedFilters = appliedFilters,
            onFilterToggled = onFilterToggled,
            onFilterReset = onFilterReset
          )
        }
      }
    }
  )
}