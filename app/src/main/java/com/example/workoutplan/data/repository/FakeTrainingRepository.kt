package com.example.workoutplan.data.repository

import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay

class FakeTrainingRepository : TrainingRepository {

    private val trainingPlan = customTrainingPlan

    override fun getTrainingDayList(): List<TrainingDay> = customTrainingPlan

    override fun getTrainingDayById(id: TrainingDayId): TrainingDay =
        trainingPlan.first { it.id == id }
}