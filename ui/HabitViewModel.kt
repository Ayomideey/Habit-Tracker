package com.UL_ED5042.project.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.UL_ED5042.project.Data.Habit
import com.UL_ED5042.project.Data.HabitRepository

data class HabitUiState(
    val habits: List<Habit> = emptyList(),
    val currentHabit: Habit = Habit("", "", "", 0)
)

class HabitViewModel(private val repo: HabitRepository) : ViewModel() {
    val uiState = MutableStateFlow(HabitUiState())

    fun init() {
        viewModelScope.launch {
            repo.getAllHabits().collect {
                uiState.value = uiState.value.copy(habits = it)
            }
        }
    }

    fun updateState(habit: Habit) {
        uiState.value = uiState.value.copy(currentHabit = habit)
    }

    suspend fun addHabit() = repo.insert(uiState.value.currentHabit)
    suspend fun updateHabit() = repo.update(uiState.value.currentHabit)
    suspend fun deleteHabit() = repo.delete(uiState.value.currentHabit)

    fun isHabitValid(habit: Habit): Boolean {
        return habit.name.isNotBlank()
                && habit.days > 0
                && habit.consistency.trim().lowercase() in listOf("poor", "fair", "good", "excellent")
    }
}