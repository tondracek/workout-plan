package com.example.workoutplan.ui.trainingsession

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.workoutplan.data.common.entity.TrainingDayId
import com.example.workoutplan.ui.navigation.AppNavigator
import kotlinx.serialization.Serializable

@Serializable
data class TrainingSessionRoute(val id: TrainingDayId)

fun AppNavigator.navigateToTrainingSession(trainingDayId: TrainingDayId) {
    println("-- Navigating to Training Session with $trainingDayId")
    navigate(TrainingSessionRoute(id = trainingDayId))
}

fun SavedStateHandle.getTrainingDayId(): TrainingDayId = toRoute<TrainingSessionRoute>().id

fun NavGraphBuilder.trainingSessionDestination() {
    composable<TrainingSessionRoute> {
        val viewModel: TrainingSessionViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingSessionScreen(
            uiState = uiState,
            navigateBack = viewModel::navigateBack,
            onFinishExerciseClicked = viewModel::onFinishExerciseClicked,
            onFinishTrainingClicked = viewModel::onFinishTrainingClicked
        )
    }
}