package ui.screens.task_detail.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// TODO: проверить
// Поменял цвет заголовка, добавил отступы между статусами
// Изменил кнопки добавил отступы

@Composable
fun CustomStatusListWindow(
  statuses: List<String>,
  onCustomStatusSelected: (String) -> Unit,
  onClose: () -> Unit,
  onAddCustomStatus: () -> Unit
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
          color = MaterialTheme.colors.onSurface,
          modifier = Modifier.padding(vertical = 16.dp),
          style = MaterialTheme.typography.h4
        )

        LazyColumn {
          items(
            items = statuses
          ) {
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onCustomStatusSelected(it) },
              shape = RoundedCornerShape(8.dp),
              border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
              elevation = 8.dp,
              backgroundColor = MaterialTheme.colors.surface
            ) {
              Text(
                text = it,
                modifier = Modifier.padding(16.dp),
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

        Button(
          onClick = { onAddCustomStatus() },
          modifier = Modifier
            .padding(all = 24.dp),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
          )
        ) {
          Text(
            text = "Добавить статусь",
            fontSize = 22.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(vertical = 12.dp),
            style = MaterialTheme.typography.button
          )
        }

        Button(
          onClick = { onClose() },
          modifier = Modifier
            .padding(bottom = 12.dp),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.onSecondary
          )
        ) {
          Text(
            text = "Закрыть",
            fontSize = 22.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(vertical = 12.dp),
            style = MaterialTheme.typography.button
          )
        }

      }
    }
  }
}