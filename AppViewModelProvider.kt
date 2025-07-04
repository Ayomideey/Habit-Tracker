package com.UL_ED5042.project

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.UL_ED5042.project.ui.HabitViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HabitViewModel(
                habitApplication().container
            )
        }
    }
}

fun CreationExtras.habitApplication(): HabitApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as HabitApplication)