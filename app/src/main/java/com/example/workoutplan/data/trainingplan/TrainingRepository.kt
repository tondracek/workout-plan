package com.example.workoutplan.data.trainingplan

import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    suspend fun addEmptyTrainingDay(name: String)

    suspend fun updateTrainingDay(trainingDay: TrainingDay)

    suspend fun deleteTrainingDay(trainingDay: TrainingDay)

    fun getTrainingDayList(): Flow<List<TrainingDay>>

    fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDay>
}