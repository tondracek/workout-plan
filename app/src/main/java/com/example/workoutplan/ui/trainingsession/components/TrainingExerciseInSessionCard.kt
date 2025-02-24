package com.example.workoutplan.ui.trainingsession.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutplan.data.repository.kg
import com.example.workoutplan.domain.model.TrainingExercise
import com.example.workoutplan.domain.model.TrainingSet
import com.example.workoutplan.ui.theme.AppTheme

@Composable
internal fun TrainingExerciseInSessionCard(
    modifier: Modifier = Modifier,
    exercise: TrainingExercise,
    finished: Boolean,
    onFinishExerciseClicked: (Boolean) -> Unit,
) {
    var collapsed by remember { mutableStateOf(false) }

    Card(modifier = modifier, onClick = { collapsed = !collapsed }) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = exercise.name)

                Checkbox(
                    checked = finished,
                    onCheckedChange = {
                        onFinishExerciseClicked(it)
                        collapsed = it
                    }
                )
            }

            AnimatedVisibility(visible = !collapsed) {
                Column() {
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp))

                    exercise.sets.forEach { trainingSet ->
                        TrainingSetItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            trainingSet = trainingSet
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun TrainingSetItem(
    modifier: Modifier = Modifier,
    trainingSet: TrainingSet,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = "${trainingSet.reps}×")
        Text(text = "${trainingSet.weight}")
    }
}


@Preview
@Composable
private fun TrainingExerciseInSessionCardPreview() {
    var finished by remember { mutableStateOf(false) }

    AppTheme {
        TrainingExerciseInSessionCard(
            exercise = TrainingExercise(
                name = "Lateral raises",
                sets = listOf(
                    TrainingSet(10, 10.kg),
                    TrainingSet(10, 8.kg),
                    TrainingSet(10, 7.kg),
                )
            ),
            finished = finished,
            onFinishExerciseClicked = { finished = it }
        )
    }
}
