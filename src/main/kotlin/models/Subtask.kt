package models

data class Subtask(
    val title: String,
    val isCompleted: Boolean = false,
    val order: Int = 0
)