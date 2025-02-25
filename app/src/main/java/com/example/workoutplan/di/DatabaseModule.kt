package com.example.workoutplan.di

import android.content.Context
import com.example.workoutplan.db.TrainingPlanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): TrainingPlanDatabase {
        return TrainingPlanDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTrainingDayDao(database: TrainingPlanDatabase) = database.trainingDayDao()

    @Provides
    @Singleton
    fun provideTrainingExerciseDao(database: TrainingPlanDatabase) = database.trainingExerciseDao()

    @Provides
    @Singleton
    fun provideTrainingSetDao(database: TrainingPlanDatabase) = database.trainingSetDao()
}