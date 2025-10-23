package models

import java.time.LocalDate

data class TaskDetail(
  val id: Long,
  val title: String,
  val status: TaskStatus,
  val subtasks: List<Subtask>,
  val relatedTasks: List<TaskItemModel>,
  val date: LocalDate,
  val progress: Float,
  val author: UserModel?
)