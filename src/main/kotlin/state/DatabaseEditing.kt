package state

import database.model.Database

data class DatabaseEditing(
  val database: Database,
  val error: String? = null,
  val initial: Boolean = true,
) {
  fun updateTableName(name: String): DatabaseEditing {
    return this.copy(database = database.copy(name = name))
  }

  fun updateError(error: String?): DatabaseEditing {
    return this.copy(error = error)
  }
}

