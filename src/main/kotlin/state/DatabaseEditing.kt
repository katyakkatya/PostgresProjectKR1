package state

import database.model.Database
import kotlin.apply

data class DatabaseEditing(
  val database: Database,
  val error: String? = null,
) {
  fun updateFieldName(index: Int, name: String): DatabaseEditing {
    val updatedFields = database.fields.toMutableList().apply {
      this[index] = this[index].copy(name = name)
    }
    return copy(database = database.copy(fields = updatedFields))
  }

  fun updateFieldType(index: Int, type: String): DatabaseEditing {
    val updatedFields = database.fields.toMutableList().apply {
      val currentField = this[index]
      val convertedValue = convertValue(currentField.name, type)
      this[index] = this[index].copy(name = convertedValue, type = type)
    }
    return copy(database = database.copy(fields = updatedFields))
  }

  fun addField(): DatabaseEditing {
    val updatedFields = database.fields + Database.Field("", "TEXT")
    return copy(database = database.copy(fields = updatedFields))
  }

  fun removeField(index: Int): DatabaseEditing {
    val updatedFields = database.fields.filterIndexed { i, _ -> i != index }
    return copy(database = database.copy(fields = updatedFields))
  }

  private fun convertValue(value: String, targetType: String): String {
    return when (targetType.uppercase()) {
      "INTEGER" -> {
        if (value.toIntOrNull() != null) value else ""
      }
      "REAL" -> {
        if (value.toDoubleOrNull() != null) value else ""
      }
      "NUMERIC" -> {
        if (value.toDoubleOrNull() != null) value else ""
      }
      "BLOB" -> {
        value
      }
      "TEXT" -> {
        value
      }
      else -> value
    }
  }

  private fun isValidValue(value: String, targetType: String): Boolean {
    return when (targetType.uppercase()) {
      "INTEGER" -> value.toIntOrNull() != null
      "REAL" -> value.toDoubleOrNull() != null
      "NUMERIC" -> value.toDoubleOrNull() != null
      "BLOB" -> true
      "TEXT" -> true
      else -> true
    }
  }
}

