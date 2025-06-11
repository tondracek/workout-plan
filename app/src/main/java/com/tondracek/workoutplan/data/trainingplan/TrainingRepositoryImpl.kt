package com.tondracek.workoutplan.data.trainingplan

import android.util.Log
import androidx.room.withTransaction
import com.tondracek.workoutplan.db.TrainingPlanDatabase
import com.tondracek.workoutplan.db.dao.TrainingDayDao
import com.tondracek.workoutplan.db.dao.TrainingExerciseDao
import com.tondracek.workoutplan.db.dao.TrainingSetDao
import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.db.entity.TrainingDayWithExerciseWithSet
import com.tondracek.workoutplan.db.entity.TrainingExerciseEntity
import com.tondracek.workoutplan.db.entity.TrainingExerciseId
import com.tondracek.workoutplan.db.entity.TrainingSetEntity
import com.tondracek.workoutplan.domain.model.TrainingDay
import com.tondracek.workoutplan.domain.model.TrainingExercise
import com.tondracek.workoutplan.domain.model.TrainingSet
import com.tondracek.workoutplan.domain.model.Weight
import com.tondracek.workoutplan.domain.model.WeightUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val db: TrainingPlanDatabase,
    private val trainingDayDao: TrainingDayDao,
    private val trainingExerciseDao: TrainingExerciseDao,
    private val trainingSetDao: TrainingSetDao,
) : TrainingRepository {

    override suspend fun addEmptyTrainingDay(name: String) {
        val trainingDay = TrainingDay(name = name, finishedCount = 0, exercises = emptyList())
        val orderIndex = trainingDayDao.getNewOrderIndex()

        upsertTrainingDay(trainingDay, orderIndex)
    }

    override suspend fun updateTrainingDay(trainingDay: TrainingDay) = db.withTransaction {
        deleteTrainingDayExercises(trainingDay.id)
        val orderIndex = trainingDayDao.getTrainingDayOrderIndex(trainingDay.id)

        upsertTrainingDay(trainingDay, orderIndex)
    }

    override suspend fun deleteTrainingDay(trainingDayId: TrainingDayId) =
        trainingDayDao.deleteTrainingDayById(trainingDayId)

    override fun getTrainingDayList(): Flow<List<TrainingDay>> =
        trainingDayDao.getAllTrainingDay()
            .map { mapToTrainingDay(it) }

    override fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDay?> =
        trainingDayDao.getTrainingDayById(id)
            .map { mapToTrainingDay(it).firstOrNull() }

    override fun getTrainingDaysCount(): Flow<Int> = trainingDayDao.getTrainingDaysCount()

    override suspend fun moveTrainingDaySooner(trainingDayId: TrainingDayId) {
        val trainingDayEntities = trainingDayDao.getAllTrainingDayEntities().first()

        val trainingDay = trainingDayEntities.first { it.id == trainingDayId }

        val previousTrainingDay = trainingDayEntities
            .lastOrNull { it.orderIndex < trainingDay.orderIndex } ?: return

        trainingDayDao.updateTrainingDay(trainingDay.copy(orderIndex = previousTrainingDay.orderIndex))
        trainingDayDao.updateTrainingDay(previousTrainingDay.copy(orderIndex = previousTrainingDay.orderIndex + 1))
    }

    override suspend fun moveTrainingDayLater(trainingDayId: TrainingDayId) {
        val trainingDayEntities = trainingDayDao.getAllTrainingDayEntities().first()

        val trainingDay = trainingDayEntities.first { it.id == trainingDayId }

        val nextTrainingDay = trainingDayEntities
            .firstOrNull { it.orderIndex > trainingDay.orderIndex } ?: return

        trainingDayDao.updateTrainingDay(nextTrainingDay.copy(orderIndex = trainingDay.orderIndex))
        trainingDayDao.updateTrainingDay(trainingDay.copy(orderIndex = trainingDay.orderIndex + 1))
    }

    override suspend fun getFollowingTrainingDayId(trainingDayId: TrainingDayId): TrainingDayId? {
        return trainingDayDao.getFollowingTrainingDayId(trainingDayId)
    }

    override suspend fun increaseFinishedCount(trainingDayId: TrainingDayId) =
        trainingDayDao.increaseFinishedCount(trainingDayId)

    /**
     * @param entities The list of @see TrainingDayWithExerciseWithSet entities
     * @return A list of @see TrainingDay objects with the exercises and sets from the entities
     */
    private fun mapToTrainingDay(entities: List<TrainingDayWithExerciseWithSet>): List<TrainingDay> =
        entities.groupBy { it.trainingDayId }
            .mapNotNull { (trainingDayId, exerciseEntities) ->
                val trainingDayName = exerciseEntities.firstOrNull()?.trainingDayName
                    ?: return@mapNotNull null
                val finishedCount = exerciseEntities.firstOrNull()?.trainingDayFinishedCount
                    ?: return@mapNotNull null
                val exercisesInDay: List<TrainingExercise> = exerciseEntities
                    .groupBy { it.trainingExerciseId }
                    .mapNotNull { (exerciseId, setEntities) ->
                        mapToTrainingExercise(
                            exerciseId,
                            setEntities
                        )
                    }

                TrainingDay(
                    id = trainingDayId,
                    name = trainingDayName,
                    exercises = exercisesInDay,
                    finishedCount = finishedCount,
                )
            }

    /**
     * @param exerciseId The id of the exercise
     * @param setEntities The list of @see TrainingDayWithExerciseWithSet entities that belong to the exerciseId.
     *                    These are only the sets of the exercise.
     * @return A @see TrainingExercise object with the sets from the setEntities
     */
    private fun mapToTrainingExercise(
        exerciseId: TrainingExerciseId?,
        setEntities: List<TrainingDayWithExerciseWithSet>
    ): TrainingExercise? {
        exerciseId ?: return null
        val exerciseName = setEntities.firstOrNull()?.trainingExerciseName ?: return null

        val trainingSets: List<TrainingSet> = setEntities.mapNotNull { setEntity ->
            when {
                setEntity.trainingSetId == null || setEntity.weight == null || setEntity.weightUnit == null || setEntity.reps == null -> null
                else -> TrainingSet(
                    id = setEntity.trainingSetId,
                    weight = Weight(setEntity.weight, WeightUnit(setEntity.weightUnit)),
                    reps = setEntity.reps,
                )
            }
        }

        return TrainingExercise(
            id = exerciseId,
            name = exerciseName,
            sets = trainingSets
        )
    }

    private suspend fun upsertTrainingDay(trainingDay: TrainingDay, newOrderIndex: Int) {
        val trainingDayEntity = trainingDay.toEntity(newOrderIndex)

        trainingDayDao.upsertTrainingDay(trainingDayEntity)
        Log.d("$this", "Upserted training day: $trainingDayEntity")

        val exercisesWithIds = insertTrainingExerciseEntities(trainingDay.id, trainingDay.exercises)
        insertTrainingSetEntities(exercisesWithIds)
    }

    private suspend fun insertTrainingExerciseEntities(
        trainingDayId: TrainingDayId,
        trainingExercises: List<TrainingExercise>,
    ): List<TrainingExercise> {
        val exerciseEntities: List<TrainingExerciseEntity> = trainingExercises
            .mapIndexed { i, value -> value.toEntity(i, trainingDayId) }

        Log.d("$this", "Inserting exercises: $exerciseEntities")
        val insertedExerciseIds: List<Long> =
            trainingExerciseDao.insertTrainingExercises(exerciseEntities)

        return trainingExercises.mapIndexed { i, trainingExercise ->
            trainingExercise.copy(id = insertedExerciseIds[i])
        }
    }

    private suspend fun insertTrainingSetEntities(trainingExercises: List<TrainingExercise>) {
        val setEntities: List<TrainingSetEntity> = trainingExercises.flatMap { trainingExercise ->
            trainingExercise.sets.mapIndexed { i, trainingSet ->
                trainingSet.toEntity(i, trainingExercise.id)
            }
        }

        Log.d("$this", "Inserting sets: $setEntities")
        trainingSetDao.insertTrainingSets(setEntities)
    }

    private suspend fun deleteTrainingDayExercises(trainingDayId: TrainingDayId) =
        trainingExerciseDao.deleteTrainingDayExercises(trainingDayId)
}
