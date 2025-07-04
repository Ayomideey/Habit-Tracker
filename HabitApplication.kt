package com.UL_ED5042.project

import android.app.Application
import com.UL_ED5042.project.Data.Habit
import com.UL_ED5042.project.Data.HabitDatabase
import com.UL_ED5042.project.Data.HabitRepository
import com.UL_ED5042.project.Data.OfflineHabitRepository


class HabitApplication : Application() {

    //This must match what the ViewModel Factory expects
    lateinit var container: HabitRepository

    override fun onCreate() {
        super.onCreate()
        val database = HabitDatabase.getDatabase(this)
        container = OfflineHabitRepository(database.habitDao())
    }
}