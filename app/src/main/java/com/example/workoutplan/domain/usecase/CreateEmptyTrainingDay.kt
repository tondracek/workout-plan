package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import javax.inject.Inject

class CreateEmptyTrainingDay @Inject constructor(
    private val repository: TrainingRepository
) : suspend () -> Unit {

    override suspend fun invoke() = repository.addEmptyTrainingDay("New Training Day")
}