package com.example.workoutplan.data.repository

import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay

interface TrainingRepository {

    fun getTrainingDayList(): List<TrainingDay>

    fun getTrainingDayById(id: TrainingDayId): TrainingDay
}