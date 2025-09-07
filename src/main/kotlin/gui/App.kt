package gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import gui.dialog.DatabaseEditingDialog
import gui.dialog.TableEditingDialog
import gui.viewmodel.DatabaseViewModel
import gui.viewmodel.TableViewModel
import java.awt.Cursor

@Composable
@Preview
fun App(
  tableViewModel: TableViewModel = Globals.tableViewModel,
  databaseViewModel: DatabaseViewModel = Globals.databaseViewModel
) {
  MaterialTheme {
    Row(Modifier.fillMaxSize()) {
      Column(modifier = Modifier.weight(0.3f).fillMaxHeight().border(width = 1.dp, color = Color.LightGray)) {
        Box(modifier = Modifier.weight(1f)) {
        }
        Divider()
        Button(
          onClick = { databaseViewModel.startDatabaseCreating() },
          modifier = Modifier.fillMaxWidth().padding(8.dp)
            .pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
        ) {
          Icon(Icons.Default.Add, null)
          Spacer(Modifier.width(8.dp))
          Text("Создать")
        }
      }

      Box(modifier = Modifier.weight(0.7f).fillMaxHeight()) {

      }
    }
  }

  val databaseEditing by databaseViewModel.databaseEditing.collectAsState(null)
  databaseEditing?.let { databaseEditing ->
    DatabaseEditingDialog(
      databaseEditing = databaseEditing,
      onConfirmed = { databaseViewModel.tryCreateDatabase() },
      onDismiss = { databaseViewModel.closeDatabaseEditing() },
      onNameChange = { databaseViewModel.updateName(it) }
    )
  }

  val tableEditing by tableViewModel.tableEditing.collectAsState(null)
  tableEditing?.let { tableEditing ->
    TableEditingDialog(
      tableEditing = tableEditing,
      onConfirmed = { },
      onDismiss = { tableViewModel.closeTableEditing() },
      onAddField = { tableViewModel.addField() },
      onEditFieldName = { index, name -> tableViewModel.updateFieldName(index, name) },
      onEditFieldType = { index, name -> tableViewModel.updateFieldType(index, name) },
      onDeletedRow = { index -> tableViewModel.removeField(index) }
    )
  }
}