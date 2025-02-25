package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTotalSetsInTrainingDay @Inject constructor(
    private val trainingRepository: TrainingRepository
) : suspend (TrainingDayId) -> Int {

    override suspend operator fun invoke(id: TrainingDayId) =
        trainingRepository.getTrainingDayById(id).first()
            .exercises.sumOf { it.sets.size }
}
