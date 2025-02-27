package com.example.workoutplan.ui.screen.trainingsession

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.usecase.GetTrainingDayByID
import com.example.workoutplan.domain.usecase.UpdateActualTrainingDay
import com.example.workoutplan.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTrainingDayByID: GetTrainingDayByID,
    private val updateActualTrainingDay: UpdateActualTrainingDay,
    private val navigator: AppNavigator,
) : ViewModel() {

    private val trainingDayId: StateFlow<TrainingDayId> =
        MutableStateFlow(savedStateHandle.getTrainingDaySessionId())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _trainingDayFlow: Flow<TrainingDay?> =
        trainingDayId
            .flatMapLatest { getTrainingDayByID(id = it) }
            .onEach { _finishedExercises.emit(emptySet()) }

    private val _finishedExercises: MutableStateFlow<Set<TrainingExercise>> =
        MutableStateFlow(emptySet())

    val uiState: StateFlow<TrainingSessionUiState> = combine(
        _trainingDayFlow,
        _finishedExercises,
    ) { trainingDay, finishedExercises ->
        when (trainingDay) {
            null -> TrainingSessionUiState.Error
                .also { navigator.navigateBack() }

            else -> TrainingSessionUiState.Success(
                trainingName = trainingDay.name,
                exercises = trainingDay.exercises.map { exercise ->
                    exercise to (exercise in finishedExercises)
                },
                isDone = finishedExercises.size == trainingDay.exercises.size
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TrainingSessionUiState.Loading
    )

    fun onFinishExerciseClicked(trainingExercise: TrainingExercise, finished: Boolean) {
        _finishedExercises.update {
            if (finished)
                it + trainingExercise
            else
                it - trainingExercise
        }
    }

    fun onFinishTrainingClicked() = viewModelScope.launch {
        updateActualTrainingDay(trainingDayId.value)
        navigator.navigateBack()
    }

    fun navigateBack() = navigator.navigateBack()
}
