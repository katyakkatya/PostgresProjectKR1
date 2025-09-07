package gui.viewmodel

import database.DatabaseInteractor
import database.model.Database
import database.request.CreateDatabaseRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import state.DatabaseEditing

class DatabaseViewModel(
  private val databaseInteractor: DatabaseInteractor,
) {

  private val _databaseEditing: MutableStateFlow<DatabaseEditing?> = MutableStateFlow(null)
  val databaseEditing: Flow<DatabaseEditing?> = _databaseEditing

  fun closeDatabaseEditing() {
    _databaseEditing.value = null
  }

  fun updateName(name: String) {
    _databaseEditing.value = _databaseEditing.value?.updateTableName(name)
  }

  fun startDatabaseCreating() {
    _databaseEditing.value = DatabaseEditing(Database(""))
  }

  fun tryCreateDatabase() {
    _databaseEditing.value?.let { databaseEditing ->
      if (validateFields(databaseEditing)) {
        databaseInteractor.createDatabase(createRequest(databaseEditing))
      }
    }
  }

  private fun createRequest(databaseEditing: DatabaseEditing): CreateDatabaseRequest {
    return CreateDatabaseRequest(databaseEditing.database.name)
  }

  private fun validateFields(databaseEditing: DatabaseEditing): Boolean {
    _databaseEditing.value?.updateError(null)
    if (databaseEditing.database.name.isBlank()) {
      _databaseEditing.value = _databaseEditing.value?.updateError("Впишите название базы данных!")
      return false
    }
    return true
  }
}