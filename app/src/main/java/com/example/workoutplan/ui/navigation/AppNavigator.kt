package com.example.workoutplan.ui.navigation

import androidx.navigation.NavController
import javax.inject.Inject

typealias Route = String

class AppNavigator @Inject constructor() {

    lateinit var navController: NavController

    fun navigateTo(route: Route) {
        navController.navigate(route)
    }

    fun navigateBack() {
        navController.navigateUp()
    }
}