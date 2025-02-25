package com.example.workoutplan.data.currenttrainingday

import kotlinx.coroutines.flow.Flow

interface CurrentTrainingDayRepository {

    fun getCurrentTrainingDayIndexFlow(): Flow<Int>

    suspend fun setCurrentTrainingDayIndex(index: Int)
}