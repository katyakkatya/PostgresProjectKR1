import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpandedTopAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  onClose: () -> Unit,
  focusRequester: FocusRequester
) {
  BoxWithConstraints(
    modifier = Modifier.fillMaxWidth()
  ) {
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
        IconButton(
          onClick = onClose,
          modifier = Modifier.size(48.dp)
        ) {
          Icon(
            Icons.Default.ArrowBack,
            "Закрыть поиск",
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(48.dp)
          )
        }
        Spacer(modifier = Modifier.width(16.dp))
        BasicTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChanged,
          modifier = Modifier
            .weight(1f)
            .focusRequester(focusRequester),
          textStyle = TextStyle(
            color = MaterialTheme.colors.onPrimary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal
          ),
          cursorBrush = SolidColor(MaterialTheme.colors.onPrimary),
          singleLine = true,
          decorationBox = { innerTextField ->
            Box(
              modifier = Modifier.fillMaxWidth(),
              contentAlignment = Alignment.CenterStart
            ) {
              if (searchQuery.isEmpty()) {
                Text(
                  "Поиск...",
                  style = TextStyle(
                    color = MaterialTheme.colors.onPrimary.copy(alpha = 0.7f),
                    fontSize = 32.sp
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
              imageVector = Icons.Filled.Close,
              contentDescription = "Очистить поиск",
              tint = MaterialTheme.colors.onPrimary
            )
          }
        } else {
          Spacer(modifier = Modifier.width(48.dp))
        }
      }
    }
  }
}