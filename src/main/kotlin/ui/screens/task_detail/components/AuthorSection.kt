package ui.screens.task_detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.UserModel
import ui.screens.common.components.UserItem

@Composable
fun AuthorSection(
  user: UserModel?,
) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
    verticalAlignment = Alignment.CenterVertically
  ){
    Text(
      text = "Автор",
      fontFamily = MaterialTheme.typography.h6.fontFamily,
      fontSize = 24.sp,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h6
    )
    Spacer(
      modifier = Modifier.width(8.dp)
    )
    if (user == null) {
      Text(
        text = "не выбран",
        fontFamily = MaterialTheme.typography.h6.fontFamily,
        fontSize = 24.sp,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.h6
      )
    } else {
      Spacer(modifier = Modifier.width(24.dp))
      Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary.copy(alpha = 0.2f)),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
      ){
        Text(
          text = user.name,
          fontSize = 24.sp,
          style = MaterialTheme.typography.h6,
          color = MaterialTheme.colors.onSurface,
          fontWeight = FontWeight.Medium,
          modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
        )
      }
    }
  }
}