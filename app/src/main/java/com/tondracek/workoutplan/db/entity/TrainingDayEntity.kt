package com.tondracek.workoutplan.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias TrainingDayId = Long

@Entity(tableName = "training_days")
data class TrainingDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: TrainingDayId,
    val orderIndex: Int,
    val name: String,
    val finishedCount: Int,
)
