package ui.screens.common.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddUserDialog(
  state: AddUserDialogState,
  onWindowClosed: () -> Unit,
  onNameChanged: (String) -> Unit,
  onUserTryAdd: () -> Unit,
) {
  when (state) {
    AddUserDialogState.Closed -> Unit
    is AddUserDialogState.Opened -> {
      Dialog(onDismissRequest = {}) {
        Surface(
          modifier = Modifier
            .width(600.dp)
            .height(400.dp),
          shape = RoundedCornerShape(16.dp),
          elevation = 8.dp,
          color = MaterialTheme.colors.surface
        ) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Text(
              modifier = Modifier
                .padding(24.dp),
              text = "Добалвение пользователя",
              style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
            )
            TextField(
              value = state.name,
              onValueChange = onNameChanged,
              textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp,
                color = MaterialTheme.colors.onSurface
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .height(60.dp)
                .padding(horizontal = 24.dp),
              colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.onSurface,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                cursorColor = MaterialTheme.colors.primary
              )
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
              Button(
                onClick = { onWindowClosed() },
                modifier = Modifier
                  .padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                  backgroundColor = MaterialTheme.colors.secondary,
                  contentColor = MaterialTheme.colors.onSecondary
                )
              ) {
                Text(
                  text = "Отмена",
                  fontSize = 22.sp,
                  fontWeight = FontWeight.W400,
                  modifier = Modifier.padding(vertical = 12.dp, horizontal = 48.dp),
                  style = MaterialTheme.typography.button
                )
              }
              Spacer(modifier = Modifier.width(16.dp))
              Button(
                onClick = { onUserTryAdd() },
                modifier = Modifier
                  .padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                  backgroundColor = MaterialTheme.colors.primary,
                  contentColor = MaterialTheme.colors.onPrimary
                )
              ) {
                Text(
                  text = "Сохранить",
                  fontSize = 22.sp,
                  fontWeight = FontWeight.W400,
                  modifier = Modifier.padding(vertical = 12.dp, horizontal = 48.dp),
                  style = MaterialTheme.typography.button
                )
              }
            }
            state.error?.let {
              Text(
                text = it,
                style = MaterialTheme.typography.body1.copy(
                  color = MaterialTheme.colors.error
                ),
                fontSize = 22.sp,
              )
            }
          }
        }
      }
    }
  }
}

sealed interface AddUserDialogState {
  data object Closed : AddUserDialogState
  data class Opened(
    val name: String,
    val error: String? = null,
  ) : AddUserDialogState
}