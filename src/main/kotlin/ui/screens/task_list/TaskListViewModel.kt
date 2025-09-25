package ui.screens.task_list

import database.model.DbTaskStatus
import database.request.TaskListRequest
import repository.TodoRepository

class TaskListViewModel(
  private val todoRepository: TodoRepository,
) {
  val tasksListFlow = todoRepository.tasksListFlow

  init {
    todoRepository.getTasksList(TaskListRequest(listOf<DbTaskStatus>()))
  }
}