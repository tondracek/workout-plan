package com.example.workoutplan.domain.model

import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingDayId

data class TrainingDay(
    val name: String,
    val exercises: List<TrainingExercise>,
    val id: TrainingDayId = 0,
) {
    fun toEntity(): TrainingDayEntity = TrainingDayEntity(
        id = id,
        name = name,
    )
}
