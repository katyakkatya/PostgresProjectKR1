package models

data class UserWithTasksModel(
  val id: Long,
  val name: String,
  val taskCount: Int,
)
