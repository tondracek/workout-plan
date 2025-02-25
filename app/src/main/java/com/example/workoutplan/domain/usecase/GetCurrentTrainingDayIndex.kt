package com.example.workoutplan.domain.usecase

import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentTrainingDayIndex @Inject constructor(
    private val currentTrainingDayRepository: CurrentTrainingDayRepository
) : () -> Flow<Int> {

    override fun invoke(): Flow<Int> {
        return currentTrainingDayRepository.getCurrentTrainingDayIndexFlow()
    }
}