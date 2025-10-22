import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        .padding(24.dp)
    ) {
      Text(
        text = "Минимальная длина названия задачи",
        modifier = Modifier
          .padding(start = 16.dp),
        fontSize = 28.sp,
        fontFamily = MaterialTheme.typography.h6.fontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.h6
      )

      Spacer(modifier = Modifier.height(12.dp))

      TextField(
        value = minimumTaskTitleField,
        onValueChange = viewModel::setMinLengthInput,
        textStyle = MaterialTheme.typography.body1,
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        colors = TextFieldDefaults.textFieldColors(
          backgroundColor = MaterialTheme.colors.surface,
          textColor = MaterialTheme.colors.onSurface,
          focusedIndicatorColor = MaterialTheme.colors.primary,
          unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
          cursorColor = MaterialTheme.colors.primary
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Максимальная длина названия задачи",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onSurface
      )

      Spacer(modifier = Modifier.height(12.dp))

      TextField(
        value = maximumTaskTitleField,
        onValueChange = viewModel::setMaxLengthInput,
        textStyle = MaterialTheme.typography.body1,
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        colors = TextFieldDefaults.textFieldColors(
          backgroundColor = MaterialTheme.colors.surface,
          textColor = MaterialTheme.colors.onSurface,
          focusedIndicatorColor = MaterialTheme.colors.primary,
          unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
          cursorColor = MaterialTheme.colors.primary
        ),
        singleLine = true
      )

      Spacer(modifier = Modifier.height(24.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Уникальные названия задач",
          style = MaterialTheme.typography.h5,
          color = MaterialTheme.colors.onSurface
        )

        Checkbox(
          checked = forceUniqueTitle,
          onCheckedChange = viewModel::setForceUniqueTitle,
          modifier = Modifier.size(40.dp),
          colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colors.primary,
            uncheckedColor = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
            checkmarkColor = MaterialTheme.colors.onPrimary
          )
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Внимание: при применении нового значения удалятся все задачи, не соответствующие новому значению",
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
      )

      Spacer(modifier = Modifier.height(32.dp))

      Button(
        onClick = viewModel::applySettings,
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          backgroundColor = MaterialTheme.colors.primary,
          contentColor = MaterialTheme.colors.onPrimary
        )
      ) {
        Text(
          text = "Применить",
          style = MaterialTheme.typography.button
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      error?.let {
        Text(
          text = it,
          style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error)
        )
      }

      if (success) {
        Text(
          text = "Настройки успешно применены",
          style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary)
        )
      }
    }
  }
}