import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import database.ApplicationDatabaseInteractor
import database.request.ConnectionRequest
import ui.AppNavigation

fun main() = application {
    var test  = ApplicationDatabaseInteractor()
    var a = test.tryConnect(ConnectionRequest("jdbc:postgresql://localhost:9876/postgres",
        "postgres", "postgres"))
    test.createDatabase();
    println(a)

    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDo App",
        state = WindowState(
            size = DpSize(
                width = 800.dp,
                height = 1200.dp
            )
        )
    ) {
        AppNavigation(Globals.mainViewModel)
    }
}