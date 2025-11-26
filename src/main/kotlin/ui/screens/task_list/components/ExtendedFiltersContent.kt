package ui.screens.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import database.model.extended_filters.RelatedTasksStatusFilter
import database.model.extended_filters.RelativesFilter
import database.model.extended_filters.SubtasksCompletionFilter
import database.model.extended_filters.SubtasksCompletionFilter.SubtasksCompletionFilterField
import database.model.extended_filters.SubtasksCompletionFilter.SubtasksCompletionFilterType
import ui.screens.task_list.components.Constants.ALL
import ui.screens.task_list.components.Constants.COMPLETED
import ui.screens.task_list.components.Constants.NOT_COMPLETED
import ui.screens.task_list.components.Constants.SOME

// TODO: доработать механизм для остальных фильтров
@Composable
fun ExtendedFiltersContent(
  state: ExtendedFiltersState,
  signals: ExtendedFiltersSignals,
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Text(
      text = "Расширенные фильтры",
      fontSize = 32.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Left,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h5,
    )

    Divider(
      modifier = Modifier.padding(bottom = 8.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    Spacer(modifier = Modifier.height(8.dp))


    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text("Где")

      Box {
        val subtasksFilterTypeOptions = listOf(ALL, SOME)
        var subtasksFilterTypeMenuExpanded by remember { mutableStateOf(false) }
        Text(
          text = when (state.subtasksFilter?.type) {
            SubtasksCompletionFilterType.ALL_COMPLETED -> ALL
            SubtasksCompletionFilterType.SOME_COMPLETED -> SOME
            null -> " "
          },
          modifier = Modifier
            .defaultMinSize(minWidth = 100.dp)
            .padding(16.dp)
            .background(Color.Red)
            .clickable {
              subtasksFilterTypeMenuExpanded = true
            }
        )
        DropdownMenu(
          expanded = subtasksFilterTypeMenuExpanded,
          onDismissRequest = { subtasksFilterTypeMenuExpanded = false }
        ) {
          subtasksFilterTypeOptions.forEach { option ->
            DropdownMenuItem(
              onClick = {
                when (option) {
                  ALL -> signals.onSubtasksFilterAllTypeClicked()
                  SOME -> signals.onSubtasksFilterSomeTypeClicked()
                }
                subtasksFilterTypeMenuExpanded = false
              }
            ) {
              Text(option)
            }
          }
        }
      }

      Text("подзадачи")

      Box {
        val subtasksFilterFieldOptions = listOf(COMPLETED, NOT_COMPLETED)
        var subtasksFilterFieldMenuExpanded by remember { mutableStateOf(false) }
        Text(
          text = when (state.subtasksFilter?.field) {
            SubtasksCompletionFilterField.COMPLETED -> COMPLETED
            SubtasksCompletionFilterField.NOT_COMPLETED -> NOT_COMPLETED
            null -> " "
          },
          modifier = Modifier
            .defaultMinSize(minWidth = 100.dp)
            .padding(16.dp)
            .background(Color.Red)
            .clickable {
              subtasksFilterFieldMenuExpanded = true
            }
        )
        DropdownMenu(
          expanded = subtasksFilterFieldMenuExpanded,
          onDismissRequest = { subtasksFilterFieldMenuExpanded = false }
        ) {
          subtasksFilterFieldOptions.forEach { option ->
            DropdownMenuItem(
              onClick = {
                when (option) {
                  COMPLETED -> signals.onSubtasksFilterCompletedFieldClicked()
                  NOT_COMPLETED -> signals.onSubtasksFilterNotCompletedFieldClicked()
                }
                subtasksFilterFieldMenuExpanded = false
              }
            ) {
              Text(option)
            }
          }
        }
      }
    }
  }
}

private object Constants {
  const val ALL = "Все"
  const val SOME = "Некоторые"
  const val HAS = "Есть"
  const val NONE = "Нет"
  const val COMPLETED = "Выполнены"
  const val NOT_COMPLETED = "Не выполнены"
}

data class ExtendedFiltersState(
  val relatedTasksFilter: RelatedTasksStatusFilter? = null,
  val subtasksFilter: SubtasksCompletionFilter? = null,
  val relativesFilter: RelativesFilter? = null,
)

data class ExtendedFiltersSignals(
  val onSubtasksFilterAllTypeClicked: () -> Unit,
  val onSubtasksFilterSomeTypeClicked: () -> Unit,

  val onSubtasksFilterCompletedFieldClicked: () -> Unit,
  val onSubtasksFilterNotCompletedFieldClicked: () -> Unit,
)