package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import javax.inject.Inject

class GetTrainingDaysCount @Inject constructor(
    private val trainingRepository: TrainingRepository
) : () -> Int {

    override fun invoke() = trainingRepository.getTrainingDayList().size
}