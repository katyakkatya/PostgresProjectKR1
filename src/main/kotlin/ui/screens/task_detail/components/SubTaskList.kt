package ui.screens.task_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Subtask

@Composable
fun SubTaskList(
  subtasks: List<Subtask>,
  onItemClick: (Int) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 32.dp, vertical = 32.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    subtasks.forEachIndexed { index, subtask ->
      SubTaskCard(subtask = subtask) {
        onItemClick(index)
      }
    }
  }
}