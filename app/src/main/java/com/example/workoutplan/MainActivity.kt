package com.example.workoutplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.workoutplan.ui.navigation.AppNavigator
import com.example.workoutplan.ui.theme.AppTheme
import com.example.workoutplan.ui.trainingmenu.trainingMenuDestination
import com.example.workoutplan.ui.trainingmenu.trainingMenuRoute
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                navigator.navController = navController

                NavHost(
                    navController = navController,
                    startDestination = trainingMenuRoute,
                ) {
                    trainingMenuDestination()
                }
            }
        }
    }
}