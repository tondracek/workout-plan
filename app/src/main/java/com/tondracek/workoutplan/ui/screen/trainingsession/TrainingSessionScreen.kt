package com.tondracek.workoutplan.ui.screen.trainingsession

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tondracek.workoutplan.data.trainingplan.fake.kg
import com.tondracek.workoutplan.db.entity.TrainingExerciseId
import com.tondracek.workoutplan.domain.model.TrainingExercise
import com.tondracek.workoutplan.domain.model.TrainingSet
import com.tondracek.workoutplan.ui.screen.components.loadingscreen.LoadingScreen
import com.tondracek.workoutplan.ui.screen.trainingsession.components.TrainingExerciseInSessionCard
import com.tondracek.workoutplan.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingSessionScreen(
    uiState: TrainingSessionUiState,
    navigateBack: () -> Unit,
    onEditTrainingClicked: () -> Unit,
    onFinishExerciseClicked: (TrainingExerciseId, Boolean) -> Unit,
    onFinishTrainingClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (uiState) {
                        is TrainingSessionUiState.Success -> Text(uiState.trainingName)
                        is TrainingSessionUiState.Loading -> Text("Loading...")
                        is TrainingSessionUiState.Error -> Text("Error")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditTrainingClicked) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Training"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            when (uiState) {
                TrainingSessionUiState.Error -> Text("error")
                TrainingSessionUiState.Loading -> LoadingScreen()
                is TrainingSessionUiState.Success -> SuccessScreen(
                    uiState = uiState,
                    onFinishExerciseClicked = onFinishExerciseClicked,
                    onFinishTrainingClicked = onFinishTrainingClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessScreen(
    uiState: TrainingSessionUiState.Success,
    onFinishExerciseClicked: (TrainingExerciseId, Boolean) -> Unit,
    onFinishTrainingClicked: () -> Unit,
) {
    var sheetOpened by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isDone) {
        if (uiState.isDone) sheetOpened = true
    }

    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState()
        ) {
            items(uiState.exercises) { (item, finished) ->
                TrainingExerciseInSessionCard(
                    modifier = Modifier.padding(16.dp),
                    exercise = item,
                    finished = finished,
                    onFinishExerciseClicked = { onFinishExerciseClicked(item.id, it) }
                )
            }
        }

        if (sheetOpened) {
            ModalBottomSheet(onDismissRequest = { sheetOpened = false }) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        sheetOpened = false
                        onFinishTrainingClicked()
                    },
                ) {
                    Text("Finish training")
                }
            }
        }
    }
}

@Preview
@Composable
private fun TrainingSessionScreenSuccessPreview() {
    AppTheme {
        TrainingSessionScreen(
            uiState = TrainingSessionUiState.Success(
                trainingName = "Test",
                exercises = List(10) {
                    TrainingExercise(
                        name = "test1",
                        sets = List(5) { TrainingSet(1, 1.kg) }
                    ) to true
                },
                isDone = true
            ),
            navigateBack = {},
            onEditTrainingClicked = {},
            onFinishExerciseClicked = { _, _ -> },
            onFinishTrainingClicked = {}
        )
    }
}

@Preview
@Composable
private fun TrainingSessionScreenLoadingPreview() {
    AppTheme {
        TrainingSessionScreen(
            uiState = TrainingSessionUiState.Loading,
            navigateBack = {},
            onEditTrainingClicked = {},
            onFinishExerciseClicked = { _, _ -> },
            onFinishTrainingClicked = {}
        )
    }
}

@Preview
@Composable
private fun TrainingSessionScreenErrorPreview() {
    AppTheme {
        TrainingSessionScreen(
            uiState = TrainingSessionUiState.Error,
            navigateBack = {},
            onEditTrainingClicked = {},
            onFinishExerciseClicked = { _, _ -> },
            onFinishTrainingClicked = {}
        )
    }
}
