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
import theme.TodoAppTheme
import ui.AppNavigation
import java.awt.Dimension
import java.io.PrintStream


fun main() = application {
//    try {
//        System.setOut(PrintStream(System.out, true, "UTF-8"))
//        System.setErr(PrintStream(System.err, true, "UTF-8"))
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    var test  = ApplicationDatabaseInteractor()
//    var a = test.tryConnect(
//        ConnectionRequest(
//            "jdbc:postgresql://localhost:9876/postgres",
//            "postgres", "postgres"
//        )
//    )
//    test.createDatabase();
//
//    test.createTask(CreateTaskRequest("gj", listOf("w"), listOf()));
//    test.createTask(CreateTaskRequest("rtnmmrtl", listOf("w"), listOf()));
//    test.createTask(CreateTaskRequest("ZZZZZZZZZ", listOf("w"), listOf()));
//    test.createTask(CreateTaskRequest("fghfj", listOf("w"), listOf(1, 2)));
//    test.changeSubtaskCompletion(1, 0);
//    println(test.addSubtask(1L, "test"))
//    println(test.createConnection(1L, 2L))
//    test.updateStatus(1L, DbTaskStatus.IN_REVIEW);
//    test.getTaskDetail(1L).data?.relatedTasks()?.forEach { s -> println(s) }
//    println(123123123)
//    test.deleteTask(1L);
//    test.getTaskDetail(4L).data?.relatedTasks()?.forEach { s -> println(s) }
//    test.getTaskList(TaskListRequest(listOf(DbTaskStatus.BACKLOG))).data?.forEach { dbTaskItem -> println(dbTaskItem) };

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

        TodoAppTheme { AppNavigation(Globals.mainViewModel) }
    }
}