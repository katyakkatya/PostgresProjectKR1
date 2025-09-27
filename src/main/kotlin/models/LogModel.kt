package models

data class LogModel(
  val type: LogType,
  val message: String,
)

enum class LogType {
  INFO,
  ERROR
}
