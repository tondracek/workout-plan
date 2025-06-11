package com.tondracek.workoutplan.di

import android.content.SharedPreferences
import com.tondracek.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.tondracek.workoutplan.data.currenttrainingday.CurrentTrainingDayRepositoryImpl
import com.tondracek.workoutplan.data.trainingplan.TrainingRepository
import com.tondracek.workoutplan.data.trainingplan.TrainingRepositoryImpl
import com.tondracek.workoutplan.db.TrainingPlanDatabase
import com.tondracek.workoutplan.db.dao.TrainingDayDao
import com.tondracek.workoutplan.db.dao.TrainingExerciseDao
import com.tondracek.workoutplan.db.dao.TrainingSetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTrainingRepository(
        db: TrainingPlanDatabase,
        trainingDayDao: TrainingDayDao,
        trainingExerciseDao: TrainingExerciseDao,
        trainingSetDao: TrainingSetDao,
    ): TrainingRepository = TrainingRepositoryImpl(
        db = db,
        trainingDayDao = trainingDayDao,
        trainingExerciseDao = trainingExerciseDao,
        trainingSetDao = trainingSetDao,
    )

    @Provides
    @Singleton
    fun provideCurrentTrainingDayRepository(
        sharedPreferences: SharedPreferences
    ): CurrentTrainingDayRepository {
        return CurrentTrainingDayRepositoryImpl(sharedPreferences)
    }
}