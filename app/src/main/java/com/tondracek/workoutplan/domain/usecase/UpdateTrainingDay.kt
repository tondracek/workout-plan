package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.domain.model.TrainingDay
import javax.inject.Inject

class UpdateTrainingDay @Inject constructor(
    private val trainingRepository: TrainingRepository
) : suspend (TrainingDay) -> Unit {

    override suspend fun invoke(trainingDay: TrainingDay) {
        trainingRepository.updateTrainingDay(trainingDay)
    }
}