package gui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.Cursor

@Composable
fun CustomDropdown(
  items: List<String>,
  selectedItem: String,
  onItemSelected: (String) -> Unit,
  modifier: Modifier = Modifier,
  label: String
) {
  var expanded by remember { mutableStateOf(false) }
  var componentWidth by remember { mutableStateOf(0.dp) }
  val density = LocalDensity.current

  Column(modifier) {
    Text(
      text = label,
      fontSize = 12.sp,
      color = MaterialTheme.colors.primary,
      modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )

    Box {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
          .onSizeChanged {
            componentWidth = with(density) { it.width.toDp() }
          }
          .clip(MaterialTheme.shapes.small)
          .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
          .clickable { expanded = true }
          .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(text = selectedItem, modifier = Modifier.weight(1f))
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Open dropdown"
        )
      }

      DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.width(componentWidth),
        offset = DpOffset(x = 0.dp, y = 4.dp)
      ) {
        items.forEach { item ->
          DropdownMenuItem(onClick = {
            onItemSelected(item)
            expanded = false
          }) {
            Text(text = item)
          }
        }
      }
    }
  }
}