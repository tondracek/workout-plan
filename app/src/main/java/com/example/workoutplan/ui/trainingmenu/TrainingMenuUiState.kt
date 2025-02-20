package com.example.workoutplan.ui.trainingmenu

import com.example.workoutplan.data.entity.TrainingDayId

sealed interface TrainingMenuUiState {

    data class Success(val trainings: List<TrainingDayUiState>) : TrainingMenuUiState

    data object Loading : TrainingMenuUiState
}

data class TrainingDayUiState(
    val id: TrainingDayId,
    val name: String,
    val totalExercises: Int,
    val totalSets: Int,
)