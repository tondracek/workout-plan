package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
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