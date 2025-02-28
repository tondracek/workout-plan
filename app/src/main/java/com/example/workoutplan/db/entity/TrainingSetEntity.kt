package com.example.workoutplan.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

typealias TrainingSetId = Long

@Entity(
    tableName = "training_sets",
    foreignKeys = [ForeignKey(
        entity = TrainingExerciseEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("trainingExerciseId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class TrainingSetEntity(
    @PrimaryKey(autoGenerate = true)
    var id: TrainingSetId,
    val orderIndex: Int = 0,
    var trainingExerciseId: TrainingExerciseId,
    var weight: Float,
    var weightUnit: String,
    var reps: Int,
)
