package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTrainingDayIndexById @Inject constructor(
    private val trainingRepository: TrainingRepository
) : (TrainingDayId) -> Flow<Int> {

    override operator fun invoke(id: TrainingDayId) =
        trainingRepository.getTrainingDayList().map { trainingDayList ->
            trainingDayList.indexOfFirst { it.id == id }
        }
}