package com.example.workoutplan.ui.trainingsession

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.ui.components.loadingscreen.LoadingScreen
import com.example.workoutplan.ui.trainingsession.components.TrainingExerciseInSessionCard

@Composable
fun TrainingSessionScreen(
    uiState: TrainingSessionUiState,
    onFinishExerciseClicked: (TrainingExercise, Boolean) -> Unit,
    onFinishTrainingClicked: () -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
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
    LazyColumn(state = rememberLazyListState()) {
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