package com.tondracek.workoutplan.ui.screen.trainingmenu

import com.tondracek.workoutplan.db.entity.TrainingDayId

sealed interface TrainingMenuUiState {

    data class Success(
        val trainings: List<TrainingDayUiState>,
        val currentTrainingDayIndex: Int,
    ) : TrainingMenuUiState

    data object Loading : TrainingMenuUiState
}

data class TrainingDayUiState(
    val id: TrainingDayId,
    val name: String,
    val finishedCount: Int,
    val totalExercises: Int,
    val totalSets: Int,
)