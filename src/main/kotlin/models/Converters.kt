package models

import database.model.DbTaskDetail
import database.model.DbTaskItem
import database.model.DbTaskStatus
import java.time.ZoneId

fun DbTaskItem.asTask(): TaskItemModel {
  return TaskItemModel(
    id = id,
    title = title,
    status = status.asTaskStatus(),
    date = startedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
    progress = completedSubtasksCount.toFloat() / subtaskCount.toFloat()
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
  return TaskDetail(
    id = id,
    title = title,
    status = status.asTaskStatus(),
    subtasks = subtaskTitles.mapIndexed { index, title ->
      Subtask(title, subtaskStatus[index])
    },
    relatedTasks = relatedTasks.map { it.asTask() },
    date = startedAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
    progress = subtaskStatus.count { it == true }.toFloat() / subtaskStatus.count()
  )
}
