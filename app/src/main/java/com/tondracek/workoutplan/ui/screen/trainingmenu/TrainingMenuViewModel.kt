package com.tondracek.workoutplan.ui.screen.trainingmenu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.domain.model.TrainingDay
import com.tondracek.workoutplan.domain.usecase.CreateEmptyTrainingDay
import com.tondracek.workoutplan.domain.usecase.GetCurrentTrainingDayId
import com.tondracek.workoutplan.domain.usecase.GetTotalExercisesInTrainingDay
import com.tondracek.workoutplan.domain.usecase.GetTotalSetsInTrainingDay
import com.tondracek.workoutplan.domain.usecase.GetTrainingDayList
import com.tondracek.workoutplan.ui.navigation.AppNavigator
import com.tondracek.workoutplan.ui.screen.edittraining.navigateToEditTrainingDay
import com.tondracek.workoutplan.ui.screen.trainingsession.navigateToTrainingSession
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
    getCurrentTrainingDayId: GetCurrentTrainingDayId,
    private val createEmptyTrainingDay: CreateEmptyTrainingDay,
    private val navigator: AppNavigator,
) : ViewModel() {

    private var _trainingDayListFlow: Flow<List<TrainingDay>> = getTrainingDayList()

    private var _currentTrainingDayIdFlow: Flow<TrainingDayId?> = getCurrentTrainingDayId()

    val uiState: StateFlow<TrainingMenuUiState> = combine(
        _trainingDayListFlow,
        _currentTrainingDayIdFlow
    ) { trainingDayList, currentTrainingDayId ->
        val trainings = trainingDayList.map { trainingDay: TrainingDay ->
            TrainingDayUiState(
                id = trainingDay.id,
                name = trainingDay.name,
                finishedCount = trainingDay.finishedCount,
                totalExercises = getTotalExercisesInTrainingDay(trainingDay),
                totalSets = getTotalSetsInTrainingDay(trainingDay),
            )
        }
        TrainingMenuUiState.Success(
            trainings = trainings,
            currentTrainingDayIndex = trainingDayList.indexOfFirst { it.id == currentTrainingDayId }
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