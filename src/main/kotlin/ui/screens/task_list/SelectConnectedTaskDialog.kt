package ui.screens.task_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import models.TaskItemModel

@Composable
fun SelectConnectedTaskDialog(
  state: TaskSelectWindowState,
  onWindowClosed: () -> Unit,
  onTaskSelected: (TaskItemModel) -> Unit,
) {
  when (state) {
    TaskSelectWindowState.Closed -> Unit
    is TaskSelectWindowState.Opened -> {
      Dialog(onDismissRequest = {}) {
        Surface(
          modifier = Modifier
            .width(600.dp)
            .height(400.dp),
          shape = RoundedCornerShape(16.dp),
          elevation = 8.dp
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
              text = "Выбор задания",
              style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
              )
            )
            Column(
              modifier = Modifier.weight(1f),
            ) {
              state.tasks.forEach { task ->
                TaskItemClickable(task, onTaskClicked = {
                  onTaskSelected(task)
                  onWindowClosed()
                })
              }
            }
            // TODO: заменить кнопки
            Button(
              onClick = {
                onWindowClosed()
              }
            ) {
              Text("отмена")
            }
          }
        }
      }

    }
  }
}

@Composable
fun TaskItemClickable(
  task: TaskItemModel,
  onTaskClicked: () -> Unit,
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp)
      .clickable {
        onTaskClicked()
      },
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(1.dp, Color.LightGray),
    elevation = 4.dp
  ) {
    Row(
      modifier = Modifier.padding(24.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(20.dp)
          .clip(CircleShape)
          .background(color = task.color)
      )
      Text(
        text = task.title,
        modifier = Modifier
          .weight(1f)
          .padding(start = 16.dp),
        fontSize = 28.sp,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.W400,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      CircularProgressIndicator(
        progress = task.progress,
        modifier = Modifier.padding(end = 24.dp),
        color = Color.DarkGray,
        backgroundColor = Color.LightGray,
        strokeWidth = 5.dp
      )
    }
  }
}