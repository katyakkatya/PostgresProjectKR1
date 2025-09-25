package ui.screens.connection

import database.DatabaseInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository

class ConnectionViewModel(
  private val databaseInteractor: DatabaseInteractor,
  private val todoRepository: TodoRepository,
) {
  private val _urlFlow = MutableStateFlow("")
  val urlFlow = _urlFlow

  private val _isLoadingFlow = MutableStateFlow(false)
  val isLoadingFlow = _isLoadingFlow

  private val _successfulConnectionFlow = MutableStateFlow(false)
  val successfulConnectionFlow = _successfulConnectionFlow

  fun onUrlChanged(url: String) {
    _urlFlow.value = url
  }

  fun tryConnect() {
    _isLoadingFlow.value = true
    databaseInteractor.setDatabaseUrl(urlFlow.value)
    val result = databaseInteractor.tryConnect()
    if (result == true) {
      _successfulConnectionFlow.value = true
    } else {
      todoRepository.showErrorMessage("Не удалось подключиться к базе данных")
    }
    _isLoadingFlow.value = false
  }
}