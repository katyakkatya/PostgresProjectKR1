import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTaskDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var taskTitle by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .width(600.dp)
                    .height(500.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Новая задача",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )

                    Text(
                        text = "Введите название задачи:",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = FontFamily.Serif,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
                    )

                    TextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        placeholder = {
                            Text(
                                "Например: Сделать домашку",
                                fontSize = 24.sp,
                                fontFamily = FontFamily.Serif,
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp)
                            .height(70.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            textColor = Color.Black,
                            focusedIndicatorColor = Color.DarkGray,
                            unfocusedIndicatorColor = Color.Gray,
                            cursorColor = Color.DarkGray
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 24.sp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                onDismiss()
                                taskTitle = ""
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Red,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 12.dp)
                                .height(65.dp)
                        ) {
                            Text(
                                text = "Отмена",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                            )
                        }

                        Button(
                            onClick = {
                                if (taskTitle.isNotBlank()) {
                                    onConfirm(taskTitle)
                                    taskTitle = ""
                                }
                            },
                            enabled = taskTitle.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF2E7D32),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp)
                                .height(65.dp)
                        ) {
                            Text(
                                text = "Добавить",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Serif,
                            )
                        }
                    }
                }
            }
        }
    }
}