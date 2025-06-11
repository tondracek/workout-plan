package com.tondracek.workoutplan.ui.screen.trainingmenu

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tondracek.workoutplan.db.entity.TrainingDayId
import com.tondracek.workoutplan.ui.screen.components.loadingscreen.LoadingScreen
import com.tondracek.workoutplan.ui.screen.trainingmenu.components.TrainingDayCard
import com.tondracek.workoutplan.ui.theme.AppTheme

@Composable
fun TrainingMenuScreen(
    uiState: TrainingMenuUiState,
    onTrainingDaySelected: (TrainingDayId) -> Unit,
    onCreateTrainingDayClicked: () -> Unit = {},
    onEditTrainingDayClicked: (TrainingDayId) -> Unit = {},
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
                        onCreateTrainingDayClicked = onCreateTrainingDayClicked,
                        onTrainingDaySelected = onTrainingDaySelected,
                        onEditTrainingDayClicked = onEditTrainingDayClicked,
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
    onCreateTrainingDayClicked: () -> Unit,
    onTrainingDaySelected: (TrainingDayId) -> Unit,
    onEditTrainingDayClicked: (TrainingDayId) -> Unit = {},
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(32.dp)) {
        itemsIndexed(uiState.trainings) { index, trainingDayUiState ->
            TrainingDayCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                uiState = trainingDayUiState,
                isCurrent = index == uiState.currentTrainingDayIndex,
                onTrainingDaySelected = { onTrainingDaySelected(trainingDayUiState.id) },
                onTrainingDayLongPressed = { onEditTrainingDayClicked(trainingDayUiState.id) }
            )
        }

        item {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                onClick = onCreateTrainingDayClicked,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = "Create new training day",
                )
            }
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
                        finishedCount = 3,
                        totalExercises = 5,
                        totalSets = 22,
                    ),
                    TrainingDayUiState(
                        id = 0,
                        name = "Bench + Arms",
                        finishedCount = 4,
                        totalExercises = 6,
                        totalSets = 30,
                    ),
                    TrainingDayUiState(
                        id = 0,
                        name = "Deadlift + Accessory",
                        finishedCount = 2,
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
private fun SuccessPreviewEmpty() {
    AppTheme {
        TrainingMenuScreen(
            uiState = TrainingMenuUiState.Success(
                trainings = emptyList(),
                currentTrainingDayIndex = 0,
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