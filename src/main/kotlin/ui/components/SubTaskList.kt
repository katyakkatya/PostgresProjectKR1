package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
      .fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    subtasks.forEachIndexed { index, subtask ->
      SubTaskCard(subtask = subtask) {
        onItemClick(index)
      }
    }
  }
}