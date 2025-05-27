package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingDaysCount @Inject constructor(
    private val trainingRepository: TrainingRepository
) : () -> Flow<Int> {

    override fun invoke() = trainingRepository.getTrainingDaysCount()
}