package gui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import models.Task

@Composable
fun AddSubTaskDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddNewSubtask: (String) -> Unit,
    onLinkExistingTask: (Task) -> Unit,
    allTasks: List<Task>
) {
    var dialogState by remember { mutableStateOf(AddSubTaskDialogState.SELECT_TYPE) }
    var taskTitle by remember { mutableStateOf("") }

    if (showDialog) {
        Dialog(onDismissRequest = {
            onDismiss()
            dialogState = AddSubTaskDialogState.SELECT_TYPE
            taskTitle = ""
        }) {
            Surface(
                modifier = Modifier
                    .width(600.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp
            ) {
                when (dialogState) {
                    AddSubTaskDialogState.SELECT_TYPE -> {
                        SelectTypeContent(
                            onAddNew = { dialogState = AddSubTaskDialogState.ADD_NEW },
                            onLinkExisting = { dialogState = AddSubTaskDialogState.LINK_EXISTING },
                            onDismiss = {
                                onDismiss()
                                dialogState = AddSubTaskDialogState.SELECT_TYPE
                            }
                        )
                    }
                    AddSubTaskDialogState.ADD_NEW -> {
                        AddNewSubtaskContent(
                            taskTitle = taskTitle,
                            onTaskTitleChange = { taskTitle = it },
                            onConfirm = {
                                if (taskTitle.isNotBlank()) {
                                    onAddNewSubtask(taskTitle)
                                    taskTitle = ""
                                    dialogState = AddSubTaskDialogState.SELECT_TYPE
                                }
                            },
                            onBack = { dialogState = AddSubTaskDialogState.SELECT_TYPE },
                            onDismiss = {
                                onDismiss()
                                dialogState = AddSubTaskDialogState.SELECT_TYPE
                                taskTitle = ""
                            }
                        )
                    }
                    AddSubTaskDialogState.LINK_EXISTING -> {
                        LinkExistingTaskContent(
                            allTasks = allTasks,
                            onTaskSelected = { task ->
                                onLinkExistingTask(task)
                                dialogState = AddSubTaskDialogState.SELECT_TYPE
                            },
                            onBack = { dialogState = AddSubTaskDialogState.SELECT_TYPE },
                            onDismiss = {
                                onDismiss()
                                dialogState = AddSubTaskDialogState.SELECT_TYPE
                            }
                        )
                    }
                }
            }
        }
    }
}

enum class AddSubTaskDialogState {
    SELECT_TYPE, ADD_NEW, LINK_EXISTING
}

@Composable
private fun SelectTypeContent(
    onAddNew: () -> Unit,
    onLinkExisting: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Добавить связь",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onAddNew,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(
                text = "Создать новую подзадачу",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Button(
            onClick = onLinkExisting,
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)

        ) {
            Text(
                text = "Связать с существующей задачей",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )
        }

        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(
                text = "Отмена",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AddNewSubtaskContent(
    taskTitle: String,
    onTaskTitleChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onBack: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Новая подзадача",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Введите название подзадачи:",
            fontSize = 28.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        TextField(
            value = taskTitle,
            onValueChange = onTaskTitleChange,
            placeholder = { Text("Например: Помыть посуду", fontSize = 24.sp) },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
                .height(60.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                focusedIndicatorColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.Gray
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(60.dp)
            ) {
                Text("Назад", fontSize = 24.sp)
            }

            Button(
                onClick = onConfirm,
                enabled = taskTitle.isNotBlank(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2E7D32)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
                    .height(60.dp)
            ) {
                Text("Создать", fontSize = 24.sp)
            }
        }

        Button(
            onClick = onDismiss,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text("Отмена", fontSize = 24.sp)
        }
    }
}

@Composable
private fun LinkExistingTaskContent(
    allTasks: List<Task>,
    onTaskSelected: (Task) -> Unit,
    onBack: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = "Выберите задачу для связи",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp)
                .padding(bottom = 16.dp)
        ) {
            items(allTasks.size) { index -> // ← Исправлено на items(count)
                val task = allTasks[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onTaskSelected(task) },
                    elevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = task.title,
                            modifier = Modifier.weight(1f),
                            fontSize = 24.sp
                        )
                        Text(
                            text = "${(task.progress * 100).toInt()}%",
                            fontSize = 24.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("Назад", fontSize = 24.sp)
            }

            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text("Отмена", fontSize = 24.sp)
            }
        }
    }
}