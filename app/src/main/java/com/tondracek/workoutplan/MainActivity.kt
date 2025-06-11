package com.tondracek.workoutplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tondracek.workoutplan.ui.navigation.AppNavigator
import com.tondracek.workoutplan.ui.screen.edittraining.editTrainingDayDestination
import com.tondracek.workoutplan.ui.screen.trainingmenu.TrainingMenuRoute
import com.tondracek.workoutplan.ui.screen.trainingmenu.trainingMenuDestination
import com.tondracek.workoutplan.ui.screen.trainingsession.trainingSessionDestination
import com.tondracek.workoutplan.ui.theme.AppTheme
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
                        editTrainingDayDestination()
                    }
                }
            }
        }
    }
}