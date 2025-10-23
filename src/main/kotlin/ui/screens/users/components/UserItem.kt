package ui.screens.users.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.UserWithTasksModel

@Composable
fun UserItem(user: UserWithTasksModel) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 0.dp),
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
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = user.name,
          style = MaterialTheme.typography.h6,
          color = MaterialTheme.colors.onSurface,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Задач: ${user.taskCount}",
          style = MaterialTheme.typography.body2,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )
      }

      // Бейдж с количеством задач
      Card(
        backgroundColor = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp
      ) {
        Text(
          text = user.taskCount.toString(),
          style = MaterialTheme.typography.body1,
          color = MaterialTheme.colors.onPrimary,
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
      }
    }
  }
}