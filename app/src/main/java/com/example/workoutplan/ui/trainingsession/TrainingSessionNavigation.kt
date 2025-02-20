package com.example.workoutplan.ui.trainingsession

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.ui.navigation.AppNavigator

const val trainingSessionRoute = "trainingSessionDestination"

fun AppNavigator.navigateToTrainingSession(trainingDayId: TrainingDayId) {

    // TODO: apply parameters to the string route

    navigateTo(trainingSessionRoute)
}

fun NavGraphBuilder.trainingSessionDestination() {
    composable(
        route = trainingSessionRoute
    ) {

    }
}