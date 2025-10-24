package ui.screens.users

import UsersTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.screens.common.dialogs.AddUserDialog
import ui.screens.common.dialogs.AddUserDialogState
import ui.screens.users.components.UserItem

@Composable
fun UsersScreen(
  viewModel: UsersScreenViewModel,
  onBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      UsersTopAppBar(
        onBack = onBack,
        onNewUserClicked = viewModel::openAddUserDialog
      )
    }
  ) { paddingValues ->
    UsersScreenContent(
      viewModel = viewModel,
      modifier = Modifier.padding(paddingValues)
    )
  }
}

@Composable
fun UsersScreenContent(
  viewModel: UsersScreenViewModel,
  modifier: Modifier = Modifier
) {
  val usersList by viewModel.usersFlow.collectAsState(emptyList())
  val regexInput by viewModel.regexInputFlow.collectAsState("")
  val minTasksInput by viewModel.minTasksInputFlow.collectAsState("")
  val error by viewModel.errorFlow.collectAsState("")

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(24.dp)
  ) {
    // Заголовок экрана
    Text(
      text = "Пользователи и задачи",
      style = MaterialTheme.typography.h4,
      color = MaterialTheme.colors.onSurface,
      modifier = Modifier.fillMaxWidth(),
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(24.dp))

    // Поле для regex фильтра
    Column(
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "Фильтр по имени (regex):",
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      TextField(
        value = regexInput,
        onValueChange = viewModel::updateRegex,
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
        singleLine = true,
        placeholder = {
          Text(
            text = "Введите регулярное выражение",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
          )
        }
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Поле для минимального количества задач
    Column(
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = "Минимальное количество задач:",
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onSurface
      )

      Spacer(modifier = Modifier.height(8.dp))

      TextField(
        value = minTasksInput,
        onValueChange = viewModel::updateMinTasks,
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
        singleLine = true,
        placeholder = {
          Text(
            text = "Введите число",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
          )
        }
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    if (error.isNullOrEmpty().not()) {
      Text(
        text = error!!,
        style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error),
        modifier = Modifier.fillMaxWidth()
      )
      Spacer(modifier = Modifier.height(16.dp))
    }

    Text(
      text = "Найдено пользователей: ${usersList.size}",
      style = MaterialTheme.typography.h6,
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
      modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Список пользователей
    if (usersList.isEmpty()) {
      // Сообщение при пустом списке
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = if (regexInput.isNotEmpty() || minTasksInput.isNotEmpty()) {
            "Пользователи не найдены\nПопробуйте изменить параметры фильтрации"
          } else {
            "Нет пользователей с задачами"
          },
          fontSize = 32.sp,
          fontFamily = MaterialTheme.typography.body1.fontFamily,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
          textAlign = TextAlign.Center
        )
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        items(usersList) { user ->
          UserItem(user)
        }
      }
    }
  }

  val addUserDialogState by viewModel.addUserDialogStateFlow.collectAsState(AddUserDialogState.Closed)
  AddUserDialog(
    state = addUserDialogState,
    onWindowClosed = viewModel::closeAddUserDialog,
    onNameChanged = viewModel::onNewUserNameChanged,
    onUserTryAdd = viewModel::tryAddNewUser
  )

}