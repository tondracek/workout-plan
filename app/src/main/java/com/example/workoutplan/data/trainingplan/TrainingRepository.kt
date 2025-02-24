package com.example.workoutplan.data.trainingplan

import com.example.workoutplan.data.common.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay

interface TrainingRepository {

    fun getTrainingDayList(): List<TrainingDay>

    fun getTrainingDayById(id: TrainingDayId): TrainingDay
}