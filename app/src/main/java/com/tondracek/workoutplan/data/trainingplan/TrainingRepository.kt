package com.tondracek.workoutplan.data.trainingplan

import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.domain.model.TrainingDay
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {

    suspend fun addEmptyTrainingDay(name: String)

    suspend fun updateTrainingDay(trainingDay: TrainingDay)

    suspend fun deleteTrainingDay(trainingDayId: TrainingDayId)

    fun getTrainingDayList(): Flow<List<TrainingDay>>

    fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDay?>

    fun getTrainingDaysCount(): Flow<Int>

    suspend fun moveTrainingDaySooner(trainingDayId: TrainingDayId)

    suspend fun moveTrainingDayLater(trainingDayId: TrainingDayId)

    suspend fun getFollowingTrainingDayId(trainingDayId: TrainingDayId): TrainingDayId?

    suspend fun increaseFinishedCount(trainingDayId: TrainingDayId)
}