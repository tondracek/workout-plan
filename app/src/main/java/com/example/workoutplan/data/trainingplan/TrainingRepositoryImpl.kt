package com.example.workoutplan.data.trainingplan

import android.util.Log
import androidx.room.withTransaction
import com.example.workoutplan.db.TrainingPlanDatabase
import com.example.workoutplan.db.dao.TrainingDayDao
import com.example.workoutplan.db.dao.TrainingExerciseDao
import com.example.workoutplan.db.dao.TrainingSetDao
import com.example.workoutplan.db.entity.TrainingDayEntity
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingDayWithExerciseWithSet
import com.example.workoutplan.db.entity.TrainingExerciseId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.model.TrainingSet
import com.example.workoutplan.domain.model.Weight
import com.example.workoutplan.domain.model.WeightUnit
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
        val trainingDayEntity = TrainingDay(name = name, exercises = emptyList())

        upsertTrainingDay(trainingDayEntity)
    }

    override suspend fun updateTrainingDay(trainingDay: TrainingDay) = db.withTransaction {
        deleteTrainingDay(trainingDay.id)
        upsertTrainingDay(trainingDay)
    }

    override suspend fun deleteTrainingDay(trainingDayId: TrainingDayId) =
        trainingDayDao.deleteTrainingDay(TrainingDayEntity(id = trainingDayId, name = ""))

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
        trainingDayDao.updateTrainingDay(previousTrainingDay.copy(orderIndex = trainingDay.orderIndex))
    }

    override suspend fun moveTrainingDayLater(trainingDayId: TrainingDayId) {
        val trainingDayEntities = trainingDayDao.getAllTrainingDayEntities().first()

        val trainingDay = trainingDayEntities.first { it.id == trainingDayId }

        val nextTrainingDay = trainingDayEntities
            .firstOrNull { it.orderIndex > trainingDay.orderIndex } ?: return

        trainingDayDao.updateTrainingDay(trainingDay.copy(orderIndex = nextTrainingDay.orderIndex))
        trainingDayDao.updateTrainingDay(nextTrainingDay.copy(orderIndex = trainingDay.orderIndex))
    }

    override suspend fun getFollowingTrainingDayId(trainingDayId: TrainingDayId): TrainingDayId? {
        return trainingDayDao.getFollowingTrainingDayId(trainingDayId)
    }

    /**
     * @param entities The list of @see TrainingDayWithExerciseWithSet entities
     * @return A list of @see TrainingDay objects with the exercises and sets from the entities
     */
    private fun mapToTrainingDay(entities: List<TrainingDayWithExerciseWithSet>): List<TrainingDay> =
        entities.groupBy { it.trainingDayId }
            .mapNotNull { (trainingDayId, exerciseEntities) ->
                val trainingDayName = exerciseEntities.firstOrNull()?.trainingDayName
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
                    exercises = exercisesInDay
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

    private suspend fun upsertTrainingDay(trainingDay: TrainingDay) {
        val newOrderIndex = trainingDayDao.getNewOrderIndex()

        val trainingDayEntity = trainingDay.toEntity(newOrderIndex)
        val trainingDayId = trainingDayDao.insertTrainingDay(trainingDayEntity)
        Log.d("$this", "$trainingDayId -> $trainingDayEntity")

        for ((i, trainingExercise) in trainingDay.exercises.withIndex()) {
            upsertTrainingExercise(trainingExercise, i, trainingDayId)
        }
    }

    private suspend fun upsertTrainingExercise(
        trainingExercise: TrainingExercise,
        orderIndex: Int,
        trainingDayId: TrainingDayId,
    ) {
        val trainingExerciseEntity = trainingExercise.toEntity(orderIndex, trainingDayId)
        val trainingExerciseId = trainingExerciseDao.insertTrainingExercise(trainingExerciseEntity)
        Log.d("$this", "$trainingExerciseId -> $trainingExerciseEntity")

        for ((i, trainingSet) in trainingExercise.sets.withIndex()) {
            upsertTrainingSet(trainingSet, i, trainingExerciseId)
        }
    }

    private suspend fun upsertTrainingSet(
        trainingSet: TrainingSet,
        orderIndex: Int,
        trainingExerciseId: TrainingExerciseId,
    ) {
        val trainingSetEntity = trainingSet.toEntity(orderIndex, trainingExerciseId)
        val trainingSetId = trainingSetDao.insertTrainingSet(trainingSetEntity)
        Log.d("$this", "$trainingSetId -> $trainingSetEntity")
    }
}
