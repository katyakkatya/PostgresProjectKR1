package ui.screens.task_list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Order
import models.OrderOptionsModel

@Composable
fun OrderOptionsContent(
  payload: OrderOptionsPayload,
) {
  Column(
    modifier = Modifier.padding(8.dp)
  ) {
    Text(
      text = "Настройки порядка",
      fontSize = 32.sp,
      fontWeight = FontWeight.W500,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
      textAlign = TextAlign.Left,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.h5,
      lineHeight = 32.sp
    )

    Divider(
      modifier = Modifier.padding(bottom = 16.dp),
      color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )

    RadioButton(
      text = "Неважно",
      onClick = { payload.onOrderSelected(Order.UNSET) },
      selected = payload.orderOptionsModel.order == Order.UNSET
    )

    Spacer(modifier = Modifier.height(16.dp))

    RadioButton(
      text = "По возрастанию",
      onClick = { payload.onOrderSelected(Order.ASC) },
      selected = payload.orderOptionsModel.order == Order.ASC
    )

    Spacer(modifier = Modifier.height(16.dp))

    RadioButton(
      text = "По убыванию",
      onClick = { payload.onOrderSelected(Order.DESC) },
      selected = payload.orderOptionsModel.order == Order.DESC
    )
  }
}

class OrderOptionsPayload(
  val orderOptionsModel: OrderOptionsModel,
  val onOrderSelected: (Order) -> Unit
)