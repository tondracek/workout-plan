package com.example.workoutplan.domain.model

import com.example.workoutplan.db.entity.TrainingSetEntity
import com.example.workoutplan.db.entity.TrainingSetId

data class TrainingSet(
    val id: TrainingSetId,
    val reps: Int,
    val weight: Weight,
) {
    fun toEntity(trainingExerciseId: Int) = TrainingSetEntity(
        id = id,
        trainingExerciseId = trainingExerciseId,
        weight = weight.value,
        weightUnit = weight.unit.name,
        reps = reps,
    )
}