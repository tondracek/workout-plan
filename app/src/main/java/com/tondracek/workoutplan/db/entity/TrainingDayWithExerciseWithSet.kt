package com.tondracek.workoutplan.db.entity

data class TrainingDayWithExerciseWithSet(
    val trainingDayId: TrainingDayId,
    val trainingDayName: String,
    val trainingDayFinishedCount: Int,

    val trainingExerciseId: TrainingExerciseId?,
    val trainingExerciseName: String?,

    val trainingSetId: TrainingSetId?,
    val weight: Float?,
    val weightUnit: String?,
    val reps: Int?,
)