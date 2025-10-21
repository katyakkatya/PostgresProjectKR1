package ui.screens.task_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
              text = "Выбор задания",
              style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
            )
            if (state.tasks.isEmpty()) {
              Spacer(modifier = Modifier.height(16.dp))
              Text(
                text = "Нет заданий для добавления",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.body1
              )
            }
            LazyColumn(
              modifier = Modifier.weight(1f),
            ) {
              items(
                items = state.tasks
              ) { task ->
                TaskItemClickable(task, onTaskClicked = {
                  onTaskSelected(task)
                  onWindowClosed()
                })
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
    border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
    elevation = 4.dp,
    backgroundColor = MaterialTheme.colors.surface
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
        fontFamily = MaterialTheme.typography.h6.fontFamily,
        fontWeight = FontWeight.W400,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.h6
      )
      CircularProgressIndicator(
        progress = task.progress,
        modifier = Modifier.padding(end = 24.dp),
        color = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
        strokeWidth = 5.dp
      )
    }
  }
}