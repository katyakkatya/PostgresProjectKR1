package ui.screens.logs

import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository

class LogsViewModel(
  private val todoRepository: TodoRepository,
) {
  val logsFlow = todoRepository.logsFlow
  private val _onlyErrorsFlow = MutableStateFlow(false)
  val onlyErrorsFlow = _onlyErrorsFlow

  fun toggleOnlyErrors() {
    _onlyErrorsFlow.value = !_onlyErrorsFlow.value
  }
}