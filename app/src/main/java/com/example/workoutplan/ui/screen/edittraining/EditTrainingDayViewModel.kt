package com.example.workoutplan.ui.screen.edittraining

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutplan.data.trainingplan.fake.kg
import com.example.workoutplan.db.entity.TrainingDayId
import com.example.workoutplan.domain.model.TrainingDay
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.model.TrainingSet
import com.example.workoutplan.domain.usecase.DeleteTrainingDay
import com.example.workoutplan.domain.usecase.GetTrainingDayByID
import com.example.workoutplan.domain.usecase.UpdateTrainingDay
import com.example.workoutplan.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTrainingDayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: AppNavigator,
    private val getTrainingDayByID: GetTrainingDayByID,
    private val deleteTrainingDay: DeleteTrainingDay,
    private val updateTrainingDay: UpdateTrainingDay,
) : ViewModel() {

    private val _trainingDayId: StateFlow<TrainingDayId> =
        MutableStateFlow(savedStateHandle.getTrainingDayEditId())

    private val _trainingDayName: MutableStateFlow<String> = MutableStateFlow("")
    private val _exercises: MutableStateFlow<List<EditTrainingExerciseUiState>> =
        MutableStateFlow(emptyList())

    val uiState: StateFlow<EditTrainingDayUiState> = combine(
        _trainingDayName,
        _exercises,
    ) { trainingDayName, exercises ->
        EditTrainingDayUiState.Success(
            name = trainingDayName,
            exercises = exercises
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EditTrainingDayUiState.Loading
    )

    init {
        viewModelScope.launch {
            _trainingDayId.collectLatest { id ->
                _trainingDayName.value = getTrainingDayByID(id).first().name
            }
        }
        viewModelScope.launch {
            _trainingDayId.collectLatest { id ->
                getTrainingDayByID(id).collectLatest { trainingDay ->
                    _exercises.value = trainingDay.exercises
                        .map { it.toUiState() }
                }
            }
        }
    }

    /** TRAINING DAY **/

    fun onNameChanged(newTrainingDayName: String) = viewModelScope.launch {
        _trainingDayName.update { newTrainingDayName }
    }

    /** TRAINING EXERCISE **/

    fun onAddExerciseClicked() = viewModelScope.launch {
        _exercises.update { it + EditTrainingExerciseUiState() }
    }

    fun onRemoveExerciseClicked(exerciseIndex: Int) = viewModelScope.launch {
        _exercises.update { it.filterIndexed { index, _ -> index != exerciseIndex } }
    }

    fun onExerciseNameChanged(exerciseIndex: Int, newExerciseName: String) = viewModelScope.launch {
        _exercises.update { exercises ->
            exercises.mapIndexed { index, exercise ->
                when (index) {
                    exerciseIndex -> exercise.copy(name = newExerciseName)
                    else -> exercise
                }
            }
        }
    }

    /** TRAINING SET **/

    fun onAddSetClicked(exerciseIndex: Int) = viewModelScope.launch {
        _exercises.update { exercises ->
            exercises.mapIndexed { index, exercise ->
                when (index) {
                    exerciseIndex -> exercise.copy(sets = exercise.sets + EditTrainingSetUiState())
                    else -> exercise
                }
            }
        }
    }

    fun onRemoveSetClicked(exerciseIndex: Int, setIndex: Int) = viewModelScope.launch {
        _exercises.update { exercises ->
            exercises.mapIndexed { i, exercise ->
                when (i) {
                    exerciseIndex -> {
                        val newSets = exercise.sets.filterIndexed { j, _ -> j != setIndex }
                        exercise.copy(sets = newSets)
                    }

                    else -> exercise
                }
            }
        }
    }

    fun onSetUpdated(exerciseIndex: Int, setIndex: Int, newReps: String, newWeight: String) =
        viewModelScope.launch {
            _exercises.update { exercises ->
                exercises.mapIndexed { i, exercise ->
                    when (i) {
                        exerciseIndex -> {
                            val newSets = exercise.sets.mapIndexed { j, set ->
                                when (j) {
                                    setIndex -> set.copy(reps = newReps, weight = newWeight)
                                    else -> set
                                }
                            }
                            exercise.copy(sets = newSets)
                        }

                        else -> exercise
                    }
                }
            }
        }

    fun onDeleteClicked() = viewModelScope.launch {
        navigateBack()
        deleteTrainingDay(_trainingDayId.value)
    }

    fun onSaveClicked() = viewModelScope.launch {
        val trainingDay = TrainingDay(
            id = _trainingDayId.value,
            name = _trainingDayName.value,
            exercises = _exercises.value.map { it.toModel() }
        )

        updateTrainingDay(trainingDay)
    }

    fun navigateBack() = navigator.navigateBack()

    private fun TrainingSet.toUiState(): EditTrainingSetUiState =
        EditTrainingSetUiState(
            reps = reps.toString(),
            weight = weight.toString(),
        )

    private fun TrainingExercise.toUiState(): EditTrainingExerciseUiState =
        EditTrainingExerciseUiState(
            name = name,
            sets = sets.map { it.toUiState() }
        )

    private fun EditTrainingExerciseUiState.toModel(): TrainingExercise =
        TrainingExercise(
            name = name,
            sets = sets.map { it.toModel() }
        )

    private fun EditTrainingSetUiState.toModel(): TrainingSet =
        TrainingSet(
            reps = reps.toIntOrNull() ?: 0,
            weight = weight.toIntOrNull()?.kg ?: 0.kg
        )
}