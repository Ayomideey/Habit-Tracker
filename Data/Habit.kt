package com.UL_ED5042.project.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey val name: String,
    val description: String,
    val consistency: String,
    val days: Int
)