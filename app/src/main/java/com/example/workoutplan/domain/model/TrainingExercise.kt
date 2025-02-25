package com.example.workoutplan.domain.model

import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingExerciseEntity
import com.example.workoutplan.db.entity.TrainingExerciseId

data class TrainingExercise(
    val id: TrainingExerciseId,
    val name: String,
    val sets: List<TrainingSet>,
) {
    fun toEntity(trainingDayId: TrainingDayId) = TrainingExerciseEntity(
        id = id,
        trainingDayId = trainingDayId,
        name = name,
    )
}