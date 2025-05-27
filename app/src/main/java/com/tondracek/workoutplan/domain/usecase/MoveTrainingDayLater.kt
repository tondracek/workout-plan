package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.db.entity.TrainingDayId
import javax.inject.Inject

class MoveTrainingDayLater @Inject constructor(
    private val trainingRepository: TrainingRepository
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(trainingDayId: TrainingDayId) =
        trainingRepository.moveTrainingDayLater(trainingDayId)
}