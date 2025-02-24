package com.example.workoutplan.domain.model

data class Weight(
    val value: Float,
    val unit: WeightUnit,
) {
    override fun toString(): String {
        return "$value ${unit.name}"
    }
}