package com.example.workoutplan.data.entity

typealias TrainingExerciseId = Int

data class TrainingExerciseEntity(
    val id: TrainingExerciseId,
    val trainingDayId: TrainingDayId,
    val name: String,
)