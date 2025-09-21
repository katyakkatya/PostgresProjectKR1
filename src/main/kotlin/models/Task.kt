package models

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

enum class TaskStatus(val color: Color) {
    TODO(Color(0xFFFF5252)),
    IN_PROGRESS(Color(0xFFFFA726)),
    DONE(Color(0xFF66BB6A))
}

data class Task(
    val title: String,
    val status: TaskStatus = TaskStatus.TODO,
    val subtasks: List<Subtask> = emptyList(),
    val relatedTasks: List<Task> = emptyList(),
    val date: LocalDate? = null,
    val progress: Float = 0f
) {
    init {
        require(progress in 0.0f..1.0f) {
            "Progress must be between 0 and 1, but was $progress"
        }

        if (status == TaskStatus.DONE && progress < 1f) {
            throw IllegalArgumentException("Done tasks must have progress 1.0")
        }
    }

    val color: Color get() = status.color
    val totalSubtasks: Int get() = subtasks.size
    val completedSubtasks: Int get() = subtasks.count { it.isCompleted }
    val totalRelatedTasks: Int get() = relatedTasks.size

    val autoProgress: Float get() =
        if (subtasks.isEmpty()) progress
        else completedSubtasks.toFloat() / totalSubtasks

    val suggestedStatus: TaskStatus
        get() = when {
            progress >= 1f -> TaskStatus.DONE
            progress > 0f -> TaskStatus.IN_PROGRESS
            else -> TaskStatus.TODO
        }

    val isOverdue: Boolean
        get() = date != null && date.isBefore(LocalDate.now()) && status != TaskStatus.DONE

    val daysUntilDeadline: Long?
        get() = date?.let { java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), it) }
}