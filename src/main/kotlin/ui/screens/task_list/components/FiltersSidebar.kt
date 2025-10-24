import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import database.model.DbTaskStatus
import models.UserModel
import ui.screens.task_list.components.*

@Composable
fun FiltersSidebar(
  appliedFilters: Set<DbTaskStatus>,
  onFilterToggled: (DbTaskStatus) -> Unit,
  onFilterReset: () -> Unit,
  onClose: () -> Unit,
  modifier: Modifier = Modifier,
  isPermanent: Boolean = false,
  formattingOptionsPayload: FormattingOptionsPayload,
  author: UserModel? = null,
  onOpenAuthorFilterSelectDialog: () -> Unit,
  orderOptionsPayload: OrderOptionsPayload,
) {
  Surface(
    modifier = modifier
      .fillMaxHeight()
      .shadow(
        elevation = 16.dp,
        shape = RectangleShape,
        clip = false
      ),
    elevation = 4.dp,
    color = MaterialTheme.colors.surface
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
    ) {
      item {
        Spacer(modifier = Modifier.height(16.dp))
        if (!isPermanent) {
          IconButton(onClick = onClose) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Закрыть фильтры",
              tint = MaterialTheme.colors.onSurface
            )
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
        StatusesFilterContent(
          appliedFilters = appliedFilters,
          onFilterToggled = onFilterToggled,
          onFilterReset = onFilterReset,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AuthorFilterContent(
          author = author,
          onOpenAuthorFilterSelectDialog = onOpenAuthorFilterSelectDialog,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OrderOptionsContent(
          payload = orderOptionsPayload
        )
        Spacer(modifier = Modifier.height(16.dp))
        FormattionOptionsContent(
          payload = formattingOptionsPayload
        )
      }
    }
  }
}