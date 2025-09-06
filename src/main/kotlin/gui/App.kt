package gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gui.dialog.DatabaseEditingDialog

@Composable
@Preview
fun App(
  databaseViewModel: DatabaseViewModel
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
      onConfirmed = { },
      onDismiss = { databaseViewModel.closeDatabaseEditing() },
      onAddField = { databaseViewModel.addField() },
      onEditFieldName = { index, name -> databaseViewModel.updateFieldName(index, name) },
      onEditFieldType = { index, name -> databaseViewModel.updateFieldType(index, name) },
      onDeletedRow = { index -> databaseViewModel.removeField(index) }
    )
  }
}