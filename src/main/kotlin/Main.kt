import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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
import database.request.CreateUserRequest
import database.request.FormattingOptions
import database.request.TaskListSortingType
import database.request.GetUsersWithTasksRequest
import database.request.TaskListRequest
import database.request.TaskListSorting
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
//    test.tryConnect(ConnectionRequest("jdbc:postgresql://localhost:9876/postgres", "postgres", "postgres"))
//    test.createDatabase();
//    test.allUsers.data?.forEach{s -> println(s)};
//
//    test.createTask(CreateTaskRequest("gj", listOf("w"), listOf(), null));
//    test.createTask(CreateTaskRequest("atnmmrtl", listOf("w"), listOf(), 1));
//    test.createTask(CreateTaskRequest("ZZZZZZZZZZZZZZZZZZZZZZZZZ", listOf("w"), listOf(), 1));
//    test.createTask(CreateTaskRequest("fghfj", listOf("w"), listOf(1, 2), 1));
//    test.createUser(CreateUserRequest("user1"))
//    test.changeSubtaskCompletion(1, 0);
//    println(test.addSubtask(1L, "test"))
//    println(test.createConnection(1L, 2L))
//    test.updateStatus(1L, DbTaskStatus.IN_REVIEW);
//    println(test.getTaskDetail(2L).data)
//    println(123123123)
//    test.deleteTask(1L)
//    test.getTaskDetail(4L).data?.relatedTasks()?.forEach { s -> println(s) }
//    test.addUserToTask(1,1)
//    test.setTaskTitleMinLength(1);
//    test.setTaskTitleMaxLength(3);
//    println(test.minTaskTitleLength)
//    println(test.getForceUniqueTaskTitle())
//    test.setShouldForceUniqueName(true)
//    println("111 " + test.getForceUniqueTaskTitle())
//    test.getUsersWithTasks(GetUsersWithTasksRequest("%", 0)).data?.forEach{s -> println(s.toString())}
//    test.getTaskList(TaskListRequest(listOf(DbTaskStatus.BACKLOG), 1, TaskListSorting(TaskListSortingType.BY_TASK_NAME, true),
//        FormattingOptions(false, false, false, false, false)
//    )).data?.forEach { dbTaskItem -> println(dbTaskItem) };
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

        TodoAppTheme {
            Surface(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
            ) {
                AppNavigation(Globals.mainViewModel)
            }
        }
    }
}