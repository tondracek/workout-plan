package com.example.workoutplan.domain.model

data class TrainingExercise(
    val name: String,
    val sets: List<TrainingSet>,
)