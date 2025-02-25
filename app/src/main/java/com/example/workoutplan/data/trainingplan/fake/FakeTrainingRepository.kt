package com.example.workoutplan.data.trainingplan.fake

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTrainingRepository : TrainingRepository {

    private val trainingPlan = emptyList<TrainingDay>()

    override suspend fun addEmptyTrainingDay(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTrainingDay(trainingDay: TrainingDay) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrainingDay(trainingDay: TrainingDay) {
        TODO("Not yet implemented")
    }

    override fun getTrainingDayList(): Flow<List<TrainingDay>> = flowOf(trainingPlan)

    override fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDay> =
        flowOf(trainingPlan.first { it.id == id })
}