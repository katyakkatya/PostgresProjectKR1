package ui.screens.logs

import repository.TodoRepository

class LogsViewModel(
  private val todoRepository: TodoRepository,
) {
  val logsFlow = todoRepository.logsFlow
}