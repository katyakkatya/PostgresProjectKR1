package ui.screens.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.UserModel

@Composable
fun UserItem(
  user: UserModel,
  onClick: (() -> Unit)? = null
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 0.dp)
      .clickable(
        enabled = onClick != null
      ) {
        onClick?.invoke()
      },
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, MaterialTheme.colors.primary.copy(alpha = 0.2f)),
    elevation = 2.dp,
    backgroundColor = MaterialTheme.colors.surface
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = user.name,
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onSurface,
        fontWeight = FontWeight.Medium
      )
    }
  }
}