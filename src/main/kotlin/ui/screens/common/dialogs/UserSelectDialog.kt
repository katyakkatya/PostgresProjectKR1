package ui.screens.common.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import models.UserModel
import ui.screens.common.components.UserItem

@Composable
fun UserSelectDialog(
  state: UserSelectDialogState,
  onWindowClosed: () -> Unit,
  onUserClicked: (UserModel) -> Unit,
) {
  when (state) {
    UserSelectDialogState.Closed -> Unit
    is UserSelectDialogState.Opened -> {
      Dialog(onDismissRequest = {}) {
        Surface(
          modifier = Modifier
            .width(600.dp)
            .height(800.dp),
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
              text = "Выберите пользователя",
              style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
            )
            if (state.users.isEmpty()) {
              Spacer(modifier = Modifier.height(16.dp))
              Text(
                text = "Нет пользователей",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.body1
              )
            }
            LazyColumn(
              modifier = Modifier.weight(1f)
                .padding(horizontal = 24.dp),
              verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
              items(
                items = state.users
              ) { user ->
                UserItem(
                  user = user,
                  onClick = {
                    onUserClicked(user)
                    onWindowClosed()
                  }
                )
              }
            }
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
          }
        }
      }
    }
  }
}

sealed interface UserSelectDialogState {
  data object Closed : UserSelectDialogState
  data class Opened(val users: List<UserModel>) : UserSelectDialogState
}