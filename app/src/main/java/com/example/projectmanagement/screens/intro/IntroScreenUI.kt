package com.example.projectmanagement.screens.intro

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.projectmanagement.widgets.SliderView

@Composable
fun IntroScreenUI(navController: NavHostController) {
    SliderView(modifier = Modifier,navController)
}