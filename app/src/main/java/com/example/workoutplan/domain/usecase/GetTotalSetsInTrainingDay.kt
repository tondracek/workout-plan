package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.common.entity.TrainingDayId
import com.example.workoutplan.data.trainingplan.TrainingRepository
import javax.inject.Inject

class GetTotalSetsInTrainingDay @Inject constructor(
    private val trainingRepository: TrainingRepository
) : (TrainingDayId) -> Int {
    override operator fun invoke(id: TrainingDayId) =
        trainingRepository.getTrainingDayById(id)
            .exercises
            .sumOf { it.sets.size }
}
