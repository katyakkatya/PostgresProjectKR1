package ui.screens.task_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.UserModel
import ui.screens.common.components.UserItem

@Composable
fun AuthorFilterContent(
  author: UserModel?,
  onOpenAuthorFilterSelectDialog: () -> Unit,
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Text(
      text = "Автор задачи",
      fontSize = 32.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Left,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h5,
    )

    Divider(
      modifier = Modifier.padding(bottom = 8.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    Spacer(modifier = Modifier.height(8.dp))

    if (author == null) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
      ) {
        Text(
          text = "Автор не выбран",
          fontFamily = MaterialTheme.typography.h6.fontFamily,
          fontSize = 24.sp,
          color = MaterialTheme.colors.onSurface,
          style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
          onClick = onOpenAuthorFilterSelectDialog,
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
          )
        ) {
          Text(
            text = "Выбрать",
            fontSize = 24.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.button
          )
        }
      }
    } else {
      UserItem(
        user = author,
        onClick = onOpenAuthorFilterSelectDialog
      )
    }
  }
}