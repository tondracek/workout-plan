package com.example.workoutplan.ui.screen.trainingmenu

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object TrainingMenuRoute

fun NavGraphBuilder.trainingMenuDestination() {
    composable<TrainingMenuRoute> {
        val viewModel: TrainingMenuViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        TrainingMenuScreen(
            uiState = uiState,
            onTrainingDaySelected = viewModel::onTrainingDaySelected
        )
    }
}