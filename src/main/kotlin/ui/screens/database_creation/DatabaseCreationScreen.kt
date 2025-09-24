package ui.screens.database_creation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DatabaseCreationScreen(
  viewModel: DatabaseCreationViewModel,
  onDatabaseCreated: () -> Unit,
) {
  LaunchedEffect(Unit) {
    // Этот метод вызвать когда нужно вернуться
    onDatabaseCreated()
  }

  val state by viewModel.stateFlow.collectAsState()
  when (state) {
    DatabaseCreationViewModel.State.Loading -> {
      // TODO: текст проверка наличия базы данных
    }

    is DatabaseCreationViewModel.State.Loaded -> {
      val loadedState = state as DatabaseCreationViewModel.State.Loaded
      /**
       * TODO: Экран создания базы данных
       * есть ли бд: loadedState.isDatabaseCreated
       * Если нет бд, то показывать это текстом и кнопкой "Создать базу данных"
       */
    }

    DatabaseCreationViewModel.State.Created -> {
      // TODO: база данных успеешно создана
    }
  }
}