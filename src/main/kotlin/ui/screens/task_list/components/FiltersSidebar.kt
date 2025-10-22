import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FiltersSidebar(
  onClose: () -> Unit,
  modifier: Modifier = Modifier,
  isPermanent: Boolean = false
) {
  Surface(
    modifier = modifier
      .fillMaxHeight(),
    elevation = 4.dp,
    color = MaterialTheme.colors.surface
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Фильтры",
          style = MaterialTheme.typography.h6,
          color = MaterialTheme.colors.onSurface
        )

        if (!isPermanent) {
          IconButton(onClick = onClose) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Закрыть фильтры",
              tint = MaterialTheme.colors.onSurface
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      if (isPermanent) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
          text = "Фильтры всегда видны на больших экранах",
          style = MaterialTheme.typography.caption,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
          modifier = Modifier.padding(top = 16.dp)
        )
      }
    }
  }
}