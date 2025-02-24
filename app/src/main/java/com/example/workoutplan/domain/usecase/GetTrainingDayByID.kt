package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.data.repository.TrainingRepository
import com.example.workoutplan.domain.model.TrainingDay
import javax.inject.Inject

class GetTrainingDayByID @Inject constructor(
    private val trainingRepository: TrainingRepository
) : (TrainingDayId) -> TrainingDay {
    override operator fun invoke(id: TrainingDayId) = trainingRepository.getTrainingDayById(id)
}