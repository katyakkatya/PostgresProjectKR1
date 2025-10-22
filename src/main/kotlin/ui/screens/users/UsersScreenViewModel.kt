package ui.screens.users

import database.request.GetUsersWithTasksRequest
import kotlinx.coroutines.flow.MutableStateFlow
import repository.TodoRepository

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
}