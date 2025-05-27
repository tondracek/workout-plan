package com.tondracek.workoutplan.ui.screen.edittraining

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.ui.navigation.AppNavigator
import kotlinx.serialization.Serializable

@Serializable
data class EditTrainingDayRoute(val id: TrainingDayId)

fun AppNavigator.navigateToEditTrainingDay(trainingDayId: TrainingDayId) {
    navigate(EditTrainingDayRoute(id = trainingDayId))
}

fun SavedStateHandle.getTrainingDayEditId(): TrainingDayId = toRoute<EditTrainingDayRoute>().id

fun NavGraphBuilder.editTrainingDayDestination() {
    composable<EditTrainingDayRoute> {
        val viewModel: EditTrainingDayViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        EditTrainingDayScreen(
            uiState = uiState,
            onNameChanged = viewModel::onNameChanged,
            onAddExerciseClicked = viewModel::onAddExerciseClicked,
            onRemoveExerciseClicked = viewModel::onRemoveExerciseClicked,
            onExerciseNameChanged = viewModel::onExerciseNameChanged,
            onAddSetClicked = viewModel::onAddSetClicked,
            onRemoveSetClicked = viewModel::onRemoveSetClicked,
            onSetUpdated = viewModel::onSetUpdated,
            onMoveSoonerInPlanClicked = viewModel::onMoveSoonerInPlanClicked,
            onMoveLaterInPlanClicked = viewModel::onMoveLaterInPlanClicked,
            onDeleteClicked = viewModel::onDeleteClicked,
            onSaveClicked = viewModel::onSave,
            onNavigateBack = viewModel::navigateBack,
        )
    }
}