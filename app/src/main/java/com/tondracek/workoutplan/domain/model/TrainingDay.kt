package com.tondracek.workoutplan.domain.model

import com.tondracek.workoutplan.db.entity.TrainingDayEntity
import com.tondracek.workoutplan.db.entity.TrainingDayId

data class TrainingDay(
    val name: String,
    val finishedCount: Int,
    val exercises: List<TrainingExercise>,
    val id: TrainingDayId = 0,
) {
    fun toEntity(orderIndex: Int): TrainingDayEntity = TrainingDayEntity(
        id = id,
        orderIndex = orderIndex,
        name = name,
        finishedCount = finishedCount,
    )
}
