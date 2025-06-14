package com.tondracek.workoutplan.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.tondracek.workoutplan.db.entity.TrainingDayEntity
import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.db.entity.TrainingDayWithExerciseWithSet
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDayDao {

    @Upsert
    suspend fun upsertTrainingDay(trainingDay: TrainingDayEntity)

    @Update
    suspend fun updateTrainingDay(trainingDay: TrainingDayEntity)

    @Delete
    suspend fun deleteTrainingDay(trainingDay: TrainingDayEntity)

    @Query("DELETE FROM training_days WHERE id = :id")
    suspend fun deleteTrainingDayById(id: TrainingDayId)

    @Query("SELECT * FROM training_days ORDER BY orderIndex ASC")
    fun getAllTrainingDayEntities(): Flow<List<TrainingDayEntity>>

    @Query(
        """
        SELECT
            td.id AS trainingDayId,
            td.name AS trainingDayName,
            td.finishedCount AS trainingDayFinishedCount,
            
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
        ORDER BY td.orderIndex, te.orderIndex, ts.orderIndex ASC
    """
    )
    fun getAllTrainingDay(): Flow<List<TrainingDayWithExerciseWithSet>>

    @Query(
        """
        SELECT 
            td.id AS trainingDayId,
            td.name AS trainingDayName,
            td.finishedCount as trainingDayFinishedCount,
            
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
        ORDER BY td.orderIndex, te.orderIndex, ts.orderIndex ASC
    """
    )
    fun getTrainingDayById(id: TrainingDayId): Flow<List<TrainingDayWithExerciseWithSet>>

    @Query("SELECT COUNT(*) FROM training_days")
    fun getTrainingDaysCount(): Flow<Int>

    @Query(
        """
        SELECT id
        FROM training_days
        WHERE orderIndex > (SELECT orderIndex FROM training_days WHERE id = :id)
        ORDER BY orderIndex ASC
        LIMIT 1
    """
    )
    suspend fun getFollowingTrainingDayId(id: TrainingDayId): TrainingDayId?

    @Query("SELECT COALESCE(MAX(orderIndex) + 1, 0) FROM training_days")
    suspend fun getNewOrderIndex(): Int

    @Query("SELECT orderIndex FROM training_days WHERE id = :id")
    suspend fun getTrainingDayOrderIndex(id: TrainingDayId): Int

    @Query("UPDATE training_days SET finishedCount = finishedCount + 1 WHERE id = :trainingDayId")
    suspend fun increaseFinishedCount(trainingDayId: TrainingDayId)
}
