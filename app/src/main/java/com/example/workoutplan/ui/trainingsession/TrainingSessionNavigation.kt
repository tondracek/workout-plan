package com.example.workoutplan.ui.trainingsession

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.ui.navigation.AppNavigator
import kotlinx.serialization.Serializable

@Serializable
data class TrainingSessionRoute(val id: TrainingDayId)

fun AppNavigator.navigateToTrainingSession(trainingDayId: TrainingDayId) {
    navigate(TrainingSessionRoute(id = trainingDayId))
}

fun NavGraphBuilder.trainingSessionDestination() {
    composable<TrainingSessionRoute> {
        val route = it.toRoute<TrainingSessionRoute>()

        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                Text("Placeholder: ${route.id}")
            }
        }
    }
}