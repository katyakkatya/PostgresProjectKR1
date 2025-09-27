package ui.screens.database_creation

import database.DatabaseInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository

class DatabaseCreationViewModel(
  private val databaseInteractor: DatabaseInteractor,
  private val todoRepository: TodoRepository,
) {
  private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State.Loading)
  val stateFlow = _stateFlow

  fun onInit() {
    checkIfDatabaseExists()
  }

  private fun checkIfDatabaseExists() {
    if (databaseInteractor.databaseExists()) {
      _stateFlow.value = State.Loaded(true)
    } else {
      _stateFlow.value = State.Loaded(false)
    }
  }

  fun createDatabase() {
    _stateFlow.value = (_stateFlow.value as State.Loaded).copy(isCreating = true)
    val result = todoRepository.tryCreateDatabase()
    if (result) {
      _stateFlow.value = State.Created
    } else {
      _stateFlow.value = (_stateFlow.value as State.Loaded).copy(isCreating = false)
    }
  }

  sealed interface State {
    object Loading : State
    data class Loaded(
      val isDatabaseCreated: Boolean,
      val isCreating: Boolean = false
    ) : State

    object Created : State
  }
}