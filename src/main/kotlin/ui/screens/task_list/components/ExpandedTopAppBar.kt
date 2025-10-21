import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ExpandedTopAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  onClose: () -> Unit,
  focusRequester: FocusRequester
) {
  TopAppBar(
    backgroundColor = MaterialTheme.colors.onBackground,
    elevation = 4.dp,
    contentColor = MaterialTheme.colors.primary // Устанавливаем цвет контента
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = onClose,
        modifier = Modifier.size(48.dp)
      ) {
        Icon(
          Icons.Filled.ArrowBack,
          contentDescription = "Close search",
          tint = MaterialTheme.colors.onPrimary // Используем цвет из темы
        )
      }

      BasicTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        modifier = Modifier
          .weight(1f)
          .focusRequester(focusRequester),
        textStyle = TextStyle(
          color = MaterialTheme.colors.onPrimary, // Цвет текста из темы
          fontSize = MaterialTheme.typography.h6.fontSize
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.onPrimary), // Цвет курсора
        singleLine = true,
        decorationBox = { innerTextField ->
          Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
          ) {
            if (searchQuery.isEmpty()) {
              Text(
                "Поиск...",
                style = MaterialTheme.typography.h6.copy(
                  color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f) // Полупрозрачный цвет
                )
              )
            }
            innerTextField()
          }
        }
      )

      if (searchQuery.isNotEmpty()) {
        IconButton(
          onClick = { onSearchQueryChanged("") },
          modifier = Modifier.size(48.dp)
        ) {
          Icon(
            Icons.Filled.Close,
            contentDescription = "Clear search",
            tint = MaterialTheme.colors.onPrimary // Цвет из темы
          )
        }
      }

      Spacer(modifier = Modifier.width(8.dp))
    }
  }
}