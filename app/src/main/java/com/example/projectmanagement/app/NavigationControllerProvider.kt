package com.example.projectmanagement.app

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationControllerProvider @Inject constructor() {
    lateinit var navController: NavHostController
}