package com.example.workoutplan.di

import android.content.SharedPreferences
import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepository
import com.example.workoutplan.data.currenttrainingday.CurrentTrainingDayRepositoryImpl
import com.example.workoutplan.data.trainingplan.FakeTrainingRepository
import com.example.workoutplan.data.trainingplan.TrainingRepository
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
    fun provideTrainingRepository(): TrainingRepository {
        return FakeTrainingRepository()
    }

    @Provides
    @Singleton
    fun provideCurrentTrainingDayRepository(
        sharedPreferences: SharedPreferences
    ): CurrentTrainingDayRepository {
        return CurrentTrainingDayRepositoryImpl(sharedPreferences)
    }
}