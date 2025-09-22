import repository.TodoRepository

class MainViewModel(
  private val repository: TodoRepository
) {
  val errorMessageFlow = repository.errorMessageFlow
}