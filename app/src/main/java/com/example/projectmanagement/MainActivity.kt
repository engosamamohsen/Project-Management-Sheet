package com.example.projectmanagement

import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.projectmanagement.common.helper.Constants
import com.example.projectmanagement.app.NavigationControllerProvider
import com.example.projectmanagement.common.language.MyContextWrapper
//import com.example.projectmanagement.common.language.LanguagesHelper
import com.example.projectmanagement.common.language.MySharedPreferences
import com.example.projectmanagement.constants.BugSubmissionScreen
import com.example.projectmanagement.constants.HomeScreen
import com.example.projectmanagement.constants.SplashScreen
import com.example.projectmanagement.firebase.NotificationPermission
import com.example.projectmanagement.screens.bug.viewModel.SharedImageViewModel
import com.example.projectmanagement.ui.theme.ProjectManagementTheme
import com.example.projectmanagement.widgets.AppNavHost
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationControllerProvider: NavigationControllerProvider


    override fun onCreate(savedInstanceState: Bundle?) {

//        LanguagesHelper.changeLanguage(this,LanguagesHelper.getCurrentLanguage(this))
        super.onCreate(savedInstanceState)
        val initialSharedImages = handleIntent(intent)

        enableEdgeToEdge()
        setContent {
//            mySharedPreferences.setLanguage("ar")

            navigationControllerProvider.navController = rememberNavController()
            val sharedImageViewModel = hiltViewModel<SharedImageViewModel>()
            LaunchedEffect(initialSharedImages) {
                if (initialSharedImages.isNotEmpty()) {
                    sharedImageViewModel.updateSharedImages(initialSharedImages)
                }
            }

            ProjectManagementTheme {
                AppNavHost(navigationControllerProvider, sharedImageViewModel) // Pass sharedImages here
            }
        }
        NotificationPermission(this).checkNotificationPermission()

    }

    override fun attachBaseContext(newBase: Context) {
        val appPreferences = MySharedPreferences(context = newBase)
        appPreferences.setLanguage(Constants.DEFAULT_LANGUAGE)
        val context: Context = MyContextWrapper.wrap(
            newBase,
            Locale.forLanguageTag(appPreferences.getCurrentLanguage(newBase))
        )
        super.attachBaseContext(context)
//        }
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        val newSharedImages = handleIntent(intent)
        if (newSharedImages.isNotEmpty()) {
            navigationControllerProvider.navController.navigate(BugSubmissionScreen) {
                popUpTo(HomeScreen){
                    inclusive = true
                }
            }
        }
    }

    private fun handleIntent(intent: Intent): List<Uri> {
        val sharedImages = mutableListOf<Uri>()

        when (intent.action) {
            Intent.ACTION_SEND -> {
                intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)?.let { uri ->
                    sharedImages.add(uri)
                }
            }
            Intent.ACTION_SEND_MULTIPLE -> {
                intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)?.let { uris ->
                    sharedImages.addAll(uris)
                }
            }
        }

        return sharedImages
    }

}
