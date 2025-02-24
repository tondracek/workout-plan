package com.example.workoutplan.ui.trainingsession

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.ui.components.loadingscreen.LoadingScreen
import com.example.workoutplan.ui.trainingsession.components.TrainingExerciseInSessionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingSessionScreen(
    uiState: TrainingSessionUiState,
    navigateBack: () -> Unit,
    onFinishExerciseClicked: (TrainingExercise, Boolean) -> Unit,
    onFinishTrainingClicked: () -> Unit,
) {
    BottomSheetScaffold(
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
                }
            )
        },
        sheetContent = {
            Button(onClick = onFinishTrainingClicked) {
                Text("Finish training")
            }
        }
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
                )
            }
        }
    }
}

@Composable
private fun SuccessScreen(
    uiState: TrainingSessionUiState.Success,
    onFinishExerciseClicked: (TrainingExercise, Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = rememberLazyListState()
    ) {
        items(uiState.exercises) { (item, finished) ->
            TrainingExerciseInSessionCard(
                modifier = Modifier.padding(16.dp),
                exercise = item,
                finished = finished,
                onFinishExerciseClicked = { onFinishExerciseClicked(item, it) }
            )
        }
    }
}