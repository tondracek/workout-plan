package com.example.workoutplan.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.workoutplan.domain.model.TrainingSet
import com.example.workoutplan.domain.model.Weight
import com.example.workoutplan.domain.model.WeightUnit

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
    var trainingExerciseId: TrainingExerciseId,
    var weight: Float,
    var weightUnit: String,
    var reps: Int,
) {
    fun toModel(): TrainingSet {
        return TrainingSet(
            id = id,
            reps = reps,
            weight = Weight(
                value = weight,
                unit = WeightUnit(weightUnit)
            ),
        )
    }
}