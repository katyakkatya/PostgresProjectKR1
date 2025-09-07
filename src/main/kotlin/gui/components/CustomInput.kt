package gui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInput(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  label: String,
  singleLine: Boolean = true
) {
  Column(modifier) {
    Text(
      text = label,
      fontSize = 12.sp,
      color = MaterialTheme.colors.primary,
      modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
    )
    BasicTextField(
      value = value,
      onValueChange = onValueChange,
      singleLine = singleLine,
      textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
      cursorBrush = SolidColor(MaterialTheme.colors.primary),
      decorationBox = { innerTextField ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray, MaterialTheme.shapes.small)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        ) {
          innerTextField()
        }
      }
    )
  }
}