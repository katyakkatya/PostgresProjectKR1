package ui.screens.task_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import models.TaskItemModel
import ui.screens.common.components.UserItem

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
  onAuthorSelectWindowOpened: () -> Unit,
) {
  when (state) {
    NewTaskWindowState.Closed -> Unit
    is NewTaskWindowState.Opened -> {
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
              text = "Добавление задания",
              style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
            )
            TextField(
              value = state.taskName,
              onValueChange = onTaskNameChanged,
              placeholder = {
                Text(
                  "Например: Помыть посуду",
                  fontSize = 24.sp,
                  color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
              },
              textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp,
                color = MaterialTheme.colors.onSurface
              ),
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .height(60.dp)
                .padding(horizontal = 24.dp),
              colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.onSurface,
                focusedIndicatorColor = MaterialTheme.colors.primary,
                unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                cursorColor = MaterialTheme.colors.primary
              )
            )
            Text(
              text = "Автор",
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
            )
            state.author?.let { author ->
              Column(
                modifier = Modifier
                  .padding(horizontal = 16.dp)
              ) {
                UserItem(
                  user = author,
                  onClick = {}
                )
                Spacer(modifier = Modifier.height(16.dp))
              }
            }
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .clickable {
                  onAuthorSelectWindowOpened()
                },
              elevation = 8.dp,
              backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
              ) {
                Icon(
                  Icons.Default.Person,
                  contentDescription = "Выбрать автора",
                  modifier = Modifier.size(48.dp),
                  tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                  text = "Выбрать автора",
                  style = MaterialTheme.typography.body1.copy(
                    fontSize = 20.sp
                  ),
                  color = MaterialTheme.colors.primary
                )
              }
            }

            Text(
              text = "Подзадачи",
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
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
                  placeholder = {
                    Text(
                      "Например: Помыть посуду",
                      fontSize = 24.sp,
                      color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                  },
                  textStyle = LocalTextStyle.current.copy(
                    fontSize = 24.sp,
                    color = MaterialTheme.colors.onSurface
                  ),
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .height(60.dp)
                    .padding(horizontal = 24.dp),
                  colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    textColor = MaterialTheme.colors.onSurface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                    cursorColor = MaterialTheme.colors.primary
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
                    contentDescription = "Удалить подзадачу",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colors.error
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
              elevation = 8.dp,
              backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
              ) {
                Icon(
                  Icons.Default.Add,
                  contentDescription = "Добавить подзадачу",
                  modifier = Modifier.size(48.dp),
                  tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                  text = "Добавить подзадачу",
                  style = MaterialTheme.typography.body1.copy(
                    fontSize = 20.sp
                  ),
                  color = MaterialTheme.colors.primary
                )
              }
            }

            Text(
              text = "Связанные задачи",
              modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
              style = MaterialTheme.typography.h5.copy(
                fontWeight = FontWeight.Bold
              ),
              color = MaterialTheme.colors.onSurface
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
              elevation = 8.dp,
              backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f)
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
              ) {
                Icon(
                  Icons.Default.Add,
                  contentDescription = "Добавить связанную задачу",
                  modifier = Modifier.size(48.dp),
                  tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                  text = "Добавить связанную задачу",
                  style = MaterialTheme.typography.body1.copy(
                    fontSize = 20.sp
                  ),
                  color = MaterialTheme.colors.primary
                )
              }
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (state.error != null) {
              Text(
                text = state.error,
                style = MaterialTheme.typography.body2.copy(
                  fontSize = 18.sp
                ),
                color = MaterialTheme.colors.error,
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
                  backgroundColor = MaterialTheme.colors.secondary,
                  contentColor = MaterialTheme.colors.onSecondary
                )
              ) {
                Text(
                  text = "Отмена",
                  fontSize = 22.sp,
                  fontWeight = FontWeight.W400,
                  modifier = Modifier.padding(vertical = 12.dp),
                  style = MaterialTheme.typography.button
                )
              }
              Spacer(modifier = Modifier.width(16.dp))
              Button(
                onClick = onNewTaskSaved,
                modifier = Modifier
                  .padding(24.dp).weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                  backgroundColor = MaterialTheme.colors.primary,
                  contentColor = MaterialTheme.colors.onPrimary
                )
              ) {
                Text(
                  text = "Сохранить",
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
      IconButton(onClick = onTaskDeleted) {
        Icon(
          modifier = Modifier.size(36.dp),
          imageVector = Icons.Default.Delete,
          contentDescription = "Удалить задачу",
          tint = MaterialTheme.colors.error //
        )
      }
    }
  }
}