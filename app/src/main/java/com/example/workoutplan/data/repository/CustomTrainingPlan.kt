package com.example.workoutplan.data.repository

import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.model.TrainingSet
import com.example.workoutplan.domain.model.Weight
import com.example.workoutplan.domain.model.WeightUnit

val Float.kg get() = Weight(this, WeightUnit("KG"))
val Int.kg get() = this.toFloat().kg

val customTrainingPlan = listOf(
    TrainingDay(
        id = 0,
        name = "Heavy Lower (Squat Focus)",
        exercises = listOf(
            TrainingExercise(
                name = "Squat",
                sets = List(4) { TrainingSet(5, 80.kg) }
            ),
            TrainingExercise(
                name = "Romanian Deadlift",
                sets = List(3) { TrainingSet(8, 85.kg) }
            ),
            TrainingExercise(
                name = "Bulgarian Split Squat",
                sets = List(3) { TrainingSet(8, 25.kg) }
            ),
            TrainingExercise(
                name = "Hanging Leg Raises",
                sets = List(3) { TrainingSet(12, 0.kg) }
            ),
            TrainingExercise(
                name = "Standing Calf Raises",
                sets = List(3) { TrainingSet(15, 0.kg) }
            )
        )
    ),
    TrainingDay(
        id = 1,
        name = "Heavy Upper (Bench Focus + Arms)",
        exercises = listOf(
            TrainingExercise(
                name = "Bench Press",
                sets = List(4) { TrainingSet(5, 64.kg) }
            ),
            TrainingExercise(
                name = "Overhead Press",
                sets = List(3) { TrainingSet(6, 40.kg) }
            ),
            TrainingExercise(
                name = "Pull-Ups",
                sets = List(3) { TrainingSet(8, 0.kg) }
            ),
            TrainingExercise(
                name = "Dips",
                sets = List(3) { TrainingSet(8, 0.kg) }
            ),
            TrainingExercise(
                name = "Barbell Curls",
                sets = List(3) { TrainingSet(10, 30.kg) }
            ),
            TrainingExercise(
                name = "Triceps Rope Pushdown",
                sets = List(3) { TrainingSet(12, 0.kg) }
            )
        )
    ),
    TrainingDay(
        id = 2,
        name = "Heavy Deadlift + Accessories",
        exercises = listOf(
            TrainingExercise(
                name = "Deadlift",
                sets = List(4) { TrainingSet(5, 104.kg) }
            ),
            TrainingExercise(
                name = "Front Squat",
                sets = List(3) { TrainingSet(6, 60.kg) }
            ),
            TrainingExercise(
                name = "Barbell Rows",
                sets = List(3) { TrainingSet(8, 70.kg) }
            ),
            TrainingExercise(
                name = "Hamstring Curls",
                sets = List(3) { TrainingSet(10, 0.kg) }
            ),
            TrainingExercise(
                name = "Hanging Leg Raises",
                sets = List(3) { TrainingSet(12, 0.kg) }
            ),
            TrainingExercise(
                name = "Dumbbell Hammer Curls",
                sets = List(3) { TrainingSet(10, 12.kg) }
            )
        )
    ),
    TrainingDay(
        id = 3,
        name = "Volume & Arm Focus",
        exercises = listOf(
            TrainingExercise(
                name = "Paused Squat",
                sets = List(3) { TrainingSet(6, 70.kg) }
            ),
            TrainingExercise(
                name = "Close-Grip Bench Press",
                sets = List(3) { TrainingSet(6, 60.kg) }
            ),
            TrainingExercise(
                name = "Face Pulls",
                sets = List(3) { TrainingSet(15, 0.kg) }
            ),
            TrainingExercise(
                name = "Incline Dumbbell Curls",
                sets = List(3) { TrainingSet(12, 10.kg) }
            ),
            TrainingExercise(
                name = "Skull Crushers",
                sets = List(3) { TrainingSet(10, 25.kg) }
            ),
            TrainingExercise(
                name = "Lateral Raises",
                sets = List(3) { TrainingSet(12, 8.kg) }
            )
        )
    )
)
