import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import database.ApplicationDatabaseInteractor
import database.model.DbTaskStatus
import database.request.ConnectionRequest
import database.request.CreateTaskRequest
import database.request.TaskListRequest
import ui.AppNavigation
import java.awt.Dimension
import java.io.PrintStream


fun main() = application {
    try {
        System.setOut(PrintStream(System.out, true, "UTF-8"))
        System.setErr(PrintStream(System.err, true, "UTF-8"))
    } catch (e: Exception) {
        e.printStackTrace()
    }
    var test  = ApplicationDatabaseInteractor()
    var a = test.tryConnect(ConnectionRequest("jdbc:postgresql://localhost:9876/postgres",
        "postgres", "postgres"))
    test.createDatabase();

    test.createTask(CreateTaskRequest("gjrt", listOf("w"), listOf()));
    test.createTask(CreateTaskRequest("rtnmmrtl", listOf("w"), listOf()));
    test.createTask(CreateTaskRequest("ZZZZZZZZZ", listOf("w"), listOf()));
    test.createTask(CreateTaskRequest("fghfj", listOf("w"), listOf(1, 2)));
    //test.changeSubtaskCompletion(1, 0);
    println(test.addSubtask(1, "test"))
    test.getTaskDetail(1L).data?.subtaskStatus()?.forEach { s -> println(s) };
    //test.getTaskList(TaskListRequest(listOf(DbTaskStatus.BACKLOG))).data?.forEach { dbTaskItem -> println(dbTaskItem) };


    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDo App",
        state = WindowState(
            size = DpSize(
                width = 800.dp,
                height = 1200.dp
            ),
        ),
    ) {
        val density = LocalDensity.current
        val minWidth = with(density) { 300.dp.toPx().toInt() }
        val minHeight = with(density) { 500.dp.toPx().toInt() }
        window.minimumSize = Dimension(minWidth, minHeight)
        AppNavigation(Globals.mainViewModel)
    }
}