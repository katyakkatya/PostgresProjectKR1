import database.ApplicationDatabaseInteractor
import database.DatabaseInteractor
import repository.TodoRepository
import stub.StubDatabaseInteractor
import ui.screens.connection.ConnectionViewModel
import ui.screens.database_creation.DatabaseCreationViewModel
import ui.screens.logs.LogsViewModel
import ui.screens.settings.SettingsScreenViewModel
import ui.screens.task_detail.TaskDetailViewModel
import ui.screens.task_list.TaskListViewModel
import ui.screens.users.UsersScreenViewModel

object Globals {
  val databaseInteractor: DatabaseInteractor = ApplicationDatabaseInteractor()
  val todoRepository: TodoRepository = TodoRepository(databaseInteractor)
  val mainViewModel: MainViewModel = MainViewModel(todoRepository)
  val taskListViewModel: TaskListViewModel = TaskListViewModel(todoRepository)
  val connectionViewModel: ConnectionViewModel = ConnectionViewModel(databaseInteractor, todoRepository)
  val databaseCreationViewModel: DatabaseCreationViewModel =
    DatabaseCreationViewModel(databaseInteractor, todoRepository)
  val logsViewModel: LogsViewModel = LogsViewModel(todoRepository)
  val taskDetailViewModelFactory: (Long) -> TaskDetailViewModel = { id -> TaskDetailViewModel(id, todoRepository) }
  val settingsViewModel: SettingsScreenViewModel = SettingsScreenViewModel(todoRepository)
  val usersViewModel: UsersScreenViewModel = UsersScreenViewModel(todoRepository)
}