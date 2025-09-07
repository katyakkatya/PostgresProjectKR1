package gui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gui.components.FieldEditorRow
import state.TableEditing
import java.awt.Cursor

@Composable
fun TableEditingDialog(
  tableEditing: TableEditing,
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
        Text("Редактировать таблицу", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(16.dp))

        LazyColumn(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          itemsIndexed(tableEditing.table.fields) { index, field ->
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
        Button(
          onClick = onAddField,
          modifier = Modifier.fillMaxWidth()
            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
        ) {
          Icon(Icons.Default.Add, null)
          Spacer(Modifier.width(8.dp))
          Text("Добавить поле")
        }

        Spacer(Modifier.height(16.dp))
        Button(
          onClick = onConfirmed,
          modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
        ) {
          Text("Сохранить")
        }
      }
    }
  }
}