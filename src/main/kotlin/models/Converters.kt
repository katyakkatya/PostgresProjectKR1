package models

import database.model.DbTaskDetail
import database.model.DbTaskItem
import database.model.DbTaskStatus
import database.model.UserWithTaskCount
import java.time.LocalDate
import java.util.*


fun DbTaskItem.asTask(): TaskItemModel {
  val calendar = Calendar.getInstance()
  calendar.setTime(startedAt)

  val localDate = LocalDate.of(
    calendar.get(Calendar.YEAR),
    calendar.get(Calendar.MONTH) + 1,
    calendar.get(Calendar.DAY_OF_MONTH)
  )
  val progress = if (subtaskCount == 0) 0f else completedSubtasksCount.toFloat() / subtaskCount.toFloat()
  return TaskItemModel(
    id = id,
    title = title,
    status = status.asTaskStatus(),
    date = localDate,
    progress = progress,
  )
}

fun DbTaskStatus.asTaskStatus(): TaskStatus {
  return when (this) {
    DbTaskStatus.BACKLOG -> TaskStatus.BACKLOG
    DbTaskStatus.IN_PROGRESS -> TaskStatus.IN_PROGRESS
    DbTaskStatus.IN_REVIEW -> TaskStatus.IN_REVIEW
    DbTaskStatus.DONE -> TaskStatus.DONE
    DbTaskStatus.DROPPED -> TaskStatus.DROPPED
  }
}

fun DbTaskDetail.asTaskDetail(): TaskDetail {
  val calendar = Calendar.getInstance()
  calendar.setTime(startedAt)

  val localDate = LocalDate.of(
    calendar.get(Calendar.YEAR),
    calendar.get(Calendar.MONTH) + 1,
    calendar.get(Calendar.DAY_OF_MONTH)
  )

  val completedSubtasksCount = subtaskStatus.count { it == true }
  val progress = if (subtaskStatus.isEmpty()) 0f else completedSubtasksCount.toFloat() / subtaskStatus.count().toFloat()
  return TaskDetail(
    id = id,
    title = title,
    status = status.asTaskStatus(),
    subtasks = subtaskTitles.mapIndexed { index, title ->
      Subtask(title, subtaskStatus[index])
    },
    relatedTasks = relatedTasks.map { it.asTask() },
    date = localDate,
    progress = progress
  )
}

fun UserWithTaskCount.asUserWithTasks(): UserWithTasksModel {
  return UserWithTasksModel(
    id = user.id,
    name = user.name,
    taskCount = tasksCount,
  )
}
