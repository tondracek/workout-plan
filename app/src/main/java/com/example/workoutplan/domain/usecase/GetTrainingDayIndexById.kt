package com.example.workoutplan.domain.usecase

import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.data.trainingplan.TrainingRepository
import javax.inject.Inject

class GetTrainingDayIndexById @Inject constructor(
    private val trainingRepository: TrainingRepository
) : (TrainingDayId) -> Int {
    override operator fun invoke(id: TrainingDayId): Int =
        trainingRepository.getTrainingDayList()
            .indexOfFirst { it.id == id }
}