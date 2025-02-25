package com.example.workoutplan.ui.trainingmenu

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutplan.data.common.entity.TrainingDayId
import com.example.workoutplan.ui.components.loadingscreen.LoadingScreen
import com.example.workoutplan.ui.theme.AppTheme
import com.example.workoutplan.ui.trainingmenu.components.TrainingDayCard

@Composable
fun TrainingMenuScreen(
    uiState: TrainingMenuUiState,
    onTrainingDaySelected: (TrainingDayId) -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = uiState, label = "") { targetState: TrainingMenuUiState ->
                when (targetState) {
                    is TrainingMenuUiState.Success -> SuccessScreen(
                        uiState = targetState,
                        onTrainingDaySelected = onTrainingDaySelected
                    )

                    TrainingMenuUiState.Loading -> LoadingScreen()
                }
            }
        }
    }
}

@Composable
private fun SuccessScreen(
    uiState: TrainingMenuUiState.Success,
    onTrainingDaySelected: (TrainingDayId) -> Unit,
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        itemsIndexed(uiState.trainings) { index, trainingDayUiState ->
            TrainingDayCard(
                modifier = Modifier.padding(horizontal = 32.dp),
                uiState = trainingDayUiState,
                isCurrent = index == uiState.currentTrainingDayIndex,
                onTrainingDaySelected = onTrainingDaySelected
            )
        }
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    AppTheme {
        TrainingMenuScreen(
            uiState = TrainingMenuUiState.Success(
                trainings = listOf(
                    TrainingDayUiState(
                        id = 0,
                        name = "Heavy Lower",
                        totalExercises = 5,
                        totalSets = 22,
                    ),
                    TrainingDayUiState(
                        id = 0,
                        name = "Bench + Arms",
                        totalExercises = 6,
                        totalSets = 30,
                    ),
                    TrainingDayUiState(
                        id = 0,
                        name = "Deadlift + Accessory",
                        totalExercises = 4,
                        totalSets = 20,
                    ),
                ),
                currentTrainingDayIndex = 1,
            ),
            onTrainingDaySelected = {},
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    AppTheme {
        TrainingMenuScreen(
            uiState = TrainingMenuUiState.Loading,
            onTrainingDaySelected = {}
        )
    }
}