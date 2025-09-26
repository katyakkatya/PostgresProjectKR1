package ui.screens.task_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
fun NewTaskDialog(
  state: NewTaskWindowState,
  onTaskNameChanged: (String) -> Unit,
  onSubtaskDeleted: (Int) -> Unit,
  onSubtaskChanged: (Int, String) -> Unit,
  onSubtaskAdded: () -> Unit,
  onTaskSelectWindowOpened: () -> Unit,
  onConnectedTaskDeleted: (Int) -> Unit,
  onNewTaskSaved: () -> Unit,
  onNewTaskClosed: () -> Unit,
) {
  when (state) {
    NewTaskWindowState.Closed -> Unit
    is NewTaskWindowState.Opened -> {
      Dialog(onDismissRequest = {}) {
        Surface(
          modifier = Modifier
            .width(600.dp)
            .height(600.dp),
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
              text = "Добавление задания",
              style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
              )
            )
            TextField(
              value = state.taskName,
              onValueChange = onTaskNameChanged,
              placeholder = { Text("Например: Помыть посуду", fontSize = 24.sp) },
              textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp,
                color = Color.Black
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .height(60.dp)
                .padding(horizontal = 24.dp),
              colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.Gray
              )
            )
            Text(
              text = "Подзадачи",
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
              )
            )
            state.subtasks.forEachIndexed { index, subtask ->
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
              ) {
                TextField(
                  value = subtask,
                  onValueChange = {
                    onSubtaskChanged(index, it)
                  },
                  placeholder = { Text("Например: Помыть посуду", fontSize = 24.sp) },
                  textStyle = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    color = Color.Black
                  ),
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .height(60.dp)
                    .padding(horizontal = 24.dp),
                  colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.DarkGray,
                    unfocusedIndicatorColor = Color.Gray
                  )
                )
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(
                  onClick = {
                    onSubtaskDeleted(index)
                  },
                  modifier = Modifier.size(64.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                  )
                }
              }
            }
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickable {
                  onSubtaskAdded()
                },
              elevation = 16.dp,
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Icon(
                  Icons.Default.Add,
                  contentDescription = null,
                  modifier = Modifier.size(64.dp),
                  tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                  text = "Добавить подзадачу",
                  style = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    color = Color.Black
                  )
                )
              }
            }
            Text(
              text = "Связанные задачи",
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
              )
            )
            state.connectedTasks.forEachIndexed { index, task ->
              TaskItem(task, onTaskDeleted = { onConnectedTaskDeleted(index) })
            }
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickable {
                  onTaskSelectWindowOpened()
                },
              elevation = 16.dp,
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Icon(
                  Icons.Default.Add,
                  contentDescription = null,
                  modifier = Modifier.size(64.dp),
                  tint = Color.Black
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                  text = "Добавить связаную задачу",
                  style = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    color = Color.Black
                  )
                )
              }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (state.error != null) {
              Text(
                text = state.error,
                style = LocalTextStyle.current.copy(
                  fontSize = 18.sp,
                  color = Color.Red
                ),
                modifier = Modifier.padding(24.dp)
              )
              Spacer(modifier = Modifier.height(16.dp))
            }
            Row {
              Button(
                onClick = onNewTaskClosed,
                modifier = Modifier
                  .padding(24.dp).weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                  backgroundColor = Color.Gray,
                  contentColor = Color.White
                )
              ) {
                Text(
                  text = "Отмена",
                  fontFamily = FontFamily.SansSerif,
                  fontSize = 22.sp,
                  fontWeight = FontWeight.W400,
                  modifier = Modifier.padding(vertical = 12.dp)
                )
              }
              Spacer(modifier = Modifier.width(16.dp))
              Button(
                onClick = onNewTaskSaved,
                modifier = Modifier
                  .padding(24.dp).weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                  backgroundColor = Color.Green,
                  contentColor = Color.White
                )
              ) {
                Text(
                  text = "Сохранить",
                  fontFamily = FontFamily.SansSerif,
                  fontSize = 22.sp,
                  fontWeight = FontWeight.W400,
                  modifier = Modifier.padding(vertical = 12.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun TaskItem(
  task: TaskItemModel,
  onTaskDeleted: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp)
      .padding(vertical = 16.dp),
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
      IconButton(onClick = onTaskDeleted) {
        Icon(
          modifier = Modifier.size(36.dp),
          imageVector = Icons.Default.Delete,
          contentDescription = ""
        )
      }
    }
  }
}