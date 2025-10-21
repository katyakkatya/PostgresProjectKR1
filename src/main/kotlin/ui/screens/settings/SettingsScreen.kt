import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screens.settings.SettingsScreenViewModel

@Composable
fun SettingsScreen(
  viewModel: SettingsScreenViewModel,
  onBack: () -> Unit,
) {
  val minimumTaskTitleField by viewModel.minLengthInputFlow.collectAsState("")
  val maximumTaskTitleField by viewModel.maxLengthInputFlow.collectAsState("")
  val forceUniqueTitle by viewModel.forceUniqueTitle.collectAsState(false)
  val error by viewModel.errorFlow.collectAsState()
  val success by viewModel.successFlow.collectAsState()
  Scaffold(
    topBar = {
      SettingsTopAppBar(
        onBack = onBack
      )
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .padding(padding)
        .fillMaxSize()
    ) {
      Text(
        text = "Минимальная длина названия задачи",
        fontSize = 24.sp,
        fontFamily = FontFamily.SansSerif
      )
      TextField(
        value = minimumTaskTitleField,
        onValueChange = viewModel::setMinLengthInput,
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
        text = "Максимальная длина названия задачи",
        fontSize = 24.sp,
        fontFamily = FontFamily.SansSerif
      )
      TextField(
        value = maximumTaskTitleField,
        onValueChange = viewModel::setMaxLengthInput,
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
      Row {
        Text(
          text = "Уникальные названия задач",
          fontSize = 20.sp,
          color = MaterialTheme.colors.onSurface,
          style = MaterialTheme.typography.body1
        )

        Checkbox(
          checked = forceUniqueTitle,
          onCheckedChange = viewModel::setForceUniqueTitle,
          modifier = Modifier.size(36.dp),
          colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colors.secondary,
            uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            checkmarkColor = MaterialTheme.colors.onSecondary
          )
        )
      }
      Text(
        text = "Внимание: при применении нового значения удалятся все задачи, не соответсвующие новому значению",
        fontSize = 24.sp,
        fontFamily = FontFamily.SansSerif
      )
      Button(
        onClick = viewModel::applySettings,
        modifier = Modifier.padding(vertical = 32.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
          backgroundColor = Color.Gray,
          contentColor = Color.White
        )
      ) {
        Text(
          text = "Применить",
          fontFamily = FontFamily.SansSerif,
          fontSize = 18.sp,
          fontWeight = FontWeight.W400,
          modifier = Modifier.padding(vertical = 12.dp, horizontal = 48.dp)
        )
      }
      error?.let {
        Text(
          text = it,
          fontSize = 24.sp,
          fontFamily = FontFamily.SansSerif,
          color = Color.Red,
        )
      }
      if (success) {
        Text(
          text = "Настройки успешно применены",
          fontSize = 24.sp,
          fontFamily = FontFamily.SansSerif,
          color = Color.Green,
        )
      }
    }
  }
}