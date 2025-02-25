package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.example.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateActualTrainingDay @Inject constructor(
    private val currentTrainingDayRepository: CurrentTrainingDayRepository,
    private val getTrainingDaysCount: GetTrainingDaysCount,
    private val getTrainingDayIndexById: GetTrainingDayIndexById,
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(currentlyFinishedTrainingDayId: TrainingDayId) {
        val trainingDaysCount: Int = getTrainingDaysCount().first()
        val currentTrainingDayIndex =
            getTrainingDayIndexById(currentlyFinishedTrainingDayId).first()

        if (trainingDaysCount == 0 || currentTrainingDayIndex >= trainingDaysCount) {
            return currentTrainingDayRepository.setCurrentTrainingDayIndex(0)
        }

        val nextTrainingDayIndex = (currentTrainingDayIndex + 1) % trainingDaysCount
        currentTrainingDayRepository.setCurrentTrainingDayIndex(nextTrainingDayIndex)
    }

    suspend fun invoke() =
        this(currentTrainingDayRepository.getCurrentTrainingDayIndexFlow().first())
}
