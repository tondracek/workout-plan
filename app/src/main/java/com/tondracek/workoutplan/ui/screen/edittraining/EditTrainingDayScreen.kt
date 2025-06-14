package com.tondracek.workoutplan.ui.screen.edittraining

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tondracek.workoutplan.ui.screen.components.loadingscreen.LoadingScreen
import com.tondracek.workoutplan.ui.theme.AppTheme

@Composable
fun EditTrainingDayScreen(
    uiState: EditTrainingDayUiState,
    onNameChanged: (String) -> Unit,
    onAddExerciseClicked: () -> Unit,
    onRemoveExerciseClicked: (Int) -> Unit,
    onExerciseNameChanged: (Int, String) -> Unit,
    onAddSetClicked: (Int) -> Unit,
    onRemoveSetClicked: (Int, Int) -> Unit,
    onSetUpdated: (Int, Int, String, String) -> Unit,
    onMoveSoonerInPlanClicked: () -> Unit,
    onMoveLaterInPlanClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onNavigateBack: () -> Unit
) {

    when (uiState) {
        is EditTrainingDayUiState.Success -> SuccessScreen(
            uiState = uiState,
            onNameChanged = onNameChanged,
            onExerciseNameChanged = onExerciseNameChanged,
            onSetUpdated = onSetUpdated,
            onRemoveSetClicked = onRemoveSetClicked,
            onAddSetClicked = onAddSetClicked,
            onRemoveExerciseClicked = onRemoveExerciseClicked,
            onAddExerciseClicked = onAddExerciseClicked,
            onMoveSoonerInPlanClicked = onMoveSoonerInPlanClicked,
            onMoveLaterInPlanClicked = onMoveLaterInPlanClicked,
            onDeleteClicked = onDeleteClicked,
            onSaveClicked = onSaveClicked,
            onNavigateBack = onNavigateBack,
        )

        else -> LoadingScaffoldScreen(
            onNavigateBack = onNavigateBack,
            onSaveClicked = onSaveClicked
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LoadingScaffoldScreen(onNavigateBack: () -> Unit, onSaveClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Training Day") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSaveClicked) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { paddingValues ->
        LoadingScreen(modifier = Modifier.padding(paddingValues))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessScreen(
    uiState: EditTrainingDayUiState.Success,
    onNameChanged: (String) -> Unit,
    onExerciseNameChanged: (Int, String) -> Unit,
    onSetUpdated: (Int, Int, String, String) -> Unit,
    onRemoveSetClicked: (Int, Int) -> Unit,
    onAddSetClicked: (Int) -> Unit,
    onRemoveExerciseClicked: (Int) -> Unit,
    onAddExerciseClicked: () -> Unit,
    onMoveSoonerInPlanClicked: () -> Unit,
    onMoveLaterInPlanClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Text(text = uiState.orderInPlan)
                    IconButton(onClick = onMoveSoonerInPlanClicked) {
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = "Move sooner in plan"
                        )
                    }
                    IconButton(onClick = onMoveLaterInPlanClicked) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "Move later in plan"
                        )
                    }
                    IconButton(onClick = onDeleteClicked) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = { onSaveClicked(); onNavigateBack() }) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                onClick = onAddExerciseClicked,
            ) {
                Text("Add Exercise")
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = onNameChanged,
                    label = { Text("Training Name") },
                    singleLine = true,
                )
            }

            itemsIndexed(uiState.exercises) { exerciseIndex, exercise ->
                ExerciseField(
                    exercise,
                    onExerciseNameChanged,
                    exerciseIndex,
                    onSetUpdated,
                    onRemoveSetClicked,
                    onAddSetClicked,
                    onRemoveExerciseClicked,
                    onSaveClicked,
                )
            }
        }
    }
}

@Composable
private fun ExerciseField(
    exercise: EditTrainingExerciseUiState,
    onExerciseNameChanged: (Int, String) -> Unit,
    exerciseIndex: Int,
    onSetUpdated: (Int, Int, String, String) -> Unit,
    onRemoveSetClicked: (Int, Int) -> Unit,
    onAddSetClicked: (Int) -> Unit,
    onRemoveExerciseClicked: (Int) -> Unit,
    onSaveClicked: () -> Unit
) {
    var collapsed by rememberSaveable { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {

        Column(Modifier.padding(12.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = exercise.name,
                    onValueChange = { onExerciseNameChanged(exerciseIndex, it) },
                    label = { Text("Exercise Name") },
                    singleLine = true,
                )
                IconButton(
                    onClick = {
                        collapsed = !collapsed
                        onSaveClicked()
                    }
                ) {
                    Icon(
                        imageVector = when (collapsed) {
                            true -> Icons.Default.KeyboardArrowDown
                            false -> Icons.Default.KeyboardArrowUp
                        },
                        contentDescription = "Collapse/Expand"
                    )
                }
            }

            AnimatedContent(
                targetState = collapsed,
                label = "",
                transitionSpec = {
                    slideInVertically().togetherWith(slideOutVertically() + fadeOut())
                }
            ) { targetState ->
                if (targetState) {
                    StatsRow(exercise)
                } else {
                    SetsColumn(
                        exercise,
                        onSetUpdated,
                        exerciseIndex,
                        onAddSetClicked,
                        onRemoveSetClicked,
                        onRemoveExerciseClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsRow(exercise: EditTrainingExerciseUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        listOf(
            "Sets: ${exercise.totalSets}",
            "Total Reps: ${exercise.totalReps}",
        ).forEach {
            Text(text = it)
        }
    }
}

@Composable
private fun SetsColumn(
    exercise: EditTrainingExerciseUiState,
    onSetUpdated: (Int, Int, String, String) -> Unit,
    exerciseIndex: Int,
    onAddSetClicked: (Int) -> Unit,
    onRemoveSetClicked: (Int, Int) -> Unit,
    onRemoveExerciseClicked: (Int) -> Unit
) {
    var spamSetsMode by remember { mutableStateOf(false) }

    Column {
        exercise.sets.forEachIndexed { setIndex, set ->
            val repsFocusRequester = remember { FocusRequester() }
            val weightFocusRequester = remember { FocusRequester() }
            val keyboardController = LocalSoftwareKeyboardController.current

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(repsFocusRequester),
                    value = set.reps,
                    onValueChange = {
                        onSetUpdated(exerciseIndex, setIndex, it, set.weight)
                    },
                    label = { Text("Reps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        weightFocusRequester.requestFocus()
                        spamSetsMode = true
                    }),
                    singleLine = true,
                )

                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(weightFocusRequester)
                        .onFocusChanged { focusState ->
                            if (!focusState.isFocused) spamSetsMode = false
                        },
                    value = set.weight,
                    onValueChange = {
                        onSetUpdated(exerciseIndex, setIndex, set.reps, it)
                    },
                    label = { Text("Weight") },
                    trailingIcon = { Text("kg") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        if (spamSetsMode) onAddSetClicked(exerciseIndex)
                    }),
                    singleLine = true,
                )

                IconButton(onClick = {
                    onRemoveSetClicked(
                        exerciseIndex,
                        setIndex
                    )
                }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remove Set"
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { onAddSetClicked(exerciseIndex) }) {
                Text("Add Set")
            }
            TextButton(onClick = { onRemoveExerciseClicked(exerciseIndex) }) {
                Text("Remove Exercise")
            }
        }
    }
}

@Preview
@Composable
private fun EditTrainingDayScreenPreview() {
    AppTheme {
        EditTrainingDayScreen(
            uiState = EditTrainingDayUiState.Success(
                name = "Test",
                exercises = List(10) {
                    EditTrainingExerciseUiState(
                        name = "test1",
                        sets = List(5) { EditTrainingSetUiState() },
                    )
                },
                orderInPlan = "1/5"
            ),
            onNameChanged = {},
            onAddExerciseClicked = {},
            onRemoveExerciseClicked = {},
            onExerciseNameChanged = { _, _ -> },
            onAddSetClicked = {},
            onRemoveSetClicked = { _, _ -> },
            onSetUpdated = { _, _, _, _ -> },
            onMoveSoonerInPlanClicked = {},
            onMoveLaterInPlanClicked = {},
            onDeleteClicked = {},
            onSaveClicked = {},
            onNavigateBack = {},
        )
    }
}