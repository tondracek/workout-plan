package com.tondracek.workoutplan.ui.screen.trainingsession

import com.tondracek.workoutplan.domain.model.TrainingExercise

sealed interface TrainingSessionUiState {

    data class Success(
        val trainingName: String,
        val exercises: List<Pair<TrainingExercise, Boolean>>,
        val isDone: Boolean,
    ) : TrainingSessionUiState

    data object Loading : TrainingSessionUiState

    data object Error : TrainingSessionUiState
}
