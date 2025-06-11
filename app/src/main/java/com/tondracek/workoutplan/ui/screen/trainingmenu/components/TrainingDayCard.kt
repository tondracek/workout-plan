package com.tondracek.workoutplan.ui.screen.trainingmenu.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tondracek.workoutplan.ui.screen.trainingmenu.TrainingDayUiState
import com.tondracek.workoutplan.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TrainingDayCard(
    modifier: Modifier = Modifier,
    uiState: TrainingDayUiState,
    isCurrent: Boolean = false,
    onTrainingDaySelected: () -> Unit,
    onTrainingDayLongPressed: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .scale(if (isCurrent) 1f else 0.95f)
            .combinedClickable(
                onClick = onTrainingDaySelected,
                onLongClick = onTrainingDayLongPressed,
            ),
        colors = when (isCurrent) {
            true -> CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            false -> CardDefaults.cardColors()
        },
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = uiState.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Finished: ${uiState.finishedCount}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.size(8.dp))

            Column(horizontalAlignment = AbsoluteAlignment.Right) {
                Text(
                    text = "Total exercises: ${uiState.totalExercises}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Total sets: ${uiState.totalSets}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun TrainingDayCardPreview() {
    AppTheme {
        TrainingDayCard(
            uiState = TrainingDayUiState(
                id = 0,
                name = "Heavy Lower",
                finishedCount = 5,
                totalExercises = 5,
                totalSets = 22,
            ),
            onTrainingDaySelected = {}
        )
    }
}

@Preview
@Composable
private fun LongTextPreview() {
    AppTheme {
        TrainingDayCard(
            uiState = TrainingDayUiState(
                id = 0,
                name = "Bench + Arms + many many many many many many many many many many other thing",
                finishedCount = 3,
                totalExercises = 5,
                totalSets = 22,
            ),
            isCurrent = true,
            onTrainingDaySelected = {},
        )
    }
}