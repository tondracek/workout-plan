package com.example.workoutplan.ui.trainingmenu

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val trainingMenuRoute = "trainingMenuDestination"

fun NavGraphBuilder.trainingMenuDestination() {
    composable(
        route = trainingMenuRoute
    ) {
        val viewModel: TrainingMenuViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()

        TrainingMenuScreen(
            uiState = uiState,
            onTrainingDaySelected = viewModel::onTrainingDaySelected
        )
    }
}