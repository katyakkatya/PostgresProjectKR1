import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LogsHeader(
  checked: Boolean,
  onCheckedChange: () -> Unit
){
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 32.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ){
    Text(
      text = "Показывать только ошибки: ",
      fontFamily = FontFamily.SansSerif,
      fontSize = 28.sp,
      fontWeight = FontWeight.W400
    )
    Checkbox(
      checked = checked,
      onCheckedChange = { onCheckedChange() }
    )
  }
}