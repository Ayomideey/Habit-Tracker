package com.UL_ED5042.project.Data


import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    suspend fun insert(habit: Habit)
    suspend fun update(habit: Habit)
    suspend fun delete(habit: Habit)
    suspend fun getHabitCount(): Int
}

class OfflineHabitRepository(private val habitDao: HabitDao) : HabitRepository {
    override fun getAllHabits() = habitDao.getAllHabits()
    override suspend fun insert(habit: Habit) = habitDao.insert(habit)
    override suspend fun update(habit: Habit) = habitDao.update(habit)
    override suspend fun delete(habit: Habit) = habitDao.delete(habit)
    override suspend fun getHabitCount() = habitDao.getHabitCount()
}