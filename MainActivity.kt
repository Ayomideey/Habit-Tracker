package com.UL_ED5042.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.UL_ED5042.project.ui.HabitViewModel
import com.UL_ED5042.project.ui.screens.HabitBox
import com.UL_ED5042.project.ui.screens.HabitScreen
import com.UL_ED5042.project.ui.screens.HabitAppBar
import com.UL_ED5042.project.ui.theme.HabittrackTheme
import com.UL_ED5042.project.ui.theme.Cream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabittrackTheme {
                Surface( //  Apply background to entire screen
                    modifier = Modifier.fillMaxSize(),
                    color = Cream
                ){
                    HabitApp()
                }

            }
        }
    }
}

@Composable
fun HabitApp(
    viewModel: HabitViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.init()
    }

    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    // 🔹 This was missing — determine current screen based on route
    val currentScreen = HabitScreenRoute.valueOf(
        backStackEntry?.destination?.route ?: HabitScreenRoute.HabitList.name
    )

    Scaffold(
        topBar = {
            HabitAppBar(
                title = when (currentScreen) {
                    HabitScreenRoute.HabitList -> "Habit Tracker"
                    HabitScreenRoute.HabitForm -> "Edit Habit"
                },
                canNavigateBack = currentScreen != HabitScreenRoute.HabitList,
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = Cream,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HabitScreenRoute.HabitList.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HabitScreenRoute.HabitList.name) {
                HabitScreen(viewModel = viewModel, navController = navController)
            }
            composable(HabitScreenRoute.HabitForm.name) {
                HabitBox(
                    habit = uiState.currentHabit,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}

enum class HabitScreenRoute {
    HabitList,
    HabitForm
}
