package ui.screens.database_log


class DatabaseLogViewModel {
  private val databaseLog: List<String> = mutableListOf()
  private val isError: Boolean = false

}

data class LogEntry(
  val message: String,
  val isError: Boolean
) {
  companion object {
    fun fromString(logString: String): LogEntry {
      val isError = logString.contains("ERROR", ignoreCase = true) ||
        logString.contains("FAILED", ignoreCase = true)
      return LogEntry(logString, isError)
    }
  }
}

