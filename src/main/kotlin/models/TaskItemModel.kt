package models

import androidx.compose.ui.graphics.Color
import database.model.DbTaskStatus
import java.time.LocalDate

sealed interface TaskStatus {
  val color: Color
  val text: String

  data object BACKLOG : TaskStatus {
    override val color: Color = Color(0xFF787878)
    override val text: String = DbTaskStatus.BACKLOG
  }

  data object IN_PROGRESS : TaskStatus {
    override val color: Color = Color(0xFF3A61C5)
    override val text: String = DbTaskStatus.IN_PROGRESS
  }

  data object IN_REVIEW : TaskStatus {
    override val color: Color = Color(0xFFF68442)
    override val text: String = DbTaskStatus.IN_REVIEW
  }

  data object DONE : TaskStatus {
    override val color: Color = Color(0xFF66BB6A)
    override val text: String = DbTaskStatus.DONE
  }

  data object DROPPED : TaskStatus {
    override val color: Color = Color(0xFFE35E5E)
    override val text: String = DbTaskStatus.DROPPED
  }

  data class CUSTOM(override val text: String) : TaskStatus {
    override val color: Color = Color(0xFF787878)
  }
}

data class TaskItemModel(
  val id: Long,
  val title: String,
  val status: TaskStatus,
  val date: LocalDate,
  val progress: Float,
  val time: String,
) {
  val color: Color get() = status.color
}