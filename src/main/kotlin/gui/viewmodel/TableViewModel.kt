package gui.viewmodel

import database.DatabaseInteractor
import database.model.Table
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import state.TableEditing

class TableViewModel(
  private val databaseInteractor: DatabaseInteractor,
) {

  private val _tableEditing: MutableStateFlow<TableEditing?> = MutableStateFlow(null)
  val tableEditing: Flow<TableEditing?> = _tableEditing

  fun closeTableEditing() {
    _tableEditing.value = null
  }

  private fun validateFields(fields: List<Table.Field>): String? {
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
    _tableEditing.value = TableEditing(Table("", emptyList()))
  }

  fun addField() {
    _tableEditing.value = _tableEditing.value?.addField()
  }

  fun updateFieldName(index: Int, name: String) {
    _tableEditing.value = _tableEditing.value?.updateFieldName(index, name)
  }

  fun updateFieldType(index: Int, type: String) {
    _tableEditing.value = _tableEditing.value?.updateFieldType(index, type)
  }

  fun removeField(index: Int) {
    _tableEditing.value = _tableEditing.value?.removeField(index)
  }
}