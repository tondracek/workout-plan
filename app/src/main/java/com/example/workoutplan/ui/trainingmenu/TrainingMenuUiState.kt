package com.example.workoutplan.ui.trainingmenu

import com.example.workoutplan.data.common.entity.TrainingDayId

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
    val totalExercises: Int,
    val totalSets: Int,
)