package gui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import database.model.Table
import database.model.Table.Companion.SUPPORTED_COLUMN_TYPES

@Composable
fun FieldEditorRow(
  field: Table.Field,
  onNameChange: (String) -> Unit,
  onTypeChange: (String) -> Unit,
  onDeleted: () -> Unit,
) {
  Row(
    Modifier.fillMaxWidth().height(IntrinsicSize.Min),
    verticalAlignment = Alignment.CenterVertically
  ) {
    CustomInput(
      value = field.name,
      onValueChange = onNameChange,
      label = "Название",
      modifier = Modifier.weight(3f)
    )
    Spacer(Modifier.width(8.dp))
    CustomDropdown(
      label = "Тип",
      items = SUPPORTED_COLUMN_TYPES,
      selectedItem = field.type,
      onItemSelected = onTypeChange,
      modifier = Modifier.weight(2f)
    )
    Spacer(Modifier.width(8.dp))
    Column(
      modifier = Modifier
        .fillMaxHeight()
        .padding(top = 16.dp),
      verticalArrangement = Arrangement.Center,
    ) {
      Icon(
        Icons.Default.Remove, null,
        modifier = Modifier
          .clip(CircleShape)
          .clickable {
            onDeleted()
          }
      )
    }
  }
}