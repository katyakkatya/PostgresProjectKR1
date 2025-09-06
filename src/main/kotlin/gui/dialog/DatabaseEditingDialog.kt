package gui.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gui.components.FieldEditorRow
import state.DatabaseEditing

@Composable
fun DatabaseEditingDialog(
  databaseEditing: DatabaseEditing,
  onConfirmed: () -> Unit,
  onDismiss: () -> Unit,
  onAddField: () -> Unit,
  onEditFieldName: (Int, String) -> Unit,
  onEditFieldType: (Int, String) -> Unit,
  onDeletedRow: (Int) -> Unit,
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(shape = MaterialTheme.shapes.medium) {
      Column(Modifier.padding(16.dp).width(500.dp)) {
        Text("Edit Table Schema", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
          itemsIndexed(databaseEditing.database.fields) { index, field ->
            FieldEditorRow(
              field = field,
              onNameChange = { onEditFieldName(index, it) },
              onTypeChange = { onEditFieldType(index, it) },
              onDeleted = { onDeletedRow(index) }
            )
            Spacer(Modifier.height(8.dp))
          }
        }

        Spacer(Modifier.height(8.dp))
        Button(onClick = onAddField, modifier = Modifier.fillMaxWidth()) {
          Icon(Icons.Default.Add, null)
          Spacer(Modifier.width(8.dp))
          Text("Добавить поле")
        }

        Spacer(Modifier.height(16.dp))
        Button(onClick = onConfirmed) {
          Text("Сохранить")
        }
      }
    }
  }
}