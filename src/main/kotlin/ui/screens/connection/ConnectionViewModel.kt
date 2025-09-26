package ui.screens.connection

import database.DatabaseInteractor
import database.request.ConnectionRequest
import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository

class ConnectionViewModel(
  private val databaseInteractor: DatabaseInteractor,
  private val todoRepository: TodoRepository,
) {
  private val _urlFlow = MutableStateFlow("")
  val urlFlow = _urlFlow

  private val _usernameFlow = MutableStateFlow("")
  val usernameFlow = _usernameFlow

  private val _passwordFlow = MutableStateFlow("")
  val passwordFlow = _passwordFlow

  private val _isLoadingFlow = MutableStateFlow(false)
  val isLoadingFlow = _isLoadingFlow

  private val _successfulConnectionFlow = MutableStateFlow(false)
  val successfulConnectionFlow = _successfulConnectionFlow

  fun onUrlChanged(url: String) {
    _urlFlow.value = url
  }

  fun onUsernameChanged(username: String) {
    _usernameFlow.value = username
  }

  fun onPasswordChanged(password: String) {
    _passwordFlow.value = password
  }

  fun tryConnect() {
    _isLoadingFlow.value = true
    val result = databaseInteractor.tryConnect(
      ConnectionRequest(_urlFlow.value, _usernameFlow.value, _passwordFlow.value)
    )
    if (result == true) {
      _successfulConnectionFlow.value = true
    } else {
      todoRepository.showErrorMessage("Не удалось подключиться к базе данных")
    }
    _isLoadingFlow.value = false
  }
}