package gui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import gui.components.CustomInput
import state.DatabaseEditing
import java.awt.Cursor

@Composable
fun DatabaseEditingDialog(
  databaseEditing: DatabaseEditing,
  onConfirmed: () -> Unit,
  onDismiss: () -> Unit,
  onNameChange: (String) -> Unit,
) {
  Dialog(onDismissRequest = {}) {
    Surface(shape = MaterialTheme.shapes.medium) {
      Column(Modifier.padding(16.dp).width(500.dp)) {
        Text(
          text = if (databaseEditing.initial) {
            "Создать базу данных"
          } else {
            "Редактировать базу данных"
          },
          style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.height(16.dp))

        CustomInput(
          value = databaseEditing.database.name,
          onValueChange = onNameChange,
          label = "Название",
        )

        Spacer(Modifier.height(16.dp))
        databaseEditing.error?.let { error ->
          Text(error)
          Spacer(Modifier.height(8.dp))
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Button(
            onClick = onDismiss,
            modifier = Modifier.pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
          ) {
            Text("Отмена")
          }
          Spacer(Modifier.width(8.dp))
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
}