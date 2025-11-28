package ui.screens.task_detail.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.screens.task_detail.CustomStatusCreationDialogState

@Composable
fun CustomStatusCreationWindow(
  state: CustomStatusCreationDialogState.Opened,
  onNameChanged: (String) -> Unit,
  onClose: () -> Unit,
  onTrySave: () -> Unit
) {
  Dialog(onDismissRequest = {}) {
    Surface(
      modifier = Modifier
        .width(600.dp)
        .wrapContentHeight(),
      shape = RoundedCornerShape(16.dp),
      elevation = 8.dp,
      color = MaterialTheme.colors.surface
    ) {
      Column(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Выбрать статус",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = MaterialTheme.typography.h4.fontFamily,
          color = MaterialTheme.colors.error,
          modifier = Modifier.padding(vertical = 16.dp),
          style = MaterialTheme.typography.h4
        )

        TextField(
          value = state.name,
          onValueChange = onNameChanged,
        )

        state.error?.let {
          Text(
            text = it,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = MaterialTheme.typography.body1.fontFamily,
            color = MaterialTheme.colors.onSurface,
            textDecoration = TextDecoration.None,
            style = MaterialTheme.typography.body1
          )
        }

        Button(
          onClick = { onClose() }
        ) {
          Text(
            text = "Закрыть",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = MaterialTheme.typography.body1.fontFamily,
            color = MaterialTheme.colors.onSurface,
            textDecoration = TextDecoration.None,
            style = MaterialTheme.typography.body1
          )
        }

        Button(
          onClick = { onTrySave() }
        ) {
          Text(
            text = "Сохранить",
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = MaterialTheme.typography.body1.fontFamily,
            color = MaterialTheme.colors.onSurface,
            textDecoration = TextDecoration.None,
            style = MaterialTheme.typography.body1
          )
        }

      }
    }
  }
}