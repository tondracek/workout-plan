package com.tondracek.workoutplan.domain.model

import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.db.entity.TrainingExerciseEntity
import com.tondracek.workoutplan.db.entity.TrainingExerciseId

data class TrainingExercise(
    val name: String,
    val sets: List<TrainingSet>,
    val id: TrainingExerciseId = 0,
) {
    fun toEntity(orderIndex: Int, trainingDayId: TrainingDayId) = TrainingExerciseEntity(
        id = id,
        orderIndex = orderIndex,
        trainingDayId = trainingDayId,
        name = name,
    )
}