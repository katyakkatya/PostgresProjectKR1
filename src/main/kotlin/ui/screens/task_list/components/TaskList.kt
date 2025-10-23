package ui.screens.task_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.TaskItemModel

@Composable
fun TaskList(
  tasks: List<TaskItemModel>,
  onTaskClick: (Long) -> Unit
) {
  Column(
    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 32.dp),
    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
  ) {
    tasks.forEach { task ->
      TaskItem(
        task = task,
        onTaskClick = { onTaskClick(it.id) }
      )
    }
  }
}