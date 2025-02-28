package com.example.workoutplan.data.currenttrainingday

import com.example.workoutplan.db.entity.TrainingDayId
import kotlinx.coroutines.flow.Flow

interface CurrentTrainingDayRepository {

    fun getCurrentTrainingDayIdFlow(): Flow<TrainingDayId?>

    suspend fun setCurrentTrainingDayId(id: TrainingDayId?)
}