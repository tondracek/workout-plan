package com.example.workoutplan.data.di

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
}