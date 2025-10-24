package ui.screens.users

import database.request.GetUsersWithTasksRequest
import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository
import ui.screens.common.dialogs.AddUserDialogState

class UsersScreenViewModel(
  private val todoRepository: TodoRepository,
) {
  val usersFlow = todoRepository.usersWithTasksListFlow

  private var request = GetUsersWithTasksRequest("", 0)

  private val _regexInputFlow = MutableStateFlow("")
  val regexInputFlow = _regexInputFlow

  private val _minTasksInputFlow = MutableStateFlow("0")
  val minTasksInputFlow = _minTasksInputFlow

  private val _errorFlow = MutableStateFlow<String?>(null)
  val errorFlow = _errorFlow

  private val _addUserDialogStateFlow: MutableStateFlow<AddUserDialogState> =
    MutableStateFlow(AddUserDialogState.Closed)
  val addUserDialogStateFlow = _addUserDialogStateFlow

  fun onInit() {
    updateUsersList()
  }

  fun updateUsersList() {
    todoRepository.getUsersWithTasksList(request)
  }

  fun updateRegex(regex: String) {
    request = GetUsersWithTasksRequest(regex, request.minTasks)
    _regexInputFlow.value = regex
    updateUsersList()
  }

  fun updateMinTasks(input: String) {
    val input = input.filter { it.isDigit() }
    _minTasksInputFlow.value = input
    val minTasks = input.trim().toIntOrNull() ?: 0
    if (minTasks < 0) {
      _errorFlow.value = "Некорректное значение минимального количества задач"
      return
    }
    request = GetUsersWithTasksRequest(request.regexQuery, minTasks)
    updateUsersList()
  }

  fun openAddUserDialog() {
    _addUserDialogStateFlow.value = AddUserDialogState.Opened("")
  }

  fun closeAddUserDialog() {
    _addUserDialogStateFlow.value = AddUserDialogState.Closed
  }

  fun onNewUserNameChanged(name: String) {
    _addUserDialogStateFlow.value = (_addUserDialogStateFlow.value as AddUserDialogState.Opened).copy(name = name)
  }

  fun tryAddNewUser() {
    if (!validateNewUser()) {
      return
    }
    todoRepository.createUser((_addUserDialogStateFlow.value as AddUserDialogState.Opened).name)
    closeAddUserDialog()
    updateUsersList()
  }

  fun validateNewUser(): Boolean {
    val name = (_addUserDialogStateFlow.value as AddUserDialogState.Opened).name
    if (name.length.coerceIn(2, 20) != name.length) {
      _addUserDialogStateFlow.value =
        (_addUserDialogStateFlow.value as AddUserDialogState.Opened).copy(error = "Имя должно быть от 2 до 20 символов")
      return false
    }
    return true
  }
}