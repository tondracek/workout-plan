package com.example.workoutplan.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingDayWithExerciseWithSet
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingDay(trainingDay: TrainingDayEntity): Long

    @Delete
    suspend fun deleteTrainingDay(trainingDay: TrainingDayEntity)

    @Query("""
        SELECT
            td.id AS trainingDayId,
            td.name AS trainingDayName,
            
            te.id AS trainingExerciseId,
            te.name AS trainingExerciseName,
            
            ts.id AS trainingSetId,
            ts.weight,
            ts.weightUnit,
            ts.reps
        FROM
            training_days td
            LEFT JOIN training_exercises te ON td.id = te.trainingDayId
            LEFT JOIN training_sets ts ON te.id = ts.trainingExerciseId
    """)
    fun getAllTrainingDay(): Flow<List<TrainingDayWithExerciseWithSet>>

    @Query(
        """
        SELECT 
            td.id AS trainingDayId,
            td.name AS trainingDayName,
            
            te.id AS trainingExerciseId,
            te.name AS trainingExerciseName,
            
            ts.id AS trainingSetId,
            ts.weight,
            ts.weightUnit,
            ts.reps
        FROM
            training_days td
            LEFT JOIN training_exercises te ON td.id = te.trainingDayId
            LEFT JOIN training_sets ts ON te.id = ts.trainingExerciseId
        WHERE td.id = :id
    """
    )
    fun getTrainingDayById(id: TrainingDayId): Flow<List<TrainingDayWithExerciseWithSet>>
}
