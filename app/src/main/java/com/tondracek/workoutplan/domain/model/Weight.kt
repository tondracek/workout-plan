package com.tondracek.workoutplan.domain.model

data class Weight(
    val value: Float,
    val unit: WeightUnit,
) : Comparable<Weight> {
    override fun compareTo(other: Weight): Int {
        return value.compareTo(other.value)
    }

    override fun toString(): String {
        return "$value ${unit.name}"
    }

    operator fun plus(other: Weight): Weight {
        return Weight(value + other.value, unit)
    }

    operator fun minus(other: Weight): Weight {
        return Weight(value - other.value, unit)
    }
}