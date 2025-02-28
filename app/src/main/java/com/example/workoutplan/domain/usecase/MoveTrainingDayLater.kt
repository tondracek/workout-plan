package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
import javax.inject.Inject

class MoveTrainingDayLater @Inject constructor(
    private val trainingRepository: TrainingRepository
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(trainingDayId: TrainingDayId) =
        trainingRepository.moveTrainingDayLater(trainingDayId)
}