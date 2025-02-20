package com.example.workoutplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.workoutplan.ui.navigation.AppNavigator
import com.example.workoutplan.ui.theme.AppTheme
import com.example.workoutplan.ui.trainingmenu.TrainingMenuRoute
import com.example.workoutplan.ui.trainingmenu.trainingMenuDestination
import com.example.workoutplan.ui.trainingsession.trainingSessionDestination
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

                Surface(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = TrainingMenuRoute,
                    ) {
                        trainingMenuDestination()
                        trainingSessionDestination()
                    }
                }
            }
        }
    }
}