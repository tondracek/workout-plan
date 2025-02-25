package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.domain.model.TrainingDay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingDayList @Inject constructor(
    private val trainingRepository: TrainingRepository
) : () -> Flow<List<TrainingDay>> {
    override operator fun invoke(): Flow<List<TrainingDay>> = trainingRepository.getTrainingDayList()
}