package ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Subtask

@Composable
fun SubTaskList(
  subtasks: List<Subtask>,
  onItemClick: (Int) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 400.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    contentPadding = PaddingValues(16.dp)
  ) {
    itemsIndexed(
      items = subtasks,
      key = { index, subtask -> "subtask_${index}_${subtask.title}" }
    ) { index, subtask ->
      SubTaskCard(subtask = subtask) {
        onItemClick(index)
      }
    }
  }
}