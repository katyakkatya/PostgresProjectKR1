import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ui.screens.task_detail.TaskDetailViewModel

@Composable
fun DeletionWindow(
  viewModel: TaskDetailViewModel
) {
  Dialog(onDismissRequest = {}) {
    Surface(
      modifier = Modifier
        .wrapContentHeight(),
      shape = RoundedCornerShape(16.dp),
      elevation = 8.dp
    ) {
      Column(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = "Удаление",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = Color.DarkGray,
          modifier = Modifier.padding(vertical = 32.dp)
        )

        Text(
          text = "Задача будет удалена без возможности восстановления.\n\nВы уверены, что хотите это сделать?",
          fontSize = 28.sp,
          fontFamily = FontFamily.SansSerif,
          modifier = Modifier.padding(vertical = 32.dp),
          textAlign = TextAlign.Center
        )

        Row {
          Button(
            onClick = { viewModel.closeDeletionWindow() },
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
            onClick = { viewModel.deleteTask() },
            modifier = Modifier
              .padding(24.dp).weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              backgroundColor = Color.Red,
              contentColor = Color.White
            )
          ) {
            Text(
              text = "Удалить",
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