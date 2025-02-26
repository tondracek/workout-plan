package com.example.workoutplan.ui.screen.edittraining

sealed interface EditTrainingDayUiState {

    data class Success(
        val name: String,
        val exercises: List<EditTrainingExerciseUiState>,
    ) : EditTrainingDayUiState

    data object Loading : EditTrainingDayUiState
}

data class EditTrainingExerciseUiState(
    val name: String = "",
    val sets: List<EditTrainingSetUiState> = emptyList(),
)

data class EditTrainingSetUiState(
    val reps: String = "",
    val weight: String = "",
)