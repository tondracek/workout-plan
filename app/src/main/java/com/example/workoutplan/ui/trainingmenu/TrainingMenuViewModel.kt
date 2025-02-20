package com.example.workoutplan.ui.trainingmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.usecase.GetTotalExercisesInTrainingDay
import com.example.workoutplan.domain.usecase.GetTotalSetsInTrainingDay
import com.example.workoutplan.domain.usecase.GetTrainingDayList
import com.example.workoutplan.ui.navigation.AppNavigator
import com.example.workoutplan.ui.trainingsession.navigateToTrainingSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TrainingMenuViewModel @Inject constructor(
    getTrainingDayList: GetTrainingDayList,
    getTotalExercisesInTrainingDay: GetTotalExercisesInTrainingDay,
    getTotalSetsInTrainingDay: GetTotalSetsInTrainingDay,
    private val navigator: AppNavigator,
) : ViewModel() {

    private var trainingDayListFlow: Flow<List<TrainingDay>> = flowOf(getTrainingDayList())

    val uiState: StateFlow<TrainingMenuUiState> = trainingDayListFlow
        .map {
            val trainings = it.map { trainingDay: TrainingDay ->
                TrainingDayUiState(
                    id = trainingDay.id,
                    name = trainingDay.name,
                    totalExercises = getTotalExercisesInTrainingDay(trainingDay.id),
                    totalSets = getTotalSetsInTrainingDay(trainingDay.id)
                )
            }
            TrainingMenuUiState.Success(
                trainings = trainings
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TrainingMenuUiState.Loading
        )

    fun onTrainingDaySelected(id: TrainingDayId) = navigator.navigateToTrainingSession(id)
}