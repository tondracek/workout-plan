package com.example.workoutplan.domain.usecase

import com.example.workoutplan.domain.model.TrainingDay
import javax.inject.Inject

class GetTotalExercisesInTrainingDay @Inject constructor(
) : (TrainingDay) -> Int {

    override operator fun invoke(trainingDay: TrainingDay): Int =
        trainingDay.exercises.size
}
