package com.tondracek.workoutplan.data.currenttrainingday

import com.tondracek.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.Flow

interface CurrentTrainingDayRepository {

    fun getCurrentTrainingDayIdFlow(): Flow<TrainingDayId?>

    suspend fun setCurrentTrainingDayId(id: TrainingDayId?)
}