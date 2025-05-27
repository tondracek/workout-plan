package com.tondracek.workoutplan.data.currenttrainingday

import android.content.SharedPreferences
import com.tondracek.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

private const val CURRENT_TRAINING_DAY_INDEX = "current_training_day_index"
private const val NULL_VALUE: Long = -1L

class CurrentTrainingDayRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : CurrentTrainingDayRepository {

    private val currentIndexFlow: MutableStateFlow<TrainingDayId?> =
        MutableStateFlow(getCurrentTrainingDayId())

    override fun getCurrentTrainingDayIdFlow(): Flow<TrainingDayId?> = currentIndexFlow

    override suspend fun setCurrentTrainingDayId(id: TrainingDayId?) {
        currentIndexFlow.emit(id)
        sharedPreferences.edit()
            .putLong(CURRENT_TRAINING_DAY_INDEX, id ?: NULL_VALUE)
            .apply()
    }

    private fun getCurrentTrainingDayId(): Long? =
        sharedPreferences.getLong(CURRENT_TRAINING_DAY_INDEX, NULL_VALUE)
            .takeIf { it != NULL_VALUE }
}