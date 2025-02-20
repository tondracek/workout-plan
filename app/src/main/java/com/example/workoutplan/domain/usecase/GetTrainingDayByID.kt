package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.data.repository.TrainingRepository
import javax.inject.Inject

class GetTrainingDayByID @Inject constructor(
    private val trainingRepository: TrainingRepository
) {
    operator fun invoke(id: TrainingDayId) = trainingRepository.getTrainingDayById(id)
}