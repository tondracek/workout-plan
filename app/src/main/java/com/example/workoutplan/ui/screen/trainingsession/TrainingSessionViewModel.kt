package com.example.workoutplan.ui.screen.trainingsession

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.db.entity.TrainingExerciseId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.usecase.FinishTrainingDay
import com.example.workoutplan.domain.usecase.GetTrainingDayByID
import com.example.workoutplan.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FINISHED_EXERCISES_KEY = "finished_exercises_key"

@HiltViewModel
class TrainingSessionViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    getTrainingDayByID: GetTrainingDayByID,
    private val finishTrainingDay: FinishTrainingDay,
    private val navigator: AppNavigator,
) : ViewModel() {

    private val trainingDayId: StateFlow<TrainingDayId> =
        MutableStateFlow(savedStateHandle.getTrainingDaySessionId())

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _trainingDayFlow: Flow<TrainingDay?> =
        trainingDayId.flatMapLatest { getTrainingDayByID(id = it) }

    private val _finishedExercises: StateFlow<Set<TrainingExerciseId>> =
        savedStateHandle.getStateFlow<Set<TrainingExerciseId>>(FINISHED_EXERCISES_KEY, emptySet())
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptySet()
            )

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
                    exercise to (exercise.id in finishedExercises)
                },
                isDone = finishedExercises.size == trainingDay.exercises.size
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TrainingSessionUiState.Loading
    )

    fun onFinishExerciseClicked(trainingExerciseId: TrainingExerciseId, finished: Boolean) {
        val finishedExercises = _finishedExercises.value.toMutableSet()

        if (finished) {
            finishedExercises.add(trainingExerciseId)
        } else {
            finishedExercises.remove(trainingExerciseId)
        }

        savedStateHandle[FINISHED_EXERCISES_KEY] = finishedExercises
    }

    fun onFinishTrainingClicked() = viewModelScope.launch {
        finishTrainingDay(trainingDayId.value)
        navigator.navigateBack()
    }

    fun navigateBack() = navigator.navigateBack()
}
