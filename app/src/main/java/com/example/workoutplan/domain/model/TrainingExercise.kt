package com.example.workoutplan.domain.model

import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingExerciseEntity
import com.example.workoutplan.db.entity.TrainingExerciseId

data class TrainingExercise(
    val name: String,
    val sets: List<TrainingSet>,
    val id: TrainingExerciseId = 0,
) {
    fun toEntity(trainingDayId: TrainingDayId) = TrainingExerciseEntity(
        id = id,
        trainingDayId = trainingDayId,
        name = name,
    )
}