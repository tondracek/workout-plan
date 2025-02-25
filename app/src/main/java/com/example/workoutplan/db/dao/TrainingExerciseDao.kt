package com.example.workoutplan.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingExerciseDao {

    @Upsert
    suspend fun upsertTrainingExercise(trainingExercise: TrainingExerciseEntity)

    @Delete
    suspend fun deleteTrainingExercise(trainingExercise: TrainingExerciseEntity)

    @Query("SELECT * FROM training_exercises")
    fun getAllTrainingExercises(): Flow<List<TrainingExerciseEntity>>

    @Query("SELECT * FROM training_exercises WHERE trainingDayId = :id")
    fun getTrainingExercisesByTrainingDayId(id: TrainingDayId): Flow<List<TrainingExerciseEntity>>
}