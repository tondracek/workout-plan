package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.data.repository.TrainingRepository
import javax.inject.Inject

class GetTotalExercisesInTrainingDay @Inject constructor(
    private val trainingRepository: TrainingRepository,
) {
    operator fun invoke(id: TrainingDayId): Int =
        trainingRepository.getTrainingDayById(id).exercises.size
}
