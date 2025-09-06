import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.StubDatabaseInteractor
import gui.App
import gui.DatabaseViewModel

fun main(): Unit = application {
    val interactor = StubDatabaseInteractor()
    val viewModel = DatabaseViewModel(interactor)
    Window(onCloseRequest = ::exitApplication, title = "Database explorer") {
        App(viewModel)
    }
}