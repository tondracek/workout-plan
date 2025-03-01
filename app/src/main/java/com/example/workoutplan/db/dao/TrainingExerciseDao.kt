package com.example.workoutplan.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingExercise(trainingExercise: TrainingExerciseEntity): Long

    @Delete
    suspend fun deleteTrainingExercise(trainingExercise: TrainingExerciseEntity)

    @Query("SELECT * FROM training_exercises")
    fun getAllTrainingExercises(): Flow<List<TrainingExerciseEntity>>

    @Query("SELECT * FROM training_exercises WHERE trainingDayId = :id")
    fun getTrainingExercisesByTrainingDayId(id: TrainingDayId): Flow<List<TrainingExerciseEntity>>

    @Query("DELETE FROM training_exercises WHERE trainingDayId = :trainingDayId")
    suspend fun deleteTrainingDayExercises(trainingDayId: TrainingDayId)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingExercises(exerciseEntities: List<TrainingExerciseEntity>)
}