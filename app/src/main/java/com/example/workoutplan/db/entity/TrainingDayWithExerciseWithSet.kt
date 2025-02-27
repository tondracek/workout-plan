package com.example.workoutplan.db.entity

data class TrainingDayWithExerciseWithSet(
    val trainingDayId: TrainingDayId,
    val trainingDayName: String,

    val trainingExerciseId: TrainingExerciseId?,
    val trainingExerciseName: String?,

    val trainingSetId: TrainingSetId?,
    val weight: Float?,
    val weightUnit: String?,
    val reps: Int?,
)