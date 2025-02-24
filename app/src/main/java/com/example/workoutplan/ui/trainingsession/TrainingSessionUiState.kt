package com.example.workoutplan.ui.trainingsession

import com.example.workoutplan.domain.model.TrainingExercise

sealed interface TrainingSessionUiState {

    data class Success(
        val trainingName: String,
        val exercises: List<Pair<TrainingExercise, Boolean>>,
    ) : TrainingSessionUiState

    data object Loading : TrainingSessionUiState

    data object Error : TrainingSessionUiState
}
