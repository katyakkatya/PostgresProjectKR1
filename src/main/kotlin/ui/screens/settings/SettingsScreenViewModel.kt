package ui.screens.settings

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import repository.Settings
import repository.TodoRepository

class SettingsScreenViewModel(
  private val todoRepository: TodoRepository,
) {
  private val _minLengthInputFlow = MutableStateFlow("")
  val minLengthInputFlow = _minLengthInputFlow

  private val _maxLengthInputFlow = MutableStateFlow("")
  val maxLengthInputFlow = _maxLengthInputFlow

  private val _forceUniqueTitle = MutableStateFlow(false)
  val forceUniqueTitle = _forceUniqueTitle

  private val _errorFlow = MutableStateFlow<String?>(null)
  val errorFlow = _errorFlow

  private val _successFlow = MutableStateFlow(false)
  val successFlow = _successFlow

  fun onInit() {
    CoroutineScope(Dispatchers.IO).launch {
      todoRepository.loadSettings()
      val settings = todoRepository.settingsFlow.first()
      _minLengthInputFlow.value = settings.minTaskTitleLength.toString()
      _maxLengthInputFlow.value = settings.maxTaskTitleLength.toString()
      _forceUniqueTitle.value = settings.forceUniqueTaskTitle
    }
    _successFlow.value = false
  }

  fun setMinLengthInput(input: String) {
    _minLengthInputFlow.value = input.filter { it.isDigit() }
  }

  fun setMaxLengthInput(input: String) {
    _maxLengthInputFlow.value = input.filter { it.isDigit() }
  }

  fun setForceUniqueTitle(force: Boolean) {
    _forceUniqueTitle.value = force
  }

  fun applySettings() {
    _errorFlow.value = null
    successFlow.value = false
    val minLength = _minLengthInputFlow.value.toIntOrNull()
    if (minLength == null) {
      _errorFlow.value = "Введена некорректная минимальная длина"
      return
    }
    val maxLength = _maxLengthInputFlow.value.toIntOrNull()
    if (maxLength == null) {
      _errorFlow.value = "Введена некорректная максимальная длина"
      return
    }
    if (maxLength < minLength) {
      _errorFlow.value = "Минимальная длина должна быть меньше или равна максимальной длине"
      return
    }
    if (maxLength > 100) {
      _errorFlow.value = "Нельзя задать максимальную длину больше 100 символов"
      return
    }
    val settingsToApply = Settings(
      forceUniqueTaskTitle = _forceUniqueTitle.value,
      minTaskTitleLength = minLength,
      maxTaskTitleLength = maxLength,
    )
    todoRepository.applySettings(settingsToApply)
    successFlow.value = true
  }
}
