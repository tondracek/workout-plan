package com.example.workoutplan.domain.model

import com.example.workoutplan.db.entity.TrainingExerciseId
import com.example.workoutplan.db.entity.TrainingSetEntity
import com.example.workoutplan.db.entity.TrainingSetId

data class TrainingSet(
    val reps: Int,
    val weight: Weight,
    val id: TrainingSetId = 0,
) {
    fun toEntity(orderIndex: Int, trainingExerciseId: TrainingExerciseId) = TrainingSetEntity(
        id = id,
        orderIndex = orderIndex,
        trainingExerciseId = trainingExerciseId,
        weight = weight.value,
        weightUnit = weight.unit.name,
        reps = reps,
    )
}