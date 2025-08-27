package com.example.projectmanagement.widgets

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.projectmanagement.app.NavigationControllerProvider
import com.example.projectmanagement.constants.ChangePasswordScreen
import com.example.projectmanagement.constants.BugSubmissionScreen
import com.example.projectmanagement.constants.IntroScreen
import com.example.projectmanagement.constants.HomeScreen
import com.example.projectmanagement.constants.SplashScreen
import com.example.projectmanagement.screens.bug.add.AddBugScreenUI
import com.example.projectmanagement.screens.bug.viewModel.SharedImageViewModel
import com.example.projectmanagement.screens.home.HomeScreenUI
import com.example.projectmanagement.screens.intro.IntroScreenUI
import com.example.projectmanagement.screens.intro.SplashScreenUI

@Composable
fun AppNavHost(navController: NavigationControllerProvider,
               sharedImageViewModel: SharedImageViewModel
) {
    val sharedImages by sharedImageViewModel.sharedImages.collectAsState()

    NavHost(navController = navController.navController, startDestination = if (sharedImages.isNotEmpty()) BugSubmissionScreen else SplashScreen){
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
        composable<BugSubmissionScreen> { backStackEntry ->
            // Get images from arguments or use initialImages
            AddBugScreenUI(
                navController.navController,
                sharedImages
            )
        }
    }
}