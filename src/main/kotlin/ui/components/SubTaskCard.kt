package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Subtask

@Composable
fun SubTaskCard(
    subtask: Subtask,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(4.dp),
        elevation = 2.dp,
        backgroundColor = if (subtask.isCompleted) Color(0xFFE8F5E8) else Color.White
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = subtask.isCompleted,
                onCheckedChange = { onClick() },
                modifier = Modifier.size(48.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF27AE60),
                    uncheckedColor = Color(0xFF95A5A6),
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = subtask.title,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif,
                color = if (subtask.isCompleted) Color.Gray else Color.Black,
                textDecoration = if (subtask.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}