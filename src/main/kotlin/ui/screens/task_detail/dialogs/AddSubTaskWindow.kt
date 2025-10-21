import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.screens.task_detail.TaskDetailViewModel

@Composable
fun AddSubtaskWindow(
  viewModel: TaskDetailViewModel
) {
  var subtask by remember { mutableStateOf("") }
  Dialog(onDismissRequest = {}) {
    Surface(
      modifier = Modifier
        .wrapContentHeight(),
      shape = RoundedCornerShape(16.dp),
      elevation = 8.dp,
      color = MaterialTheme.colors.surface
    ) {
      Column(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Добавление подзадачи",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = MaterialTheme.typography.h4.fontFamily,
          color = MaterialTheme.colors.onSurface,
          modifier = Modifier.padding(vertical = 32.dp),
          style = MaterialTheme.typography.h4
        )

        TextField(
          value = subtask,
          onValueChange = {
            subtask = it
            viewModel.editSubtask(it)
          },
          placeholder = {
            Text(
              text = "Введите название подзадачи",
              textAlign = TextAlign.Start,
              color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
              fontFamily = MaterialTheme.typography.body1.fontFamily,
              fontSize = 24.sp,
              fontWeight = FontWeight.W400,
              style = MaterialTheme.typography.body1
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(90.dp)
            .wrapContentHeight(Alignment.CenterVertically),
          shape = RoundedCornerShape(16.dp),
          colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
            cursorColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.onSurface,
            backgroundColor = MaterialTheme.colors.surface
          ),
          textStyle = TextStyle(
            fontSize = 28.sp,
            fontFamily = MaterialTheme.typography.h6.fontFamily,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Start
          ),
          singleLine = true,
        )

        Row {
          Button(
            onClick = { viewModel.closeSubtaskWindow() },
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
            onClick = { viewModel.addSubtask(subtask) },
            modifier = Modifier
              .padding(24.dp).weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              backgroundColor = MaterialTheme.colors.primary,
              contentColor = MaterialTheme.colors.onPrimary
            )
          ) {
            Text(
              text = "Добавить",
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