package com.tondracek.workoutplan.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

typealias TrainingExerciseId = Long

@Entity(
    tableName = "training_exercises",
    foreignKeys = [ForeignKey(
        entity = TrainingDayEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("trainingDayId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TrainingExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: TrainingExerciseId,
    val orderIndex: Int = 0,
    val trainingDayId: TrainingDayId,
    val name: String,
)
