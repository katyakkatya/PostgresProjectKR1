package ui.screens.task_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.UserModel
import ui.screens.common.components.UserItem

@Composable
fun AuthorSection(
  user: UserModel?,
) {
  Column {
    Spacer(modifier = Modifier.height(32.dp))
    Text(
      text = "Автор",
      fontFamily = MaterialTheme.typography.h6.fontFamily,
      fontSize = 24.sp,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h6
    )
    Spacer(
      modifier = Modifier.height(16.dp)
    )
    if (user == null) {
      Text(
        text = "Автор не выбран",
        fontFamily = MaterialTheme.typography.h6.fontFamily,
        fontSize = 24.sp,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.h6
      )
    } else {
      UserItem(
        user = user
      )
    }
  }
}