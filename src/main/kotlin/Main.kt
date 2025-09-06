import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import database.StubDatabaseInteractor
import gui.App

fun main(): Unit = application {
    val interactor = StubDatabaseInteractor()
    Window(onCloseRequest = ::exitApplication, title = "Database explorer") {
        App()
    }
}