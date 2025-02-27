package com.example.workoutplan.di

import android.content.SharedPreferences
import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepositoryImpl
import com.example.workoutplan.data.trainingplan.TrainingRepository
import com.example.workoutplan.data.trainingplan.TrainingRepositoryImpl
import com.example.workoutplan.db.TrainingPlanDatabase
import com.example.workoutplan.db.dao.TrainingDayDao
import com.example.workoutplan.db.dao.TrainingExerciseDao
import com.example.workoutplan.db.dao.TrainingSetDao
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