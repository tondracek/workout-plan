package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.repository.TrainingRepository
import com.example.workoutplan.domain.model.TrainingDay
import javax.inject.Inject

class GetTrainingDayList @Inject constructor(
    private val trainingRepository: TrainingRepository
) : () -> List<TrainingDay> {
    override operator fun invoke(): List<TrainingDay> = trainingRepository.getTrainingDayList()
}