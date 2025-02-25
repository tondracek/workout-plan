package com.example.workoutplan.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workoutplan.db.dao.TrainingDayDao
import com.example.workoutplan.db.dao.TrainingExerciseDao
import com.example.workoutplan.db.dao.TrainingSetDao
import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingExerciseEntity
import com.example.workoutplan.db.entity.TrainingSetEntity

@Database(
    entities = [
        TrainingDayEntity::class,
        TrainingExerciseEntity::class,
        TrainingSetEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class TrainingPlanDatabase : RoomDatabase() {

    abstract fun trainingDayDao(): TrainingDayDao

    abstract fun trainingSetDao(): TrainingSetDao

    abstract fun trainingExerciseDao(): TrainingExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: TrainingPlanDatabase? = null

        fun getInstance(context: Context): TrainingPlanDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TrainingPlanDatabase::class.java,
                    "training_plan_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
