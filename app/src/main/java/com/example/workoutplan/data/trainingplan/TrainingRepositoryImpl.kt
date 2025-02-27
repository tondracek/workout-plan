package com.example.workoutplan.data.trainingplan

import com.example.workoutplan.db.dao.TrainingDayDao
import com.example.workoutplan.db.dao.TrainingExerciseDao
import com.example.workoutplan.db.dao.TrainingSetDao
import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingExerciseEntity
import com.example.workoutplan.db.entity.TrainingSetEntity
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.model.TrainingSet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val trainingDayDao: TrainingDayDao,
    private val trainingExerciseDao: TrainingExerciseDao,
    private val trainingSetDao: TrainingSetDao,
) : TrainingRepository {

    override suspend fun addEmptyTrainingDay(name: String) {
        val trainingDayEntity = TrainingDay(name = name, exercises = emptyList())

        upsertTrainingDay(trainingDayEntity)
    }

    override suspend fun updateTrainingDay(trainingDay: TrainingDay) {
        if (trainingDay.id != 0)
            deleteTrainingDay(trainingDay.id)
        upsertTrainingDay(trainingDay)
    }

    override suspend fun deleteTrainingDay(trainingDayId: TrainingDayId) {
        trainingDayDao.deleteTrainingDay(TrainingDayEntity(id = trainingDayId, name = ""))
    }

    override fun getTrainingDayList(): Flow<List<TrainingDay>> {
        val trainingSetsFlow: Flow<List<TrainingSetEntity>> =
            trainingSetDao.getAllTrainingSets()
        val trainingExercisesFlow: Flow<List<TrainingExerciseEntity>> =
            trainingExerciseDao.getAllTrainingExercises()
        val trainingDaysFlow: Flow<List<TrainingDayEntity>> = trainingDayDao.getAllTrainingDay()

        return combine(
            trainingSetsFlow,
            trainingExercisesFlow,
            trainingDaysFlow,
        ) { trainingSets, trainingExercises, trainingDays ->
            trainingDays.map { trainingDayEntity: TrainingDayEntity ->
                val trainingExercisesByDay: List<TrainingExercise> = trainingExercises
                    .filter { exerciseEntity -> exerciseEntity.trainingDayId == trainingDayEntity.id }
                    .map { exerciseEntity ->
                        val trainingSetsByExercise: List<TrainingSet> = trainingSets
                            .filter { setEntity -> setEntity.trainingExerciseId == exerciseEntity.id }
                            .map(TrainingSetEntity::toModel)

                        exerciseEntity.toModel(trainingSetsByExercise)
                    }

                trainingDayEntity.toModel(trainingExercisesByDay)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDay> =
        trainingDayDao.getTrainingDayById(id).filterNotNull().flatMapLatest { trainingDayEntity ->
            trainingExerciseDao.getTrainingExercisesByTrainingDayId(id)
                .flatMapLatest { trainingExerciseEntities: List<TrainingExerciseEntity> ->
                    val trainingExerciseFlows: List<Flow<TrainingExercise>> =
                        trainingExerciseEntities.map { trainingExerciseEntity ->
                            trainingSetDao.getTrainingSetsByExerciseId(trainingExerciseEntity.id)
                                .map { trainingSetEntities: List<TrainingSetEntity> ->
                                    val trainingSets = trainingSetEntities
                                        .map(TrainingSetEntity::toModel)
                                    trainingExerciseEntity.toModel(trainingSets)
                                }
                        }

                    if (trainingExerciseFlows.isEmpty()) {
                        flowOf(trainingDayEntity.toModel(emptyList()))
                    } else {
                        combine(trainingExerciseFlows) {
                            trainingDayEntity.toModel(it.toList())
                        }
                    }
                }
        }

    private suspend fun upsertTrainingDay(trainingDay: TrainingDay) {
        val trainingDayEntity = trainingDay.toEntity()
        trainingDayDao.upsertTrainingDay(trainingDayEntity)

        for (trainingExercise in trainingDay.exercises) {
            upsertTrainingExercise(trainingExercise, trainingDayEntity.id)
        }
    }

    private suspend fun upsertTrainingExercise(
        trainingExercise: TrainingExercise,
        trainingDayId: TrainingDayId
    ) {
        val trainingExerciseEntity = trainingExercise.toEntity(trainingDayId)
        trainingExerciseDao.upsertTrainingExercise(trainingExerciseEntity)

        for (trainingSet in trainingExercise.sets) {
            upsertTrainingSet(trainingSet, trainingExerciseEntity.id)
        }
    }

    private suspend fun upsertTrainingSet(trainingSet: TrainingSet, trainingExerciseId: Int) {
        val trainingSetEntity = trainingSet.toEntity(trainingExerciseId)
        trainingSetDao.upsertTrainingSet(trainingSetEntity)
    }
}
