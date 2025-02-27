package com.example.workoutplan.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise

typealias TrainingDayId = Long

@Entity(tableName = "training_days")
data class TrainingDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: TrainingDayId,
    val name: String,
) {
    fun toModel(trainingExercises: List<TrainingExercise>) = TrainingDay(
        id = id,
        name = name,
        exercises = trainingExercises,
    )
}
