package com.example.workoutplan.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.model.TrainingSet

typealias TrainingExerciseId = Int

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
    val trainingDayId: TrainingDayId,
    val name: String,
) {
    fun toModel(trainingSets: List<TrainingSet>) = TrainingExercise(
        id = id,
        name = name,
        sets = trainingSets
    )
}