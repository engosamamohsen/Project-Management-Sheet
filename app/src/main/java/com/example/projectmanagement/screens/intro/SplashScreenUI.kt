package com.example.projectmanagement.screens.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.projectmanagement.R
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.constants.IntroScreen
import com.example.projectmanagement.constants.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreenUI (navController: NavHostController) {
    var navigate by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        delay(3000)
        navigate = true
    }
    if(navigate){
        navController.navigate(IntroScreen){
            popUpTo(SplashScreen){
                inclusive = true
            }
        }
        navigate = false
    }
    Box(modifier = Modifier.fillMaxSize().background(color = AppColor.primaryColor)) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_white),
            modifier = Modifier.width(200.dp).height(400.dp).align(Alignment.Center),
            contentDescription = "trend"
        )
    }
}