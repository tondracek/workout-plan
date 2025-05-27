package com.example.workoutplan.ui.screen.trainingsession

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.ui.navigation.AppNavigator
import kotlinx.serialization.Serializable

@Serializable
data class TrainingSessionRoute(val id: TrainingDayId)

fun AppNavigator.navigateToTrainingSession(trainingDayId: TrainingDayId) {
    navigate(TrainingSessionRoute(id = trainingDayId))
}

fun SavedStateHandle.getTrainingDaySessionId(): TrainingDayId = toRoute<TrainingSessionRoute>().id

fun NavGraphBuilder.trainingSessionDestination() {
    composable<TrainingSessionRoute> {
        val viewModel: TrainingSessionViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TrainingSessionScreen(
            uiState = uiState,
            navigateBack = viewModel::navigateBack,
            onEditTrainingClicked = viewModel::onEditTrainingClicked,
            onFinishExerciseClicked = viewModel::onFinishExerciseClicked,
            onFinishTrainingClicked = viewModel::onFinishTrainingClicked
        )
    }
}