package ui.screens.connection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ConnectionScreen(
  viewModel: ConnectionViewModel,
  onConnectionSuccess: () -> Unit,
) {
  val url by viewModel.urlFlow.collectAsState()
  val username by viewModel.usernameFlow.collectAsState()
  val password by viewModel.passwordFlow.collectAsState()
  LaunchedEffect(Unit) {
    delay(500)
    onConnectionSuccess()
  }
  /**
   * TODO: Когда идет подключение, нужно скрывать кнопкку и вместо нее писать текст загрузки
   * Если подключился успешно, то показывать модалку с текстом "Подключение успешно" и кнопкой ок
   */
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color.Gray)
      .padding(80.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(color = Color.LightGray),
    verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Подключение к базе данных",
      textAlign = TextAlign.Start,
      fontFamily = FontFamily.SansSerif,
      fontWeight = FontWeight.W400,
      fontSize = 36.sp,
      modifier = Modifier.padding(top = 40.dp)
    )
    ConnectionTextField(
      text = url,
      onValueChange = { viewModel.onUrlChanged(it) },
      label = "Ссылка на базу данных",
      placeholder = "Введите ссылку"
    )
    ConnectionTextField(
      text = username,
      onValueChange = { viewModel.onUsernameChanged(it)},
      label = "Имя пользователя",
      placeholder = "Введите имя пользователя"
    )
    ConnectionTextField(
      text = password,
      onValueChange = { viewModel.onPasswordChanged(it) },
      label = "Пароль от базы данных",
      placeholder = "Введите пароль",
      isPassword = true
    )

    Button(
      onClick = {viewModel.tryConnect()},
      modifier = Modifier.padding(top = 64.dp),
      shape = RoundedCornerShape(16.dp),
      colors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Gray,
        contentColor = Color.White
      )
    ) {
      Text(
        text = "Подключиться",
        fontFamily = FontFamily.SansSerif,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        modifier = Modifier.padding(vertical = 18.dp, horizontal = 64.dp)
      )
    }
  }
}

@Composable
fun ConnectionTextField(
  text: String,
  onValueChange: (String) -> Unit,
  label: String,
  placeholder: String,
  isPassword: Boolean = false
) {
  TextField(
    value = text,
    onValueChange = onValueChange,
    placeholder = {
      Text(
        text = placeholder,
        textAlign = TextAlign.Start,
        color = Color.Gray,
        fontFamily = FontFamily.SansSerif,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400
      )
    },
    label = {
      Text(
        text = label,
        textAlign = TextAlign.Start,
        color = Color.DarkGray,
        fontFamily = FontFamily.SansSerif,
        fontSize = 24.sp,
      )
    },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 80.dp)
      .height(90.dp)
      .wrapContentHeight(Alignment.CenterVertically),
    shape = RoundedCornerShape(16.dp),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedBorderColor = Color.DarkGray,
      unfocusedBorderColor = Color.Gray,
      cursorColor = Color.DarkGray
    ),
    textStyle = TextStyle(
      fontSize = 28.sp,
      fontFamily = FontFamily.SansSerif,
      fontWeight = FontWeight.W400,
      color = Color.DarkGray,
      textAlign = TextAlign.Start
    ),
    singleLine = true,
    visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
  )
}