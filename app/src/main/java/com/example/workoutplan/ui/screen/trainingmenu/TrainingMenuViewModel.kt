package com.example.workoutplan.ui.screen.trainingmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.usecase.CreateEmptyTrainingDay
import com.example.workoutplan.domain.usecase.GetCurrentTrainingDayIndex
import com.example.workoutplan.domain.usecase.GetTotalExercisesInTrainingDay
import com.example.workoutplan.domain.usecase.GetTotalSetsInTrainingDay
import com.example.workoutplan.domain.usecase.GetTrainingDayList
import com.example.workoutplan.ui.navigation.AppNavigator
import com.example.workoutplan.ui.screen.edittraining.navigateToEditTrainingDay
import com.example.workoutplan.ui.screen.trainingsession.navigateToTrainingSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingMenuViewModel @Inject constructor(
    getTrainingDayList: GetTrainingDayList,
    getTotalExercisesInTrainingDay: GetTotalExercisesInTrainingDay,
    getTotalSetsInTrainingDay: GetTotalSetsInTrainingDay,
    getCurrentTrainingDayIndex: GetCurrentTrainingDayIndex,
    private val createEmptyTrainingDay: CreateEmptyTrainingDay,
    private val navigator: AppNavigator,
) : ViewModel() {

    private var _trainingDayListFlow: Flow<List<TrainingDay>> = getTrainingDayList()

    private var _currentTrainingDayIndexFlow: Flow<Int> = getCurrentTrainingDayIndex()

    val uiState: StateFlow<TrainingMenuUiState> = combine(
        _trainingDayListFlow,
        _currentTrainingDayIndexFlow
    ) { trainingDayList, currentTrainingDayIndex ->
        val trainings = trainingDayList.map { trainingDay: TrainingDay ->
            TrainingDayUiState(
                id = trainingDay.id,
                name = trainingDay.name,
                totalExercises = getTotalExercisesInTrainingDay(trainingDay.id),
                totalSets = getTotalSetsInTrainingDay(trainingDay.id),
            )
        }
        TrainingMenuUiState.Success(
            trainings = trainings,
            currentTrainingDayIndex = currentTrainingDayIndex,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TrainingMenuUiState.Loading
    )

    fun onTrainingDaySelected(id: TrainingDayId) = navigator.navigateToTrainingSession(id)

    fun onTrainingDayCreated() = viewModelScope.launch {
        createEmptyTrainingDay()
    }

    fun onEditTrainingDayClicked(id: TrainingDayId) = navigator.navigateToEditTrainingDay(id)
}