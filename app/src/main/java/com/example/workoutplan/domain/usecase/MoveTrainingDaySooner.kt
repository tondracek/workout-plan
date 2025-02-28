package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
import javax.inject.Inject

class MoveTrainingDaySooner @Inject constructor(
    private val trainingRepository: TrainingRepository
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(trainingDayId: TrainingDayId) =
        trainingRepository.moveTrainingDaySooner(trainingDayId)
}
