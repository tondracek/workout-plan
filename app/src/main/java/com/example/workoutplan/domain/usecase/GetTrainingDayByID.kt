package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingDayByID @Inject constructor(
    private val trainingRepository: TrainingRepository
) : (TrainingDayId) -> Flow<TrainingDay?> {

    override operator fun invoke(id: TrainingDayId): Flow<TrainingDay?> =
        trainingRepository.getTrainingDayById(id)
}