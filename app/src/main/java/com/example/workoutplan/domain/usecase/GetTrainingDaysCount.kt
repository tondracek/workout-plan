package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingDaysCount @Inject constructor(
    private val trainingRepository: TrainingRepository
) : () -> Flow<Int> {

    override fun invoke() = trainingRepository.getTrainingDaysCount()
}