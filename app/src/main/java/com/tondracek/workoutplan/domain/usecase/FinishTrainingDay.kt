package com.tondracek.workoutplan.domain.usecase

import com.tondracek.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FinishTrainingDay @Inject constructor(
    private val currentTrainingDayRepository: CurrentTrainingDayRepository,
    private val trainingRepository: TrainingRepository,
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(finishedTrainingDayId: TrainingDayId) {
        setNewActualTrainingDay(finishedTrainingDayId)
        increaseFinishedCount(finishedTrainingDayId)
    }

    private suspend fun setNewActualTrainingDay(currentlyFinishedTrainingDayId: TrainingDayId) {
        val trainingDaysList = trainingRepository.getTrainingDayList().first()

        if (trainingDaysList.isEmpty()) {
            currentTrainingDayRepository.setCurrentTrainingDayId(null)
            return
        }

        val followingDayId: TrainingDayId =
            trainingRepository.getFollowingTrainingDayId(currentlyFinishedTrainingDayId)
                ?: trainingDaysList.first().id

        currentTrainingDayRepository.setCurrentTrainingDayId(followingDayId)
    }

    private suspend fun increaseFinishedCount(trainingDayId: TrainingDayId) =
        trainingRepository.increaseFinishedCount(trainingDayId)
}
