package com.tondracek.workoutplan.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tondracek.workoutplan.db.dao.TrainingDayDao
import com.tondracek.workoutplan.db.dao.TrainingExerciseDao
import com.tondracek.workoutplan.db.dao.TrainingSetDao
import com.tondracek.workoutplan.db.entity.TrainingDayEntity
import com.tondracek.workoutplan.db.entity.TrainingExerciseEntity
import com.tondracek.workoutplan.db.entity.TrainingSetEntity
import com.tondracek.workoutplan.db.migration.MIGRATION_1_2
import com.tondracek.workoutplan.db.migration.MIGRATION_2_3
import com.tondracek.workoutplan.db.migration.MIGRATION_3_4

@Database(
    entities = [
        TrainingDayEntity::class,
        TrainingExerciseEntity::class,
        TrainingSetEntity::class
    ],
    version = 4,
    exportSchema = true,
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
                )
                    .addMigrations(*migrations)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}

val migrations = arrayOf(
    MIGRATION_1_2,
    MIGRATION_2_3,
    MIGRATION_3_4,
)
