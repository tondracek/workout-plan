package com.example.workoutplan.ui.screen.edittraining

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutplan.ui.screen.components.loadingscreen.LoadingScreen
import com.example.workoutplan.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
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
    onDeleteClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onNavigateBack: () -> Unit
) {

    when (uiState) {
        is EditTrainingDayUiState.Success ->
            SuccessScreen(
                uiState = uiState,
                onNameChanged = onNameChanged,
                onExerciseNameChanged = onExerciseNameChanged,
                onSetUpdated = onSetUpdated,
                onRemoveSetClicked = onRemoveSetClicked,
                onAddSetClicked = onAddSetClicked,
                onRemoveExerciseClicked = onRemoveExerciseClicked,
                onAddExerciseClicked = onAddExerciseClicked,
                onDeleteClicked = onDeleteClicked,
                onSaveClicked = onSaveClicked,
                onNavigateBack = onNavigateBack,
            )

        else ->
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
    onDeleteClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    OutlinedTextField(
                        value = uiState.name,
                        onValueChange = onNameChanged,
                        label = { Text("Training Name") },
                        singleLine = true,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onDeleteClicked) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                    IconButton(onClick = onSaveClicked) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
                onClick = onAddExerciseClicked,
            ) {
                Text("Add Exercise")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            itemsIndexed(uiState.exercises) { exerciseIndex, exercise ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        OutlinedTextField(
                            value = exercise.name,
                            onValueChange = { onExerciseNameChanged(exerciseIndex, it) },
                            label = { Text("Exercise Name") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        exercise.sets.forEachIndexed { setIndex, set ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = set.reps,
                                    onValueChange = {
                                        onSetUpdated(exerciseIndex, setIndex, it, set.weight)
                                    },
                                    label = { Text("Reps") },
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedTextField(
                                    value = set.weight,
                                    onValueChange = {
                                        onSetUpdated(exerciseIndex, setIndex, set.reps, it)
                                    },
                                    label = { Text("Weight") },
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                )
                                IconButton(onClick = {
                                    onRemoveSetClicked(exerciseIndex, setIndex)
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
                        sets = List(5) { EditTrainingSetUiState() }
                    )
                }
            ),
            onNameChanged = {},
            onAddExerciseClicked = {},
            onRemoveExerciseClicked = {},
            onExerciseNameChanged = { _, _ -> },
            onAddSetClicked = {},
            onRemoveSetClicked = { _, _ -> },
            onSetUpdated = { _, _, _, _ -> },
            onDeleteClicked = {},
            onSaveClicked = {},
            onNavigateBack = {},
        )
    }
}