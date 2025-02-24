package com.example.workoutplan.ui.trainingsession

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutplan.data.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.usecase.GetTrainingDayByID
import com.example.workoutplan.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TrainingSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTrainingDayByID: GetTrainingDayByID,
    private val navigator: AppNavigator,
) : ViewModel() {

    private val trainingDayId: StateFlow<TrainingDayId?> =
        MutableStateFlow(savedStateHandle.toRoute<TrainingSessionRoute>().id)

    private val _trainingDayFlow: Flow<TrainingDay?> =
        trainingDayId.map { it?.let { getTrainingDayByID(id = it) } }

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
                }
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

    fun onFinishTrainingClicked() {
        TODO("Not yet implemented")
    }

    fun navigateBack() = navigator.navigateBack()
}
