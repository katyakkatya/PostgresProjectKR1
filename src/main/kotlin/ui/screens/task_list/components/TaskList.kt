package ui.screens.task_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.TaskItemModel

@Composable
fun TaskList(
  tasks: List<TaskItemModel>,
  onTaskClick: (Long) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 32.dp),
    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
  ) {
    items(
      items = tasks
    ) { task ->
      TaskItem(
        task = task,
        onTaskClick = { onTaskClick(it.id) }
      )
    }
  }
}