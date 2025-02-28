package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateActualTrainingDay @Inject constructor(
    private val currentTrainingDayRepository: CurrentTrainingDayRepository,
    private val trainingRepository: TrainingRepository,
) : suspend (TrainingDayId) -> Unit {

    override suspend fun invoke(currentlyFinishedTrainingDayId: TrainingDayId) {
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
}
