package models

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

enum class TaskStatus(val color: Color) {
    BACKLOG(Color(0xFF787878)),
    IN_PROGRESS(Color(0xFF3A61C5)),
    IN_REVIEW(Color(0xFFF68442)),
    DONE(Color(0xFF66BB6A)),
    DROPPED(Color(0xFFE35E5E))
}

data class TaskItemModel(
    val id: Long,
    val title: String,
    val status: TaskStatus,
    val date: LocalDate,
    val progress: Float
) {
    val color: Color get() = status.color
}