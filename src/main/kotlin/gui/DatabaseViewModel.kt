package gui

import database.DatabaseInteractor
import database.model.Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import state.DatabaseEditing
import kotlin.collections.map

class DatabaseViewModel(
  private val databaseInteractor: DatabaseInteractor,
) {

  private val _databaseEditing: MutableStateFlow<DatabaseEditing?> = MutableStateFlow(null)
  val databaseEditing: Flow<DatabaseEditing?> = _databaseEditing

  fun closeDatabaseEditing() {
    _databaseEditing.value = null
  }

  private fun validateFields(fields: List<Database.Field>): String? {
    fields.forEach { field ->
      if (field.name.isBlank()) {
        return "Field names cannot be empty"
      }
    }

    val names = fields.map { it.name }
    if (names.toSet().size != names.size) {
      return "Duplicate field names are not allowed"
    }

    fields.forEach { field ->
      if (field.type.isBlank()) {
        return "Field types cannot be empty"
      }
    }

    return null
  }

  fun startDatabaseCreating() {
    _databaseEditing.value = DatabaseEditing(Database("", emptyList()))
  }

  fun addField() {
    _databaseEditing.value = _databaseEditing.value?.addField()
  }

  fun updateFieldName(index: Int, name: String) {
    _databaseEditing.value = _databaseEditing.value?.updateFieldName(index, name)
  }

  fun updateFieldType(index: Int, type: String) {
    _databaseEditing.value = _databaseEditing.value?.updateFieldType(index, type)
  }

  fun removeField(index: Int) {
    _databaseEditing.value = _databaseEditing.value?.removeField(index)
  }
}