package ui.screens.task_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import models.TaskItemModel
import ui.screens.task_list.TaskItemClickable

@Composable
fun AddRelatedTaskWindow(
  state: NewRelatedTaskState.Opened,
  onWindowClosed: () -> Unit,
  onTaskSelected: (TaskItemModel) -> Unit,
) {
  Dialog(onDismissRequest = {}) {
    Surface(
      modifier = Modifier
        .width(600.dp)
        .height(800.dp),
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
        if (state.tasks.isEmpty()) {
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Нет заданий для добавления",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
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
            backgroundColor = Color.Gray,
            contentColor = Color.White
          )
        ) {
          Text(
            text = "Отмена",
            fontFamily = FontFamily.SansSerif,
            fontSize = 22.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 48.dp)
          )
        }
      }
    }
  }
}