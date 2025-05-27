package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.domain.model.TrainingDay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingDayByID @Inject constructor(
    private val trainingRepository: TrainingRepository
) : (TrainingDayId) -> Flow<TrainingDay?> {

    override operator fun invoke(id: TrainingDayId): Flow<TrainingDay?> =
        trainingRepository.getTrainingDayById(id)
}