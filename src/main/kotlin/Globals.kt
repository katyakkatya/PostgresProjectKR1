import database.ApplicationDatabaseInteractor
import database.DatabaseInteractor
import repository.TodoRepository
import stub.StubDatabaseInteractor
import ui.screens.connection.ConnectionViewModel
import ui.screens.database_creation.DatabaseCreationViewModel
import ui.screens.logs.LogsViewModel
import ui.screens.task_detail.TaskDetailViewModel
import ui.screens.task_list.TaskListViewModel

object Globals {
  val databaseInteractor: DatabaseInteractor = StubDatabaseInteractor()
  val todoRepository: TodoRepository = TodoRepository(databaseInteractor)
  val mainViewModel: MainViewModel = MainViewModel(todoRepository)
  val taskListViewModel: TaskListViewModel = TaskListViewModel(todoRepository)
  val connectionViewModel: ConnectionViewModel = ConnectionViewModel(databaseInteractor, todoRepository)
  val databaseCreationViewModel: DatabaseCreationViewModel =
    DatabaseCreationViewModel(databaseInteractor, todoRepository)
  val logsViewModel: LogsViewModel = LogsViewModel(todoRepository)
  val taskDetailViewModelFactory: (Long) -> TaskDetailViewModel = { id -> TaskDetailViewModel(id, todoRepository) }
}