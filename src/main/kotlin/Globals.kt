import database.DatabaseInteractor
import repository.TodoRepository
import stub.StubDatabaseInteractor
import ui.screens.task_detail.TaskDetailViewModel
import ui.screens.task_list.TaskListViewModel

object Globals {
  val databaseInteractor: DatabaseInteractor = StubDatabaseInteractor()
  val todoRepository: TodoRepository = TodoRepository(databaseInteractor)
  val mainViewModel: MainViewModel = MainViewModel(todoRepository)
  val taskListViewModel: TaskListViewModel = TaskListViewModel(todoRepository)
  val taskDetailViewModelFactory: (Long) -> TaskDetailViewModel = { id -> TaskDetailViewModel(id, todoRepository) }
}