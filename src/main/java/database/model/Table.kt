package database.model

data class Table(
  val name: String,
  val fields: List<Field>
) {
  data class Field(
    val name: String,
    val type: String
  )

  companion object {
    val SUPPORTED_COLUMN_TYPES = listOf("TEXT", "INTEGER", "REAL", "BLOB", "NUMERIC")
  }
}
