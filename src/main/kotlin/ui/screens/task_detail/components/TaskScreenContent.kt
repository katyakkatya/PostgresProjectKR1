import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.model.DbTaskStatus
import models.TaskDetail

@Composable
fun TaskScreenContent(
  innerPadding: PaddingValues,
  task: TaskDetail,
  onAddSubtaskClick: () -> Unit,
  onSubtaskToggled: (Int) -> Unit,
  onRelatedTaskClick: (Long) -> Unit,
  onStatusChanged: (DbTaskStatus) -> Unit,
  onAddRelatedTaskClick: () -> Unit,
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.background)
      .padding(innerPadding)
      .padding(horizontal = 32.dp, vertical = 24.dp)
  ) {
    item {
      TaskHeader(task = task)
      TaskStatusInfo(task = task) { status ->
        onStatusChanged(status)
      }
      SubtasksSection(
        subtasks = task.subtasks,
        onItemClick = { index -> onSubtaskToggled(index) },
        onAddSubtaskClick = onAddSubtaskClick
      )
      RelatedTasksSection(
        relatedTasks = task.relatedTasks,
        onRelatedTaskClick = onRelatedTaskClick,
        onAddRelatedTaskClick = onAddRelatedTaskClick,
      )
    }
  }
}