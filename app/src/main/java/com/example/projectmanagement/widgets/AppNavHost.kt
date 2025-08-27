package com.example.projectmanagement.widgets

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projectmanagement.app.NavigationControllerProvider
import com.example.projectmanagement.constants.ChangePasswordScreen
import com.example.projectmanagement.constants.BugSubmissionScreen
import com.example.projectmanagement.constants.IntroScreen
import com.example.projectmanagement.constants.HomeScreen
import com.example.projectmanagement.constants.SplashScreen
import com.example.projectmanagement.screens.bug.add.AddBugScreenUI
import com.example.projectmanagement.screens.home.HomeScreenUI
import com.example.projectmanagement.screens.intro.IntroScreenUI
import com.example.projectmanagement.screens.intro.SplashScreenUI

@Composable
fun AppNavHost(navController: NavigationControllerProvider) {

    NavHost(navController = navController.navController, startDestination = SplashScreen){
        composable<SplashScreen> {
            SplashScreenUI(navController.navController)
        }
        composable<IntroScreen> {
            IntroScreenUI(navController.navController)
        }
        composable<HomeScreen> {
//            LoginScreenUI(navController.navController)
            HomeScreenUI(navController.navController)
        }
        composable<BugSubmissionScreen> {
            AddBugScreenUI(navController.navController)
        }
    }
}