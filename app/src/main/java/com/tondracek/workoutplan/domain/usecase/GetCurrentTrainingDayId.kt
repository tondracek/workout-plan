package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentTrainingDayId @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val currentTrainingDayRepository: CurrentTrainingDayRepository
) : () -> Flow<TrainingDayId?> {

    override fun invoke(): Flow<TrainingDayId?> {
        return currentTrainingDayRepository.getCurrentTrainingDayIdFlow()
            .map { id: TrainingDayId? ->
                id ?: trainingRepository.getTrainingDayList().first().firstOrNull()?.id
            }
    }
}