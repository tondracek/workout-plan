package com.example.workoutplan.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDayDao {

    @Upsert
    suspend fun upsertTrainingDay(trainingDay: TrainingDayEntity)

    @Delete
    suspend fun deleteTrainingDay(trainingDay: TrainingDayEntity)

    @Query("SELECT * FROM training_days")
    fun getAllTrainingDay(): Flow<List<TrainingDayEntity>>

    @Query("SELECT * FROM training_days WHERE id = :id")
    fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDayEntity>
}
