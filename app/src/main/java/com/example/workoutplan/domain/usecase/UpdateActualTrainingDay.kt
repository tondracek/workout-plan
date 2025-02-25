package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.common.entity.TrainingDayId
import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class UpdateActualTrainingDay @Inject constructor(
    private val currentTrainingDayRepository: CurrentTrainingDayRepository,
    private val getTrainingDaysCount: GetTrainingDaysCount,
    private val getTrainingDayIndexById: GetTrainingDayIndexById,
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(currentlyFinishedTrainingDayId: TrainingDayId) {
        val trainingDaysCount = getTrainingDaysCount()
        val currentTrainingDayIndex = getTrainingDayIndexById(currentlyFinishedTrainingDayId)

        if (currentTrainingDayIndex >= trainingDaysCount) {
            return currentTrainingDayRepository.setCurrentTrainingDayIndex(0)
        }

        val nextTrainingDayIndex = (currentTrainingDayIndex + 1) % trainingDaysCount
        currentTrainingDayRepository.setCurrentTrainingDayIndex(nextTrainingDayIndex)
    }

    suspend fun invoke() =
        this(currentTrainingDayRepository.getCurrentTrainingDayIndexFlow().last())
}
