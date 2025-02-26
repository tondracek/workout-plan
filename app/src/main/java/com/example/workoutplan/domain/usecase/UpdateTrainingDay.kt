package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.domain.model.TrainingDay
import javax.inject.Inject

class UpdateTrainingDay @Inject constructor(
    private val trainingRepository: TrainingRepository
) : suspend (TrainingDay) -> Unit {

    override suspend fun invoke(trainingDay: TrainingDay) {
        trainingRepository.updateTrainingDay(trainingDay)
    }
}