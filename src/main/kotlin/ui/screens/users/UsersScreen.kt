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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.UserWithTasksModel

@Composable
fun UsersScreen(
  viewModel: UsersScreenViewModel,
  onBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      UsersTopAppBar(
        onBack = onBack
      )
    }
  ) {
    UsersScreenContent(
      viewModel = viewModel,
    )
  }
}

@Composable
fun UsersScreenContent(
  viewModel: UsersScreenViewModel,
) {
  val usersList by viewModel.usersFlow.collectAsState(emptyList())
  val regexInput by viewModel.regexInputFlow.collectAsState("")
  val minTasksInput by viewModel.minTasksInputFlow.collectAsState("")
  Column {
    TextField(
      value = regexInput,
      onValueChange = viewModel::updateRegex,
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

    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Минимальное количество задач:",
        fontSize = 32.sp,
        fontFamily = FontFamily.SansSerif,
      )
      Spacer(modifier = Modifier.width(32.dp))
      TextField(
        value = minTasksInput,
        onValueChange = viewModel::updateMinTasks,
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
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f)
    ) {
      items(usersList) { user ->
        UserItem(user)
      }
    }
  }
}

@Composable
fun UserItem(user: UserWithTasksModel) {
  Text(
    text = "${user.name} (${user.taskCount})",
    fontSize = 24.sp,
    fontFamily = FontFamily.SansSerif
  )
}