package com.example.workoutplan.domain.model

import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingDayId

data class TrainingDay(
    val id: TrainingDayId = 0,
    val name: String,
    val exercises: List<TrainingExercise>,
) {
    fun toEntity(): TrainingDayEntity = TrainingDayEntity(
        id = id,
        name = name,
    )
}
