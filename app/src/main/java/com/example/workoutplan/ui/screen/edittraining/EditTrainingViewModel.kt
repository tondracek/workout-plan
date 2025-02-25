package com.example.workoutplan.ui.screen.edittraining

import androidx.lifecycle.ViewModel
import com.example.workoutplan.domain.usecase.GetTrainingDayByID
import javax.inject.Inject

class EditTrainingViewModel @Inject constructor(
    private val getTrainingDayByID: GetTrainingDayByID,
) : ViewModel()