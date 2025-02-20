package com.example.workoutplan.domain.model

import com.example.workoutplan.data.entity.TrainingDayId

data class TrainingDay(
    val id: TrainingDayId,
    val name: String,
    val exercises: List<TrainingExercise>,
)
