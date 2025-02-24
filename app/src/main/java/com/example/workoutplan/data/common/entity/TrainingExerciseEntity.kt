package com.example.workoutplan.data.common.entity

typealias TrainingExerciseId = Int

data class TrainingExerciseEntity(
    val id: TrainingExerciseId,
    val trainingDayId: TrainingDayId,
    val name: String,
)