package com.tondracek.workoutplan.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tondracek.workoutplan.db.entity.TrainingExerciseId
import com.tondracek.workoutplan.db.entity.TrainingSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingSet(trainingSet: TrainingSetEntity): Long

    @Delete
    suspend fun deleteTrainingSet(trainingSet: TrainingSetEntity)

    @Query("SELECT * FROM training_sets")
    fun getAllTrainingSets(): Flow<List<TrainingSetEntity>>

    @Query("SELECT * FROM training_sets WHERE trainingExerciseId = :id")
    fun getTrainingSetsByExerciseId(id: TrainingExerciseId): Flow<List<TrainingSetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingSets(entities: List<TrainingSetEntity>)
}