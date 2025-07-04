package com.UL_ED5042.project.ui

import com.UL_ED5042.project.Data.Habit

fun lookupConsistencyScore(consistency: String): Double {
    return when (consistency.lowercase()) {
        "poor" -> 1.0
        "fair" -> 2.0
        "good" -> 3.0
        "excellent" -> 4.0
        else -> 0.0
    }
}

fun calculateHabitScore(habits: List<Habit>): String {
    val totalDays = habits.sumOf { it.days }
    val totalScore = habits.sumOf { lookupConsistencyScore(it.consistency) * it.days }
    val score = if (totalDays == 0) 0.0 else totalScore / totalDays
    return String.format("%.2f", score)
}
