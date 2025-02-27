package com.example.workoutplan.domain.usecase

import com.example.workoutplan.domain.model.TrainingDay
import javax.inject.Inject

class GetTotalSetsInTrainingDay @Inject constructor(
) : (TrainingDay) -> Int {

    override operator fun invoke(trainingDay: TrainingDay) =
        trainingDay.exercises.sumOf { it.sets.size }
}
