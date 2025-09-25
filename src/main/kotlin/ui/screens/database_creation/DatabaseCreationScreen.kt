package ui.screens.database_creation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DatabaseCreationScreen(
  viewModel: DatabaseCreationViewModel,
  onDatabaseCreated: () -> Unit,
) {

  val state by viewModel.stateFlow.collectAsState()
  when (state) {
    DatabaseCreationViewModel.State.Loading -> {
      Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ){
        CircularProgressIndicator(
          color = Color.Gray,
          strokeWidth = 6.dp,
          modifier = Modifier.size(64.dp)
        )
      }
    }

    is DatabaseCreationViewModel.State.Loaded -> {
      val loadedState = state as DatabaseCreationViewModel.State.Loaded
      if (loadedState.isDatabaseCreated){
        onDatabaseCreated()
      }
      else{
        Column (
          modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
            .padding(60.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.LightGray),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
        ){
          Text(
            text = "База данных не создана",
            fontSize = 36.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center
          )
          Button(
            onClick = { },
            modifier = Modifier.padding(top = 64.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              backgroundColor = Color.Gray,
              contentColor = Color.White
            )
          ) {
            Text(
              text = "Создать базу данных",
              fontFamily = FontFamily.SansSerif,
              fontSize = 24.sp,
              fontWeight = FontWeight.W400,
              modifier = Modifier.padding(vertical = 18.dp, horizontal = 64.dp)
            )
          }
        }
      }
    }

    DatabaseCreationViewModel.State.Created -> {
      Column (
        modifier = Modifier
          .fillMaxSize()
          .background(color = Color.Gray)
          .padding(60.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(color = Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ){
        Text(
          text = "База данных создана",
          fontSize = 36.sp,
          fontFamily = FontFamily.SansSerif,
          fontWeight = FontWeight.W400,
          textAlign = TextAlign.Center
        )
        Button(
          onClick = {},
          modifier = Modifier.padding(top = 64.dp),
          shape = RoundedCornerShape(16.dp),
          colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Gray,
            contentColor = Color.White
          )
        ) {
          Text(
            text = "Продолжить",
            fontFamily = FontFamily.SansSerif,
            fontSize = 24.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.padding(vertical = 18.dp, horizontal = 64.dp)
          )
        }
      }
    }
  }
}