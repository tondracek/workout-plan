package com.example.workoutplan.ui.screen.edittraining

import com.example.workoutplan.db.entity.TrainingExerciseId
import com.example.workoutplan.db.entity.TrainingSetId

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
    val id: TrainingExerciseId = 0
)

data class EditTrainingSetUiState(
    val reps: String = "",
    val weight: String = "",
    val id: TrainingSetId = 0
)