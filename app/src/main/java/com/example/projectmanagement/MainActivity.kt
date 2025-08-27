package com.example.projectmanagement

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.projectmanagement.common.helper.Constants
import com.example.projectmanagement.app.NavigationControllerProvider
import com.example.projectmanagement.common.language.MyContextWrapper
//import com.example.projectmanagement.common.language.LanguagesHelper
import com.example.projectmanagement.common.language.MySharedPreferences
import com.example.projectmanagement.firebase.NotificationPermission
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
        enableEdgeToEdge()
        setContent {
//            mySharedPreferences.setLanguage("ar")

            navigationControllerProvider.navController = rememberNavController()
            ProjectManagementTheme {
//                val navController = rememberNavController()
                AppNavHost(navigationControllerProvider)
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    SliderView(modifier = Modifier.padding(innerPadding))
//                }
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

}
