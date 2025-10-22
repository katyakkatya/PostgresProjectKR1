package ui.screens.task_list.components

import FilterStatusItem
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.FormattingOptionsModel
import models.HeightTransformation

@Composable
fun FormattionOptionsContent(
  payload: FormattingOptionsPayload,
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Text(
      text = "Настройки форматирования",
      fontSize = 28.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Center,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h5
    )

    Divider(
      modifier = Modifier.padding(bottom = 8.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    RadioButton(
      text = "В верхнем регистре",
      onClick = { payload.onHeightTransformationClicked(HeightTransformation.UPPERCASE) },
      selected = payload.formattingOptionsModel.heightTransformation == HeightTransformation.UPPERCASE
    )

    RadioButton(
      text = "В нижнем регистре",
      onClick = { payload.onHeightTransformationClicked(HeightTransformation.LOWERCASE) },
      selected = payload.formattingOptionsModel.heightTransformation == HeightTransformation.LOWERCASE
    )

    Divider(
      modifier = Modifier.padding(bottom = 8.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    FilterStatusItem(
      enabled = payload.formattingOptionsModel.showShort,
      text = "Сокращать название",
      onClick = { payload.onShowShortClicked() }
    )
    FilterStatusItem(
      enabled = payload.formattingOptionsModel.displayId,
      text = "Показывать id задачи",
      onClick = { payload.onDisplayIdClicked() }
    )
    FilterStatusItem(
      enabled = payload.formattingOptionsModel.displayFullStatus,
      text = "Показывать полный статус задачи",
      onClick = { payload.onDisplayFullStatusClicked() }
    )
  }
}

@Composable
fun RadioButton(
  text: String,
  onClick: () -> Unit,
  selected: Boolean,
) {
  Row(
    Modifier
      .fillMaxWidth()
      .selectable(
        selected = selected,
        onClick = onClick,
        role = Role.RadioButton
      )
      .padding(horizontal = 16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    RadioButton(
      selected = selected,
      onClick = null
    )
    Text(
      text = text,
      modifier = Modifier.padding(start = 16.dp)
    )
  }
}

class FormattingOptionsPayload(
  val formattingOptionsModel: FormattingOptionsModel,
  val onHeightTransformationClicked: (HeightTransformation) -> Unit,
  val onShowShortClicked: () -> Unit,
  val onDisplayIdClicked: () -> Unit,
  val onDisplayFullStatusClicked: () -> Unit,
)