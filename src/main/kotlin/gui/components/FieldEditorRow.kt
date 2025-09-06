package gui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.*
import database.model.Database

@Composable
fun FieldEditorRow(
  field: Database.Field,
  onNameChange: (String) -> Unit,
  onTypeChange: (String) -> Unit,
  onDeleted: () -> Unit,
) {
  var typeDropdownExpanded by remember { mutableStateOf(false) }

  Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
    OutlinedTextField(
      value = field.name,
      onValueChange = onNameChange,
      label = { Text("Column Name") },
      modifier = Modifier.weight(1f)
    )
    Spacer(Modifier.width(8.dp))
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = field.type,
        onValueChange = {},
        readOnly = true,
        label = { Text("Type") },
        trailingIcon = {
          Icon(
            Icons.Default.ArrowDropDown,
            "Select Type",
            Modifier.clickable { typeDropdownExpanded = true }
          )
        },
        modifier = Modifier.width(150.dp)
      )
      DropdownMenu(
        expanded = typeDropdownExpanded,
        onDismissRequest = { typeDropdownExpanded = false }
      ) {
        Database.SUPPORTED_COLUMN_TYPES.forEach { type ->
          DropdownMenuItem(onClick = {
            onTypeChange(type)
            typeDropdownExpanded = false
          }) {
            Text(type)
          }
        }
      }
      Icon(Icons.Default.Remove, null,
        modifier = Modifier.clickable {
          onDeleted()
        }
      )
    }
  }
}