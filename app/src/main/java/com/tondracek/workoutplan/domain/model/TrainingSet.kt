package com.tondracek.workoutplan.domain.model

import com.tondracek.workoutplan.db.entity.TrainingExerciseId
import com.tondracek.workoutplan.db.entity.TrainingSetEntity
import com.tondracek.workoutplan.db.entity.TrainingSetId

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