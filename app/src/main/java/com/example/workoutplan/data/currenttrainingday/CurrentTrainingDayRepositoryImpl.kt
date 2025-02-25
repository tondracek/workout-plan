package com.example.workoutplan.data.currenttrainingday

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

private const val CURRENT_TRAINING_DAY_INDEX = "current_training_day_index"

class CurrentTrainingDayRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : CurrentTrainingDayRepository {

    private val currentIndexFlow: MutableStateFlow<Int> =
        MutableStateFlow(getCurrentTrainingDayIndex())

    override fun getCurrentTrainingDayIndexFlow(): Flow<Int> = currentIndexFlow

    override suspend fun setCurrentTrainingDayIndex(index: Int) {
        sharedPreferences.edit()
            .putInt(CURRENT_TRAINING_DAY_INDEX, index)
            .apply()
        currentIndexFlow.emit(index)
    }

    private fun getCurrentTrainingDayIndex(): Int =
        sharedPreferences.getInt(CURRENT_TRAINING_DAY_INDEX, 0)
}