package com.example.workoutplan.ui.trainingsession

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.ui.navigation.AppNavigator
import kotlinx.serialization.Serializable

@Serializable
data class TrainingSessionRoute(val id: TrainingDayId)

fun AppNavigator.navigateToTrainingSession(trainingDayId: TrainingDayId) {
    println("-- Navigating to Training Session with $trainingDayId")
    navigate(TrainingSessionRoute(id = trainingDayId))
}

fun NavGraphBuilder.trainingSessionDestination() {
    composable<TrainingSessionRoute> {
        val viewModel: TrainingSessionViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingSessionScreen(
            uiState = uiState,
            onFinishExerciseClicked = viewModel::onFinishExerciseClicked,
            onFinishTrainingClicked = viewModel::onFinishTrainingClicked
        )
    }
}