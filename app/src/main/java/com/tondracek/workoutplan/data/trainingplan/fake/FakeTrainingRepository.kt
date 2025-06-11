package com.tondracek.workoutplan.data.trainingplan.fake

//class FakeTrainingRepository : TrainingRepository {
//
//    private val trainingPlan = emptyList<TrainingDay>()
//
//    override suspend fun addEmptyTrainingDay(name: String) {
//    }
//
//    override suspend fun updateTrainingDay(trainingDay: TrainingDay) {
//    }
//
//    override suspend fun deleteTrainingDay(trainingDayId: TrainingDayId) {
//    }
//
//    override fun getTrainingDayList(): Flow<List<TrainingDay>> = flowOf(trainingPlan)
//
//    override fun getTrainingDayById(id: TrainingDayId): Flow<TrainingDay?> =
//        flowOf(trainingPlan.first { it.id == id })
//}