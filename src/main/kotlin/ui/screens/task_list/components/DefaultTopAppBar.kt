import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
  onUsersClicked: () -> Unit,
  onFiltersSidebarToggle: () -> Unit = {}
) {
  var filterPopupOpened by remember { mutableStateOf(false) }

  BoxWithConstraints(
    modifier = Modifier.fillMaxWidth()
  ) {
    val isFullScreen = maxWidth >= 1200.dp

    val calculatedHeight = remember(maxHeight) {
      val tenPercent = maxHeight * 0.1f
      tenPercent.coerceIn(60.dp, 120.dp)
    }

    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .height(calculatedHeight),
      color = MaterialTheme.colors.primary,
      elevation = 4.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = onSettingsClicked) {
          Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "Настройки",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colors.onPrimary
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
          text = "Трекер задач",
          fontSize = 32.sp,
          fontWeight = FontWeight.SemiBold,
          modifier = Modifier.weight(1f),
          textAlign = TextAlign.Left,
          color = MaterialTheme.colors.onPrimary,
          style = MaterialTheme.typography.h4
        )

        if (!isFullScreen) {
          IconButton(onClick = onFiltersSidebarToggle) {
            Icon(
              Icons.Outlined.FilterList,
              contentDescription = "Фильтры",
              tint = MaterialTheme.colors.onPrimary,
              modifier = Modifier.size(48.dp),
              )
          }
        }
        Spacer(modifier = Modifier.width(16.dp))

          IconButton(onClick = onUsersClicked) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = "Пользователи",
              modifier = Modifier.size(36.dp),
              tint = MaterialTheme.colors.onPrimary
            )
          }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = onSearchClicked) {
          Icon(
            Icons.Filled.Search,
            contentDescription = "Поиск",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colors.onPrimary
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(onClick = onLogsClicked) {
          Icon(
            imageVector = Icons.Outlined.List,
            contentDescription = "Логи",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colors.onPrimary
          )
        }

        Spacer(modifier = Modifier.width(16.dp))


        if(!isFullScreen){
          Box {
            IconButton(
              onClick = { filterPopupOpened = !filterPopupOpened }
            ) {
              Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = "Быстрые фильтры",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colors.onPrimary
              )
            }

            DropdownMenu(
              expanded = filterPopupOpened,
              onDismissRequest = { filterPopupOpened = false },
              modifier = Modifier
                .width(380.dp)
                .clip(RoundedCornerShape(8.dp))
            ) {
              StatusesFilterContent(
                appliedFilters = appliedFilters,
                onFilterToggled = onFilterToggled,
                onFilterReset = onFilterReset
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(16.dp))
      }
    }
  }
}