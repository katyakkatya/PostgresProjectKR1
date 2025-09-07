import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.StubDatabaseInteractor
import gui.App
import gui.Globals
import gui.viewmodel.DatabaseViewModel
import gui.viewmodel.TableViewModel

fun main(): Unit = application {
    val interactor = StubDatabaseInteractor()
    Globals.tableViewModel = TableViewModel(interactor)
    Globals.databaseViewModel = DatabaseViewModel(interactor)
    Window(onCloseRequest = ::exitApplication, title = "Database explorer") {
        App()
    }
}