package com.UL_ED5042.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.UL_ED5042.project.Data.Habit
import com.UL_ED5042.project.HabitScreenRoute
import com.UL_ED5042.project.ui.HabitViewModel
import com.UL_ED5042.project.ui.screens.HabitAppBar
import kotlinx.coroutines.launch
import com.UL_ED5042.project.ui.theme.ForestGreen
import com.UL_ED5042.project.ui.theme.Cream

@Composable
fun HabitBox(
    habit: Habit,
    viewModel: HabitViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf(habit.name) }
    var desc by remember { mutableStateOf(habit.description) }
    var consistency by remember { mutableStateOf(habit.consistency) }
    var days by remember { mutableStateOf(habit.days.toString()) }

    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()


    Column(
            modifier = Modifier
               // .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize()
                //.background(Cream)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           /** Text(
                text = "Habit Form",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 20.dp)
            )*/

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEAEAEA),
                    unfocusedContainerColor = Color(0xFFEAEAEA)
                )
            )

            TextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Description", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEAEAEA),
                    unfocusedContainerColor = Color(0xFFEAEAEA)
                )
            )

            TextField(
                value = consistency,
                onValueChange = { consistency = it },
                label = { Text("Consistency (Poor/Good/etc)", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEAEAEA),
                    unfocusedContainerColor = Color(0xFFEAEAEA)
                )
            )

            TextField(
                value = days,
                onValueChange = { days = it },
                label = { Text("Days Practiced", fontSize = 18.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFEAEAEA),
                    unfocusedContainerColor = Color(0xFFEAEAEA)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    println(" Save button CLICKED")
                    val updated = Habit(name, desc, consistency, days.toIntOrNull() ?: 0)
                    println("Habit to save: $updated")
                    if (viewModel.isHabitValid(updated)) {
                        println("Habit is valid, proceeding to save")
                        viewModel.updateState(updated)
                        scope.launch {
                            if (habit.name.isBlank()) {
                                println(" Adding new habit")
                                viewModel.addHabit()
                            } else {
                                println(" Updating existing habit")
                                viewModel.updateHabit()
                            }
                            println("Save button clicked")
                            println("Navigating to: ${HabitScreenRoute.HabitList.name}")

                            navController.navigate(HabitScreenRoute.HabitList.name)

                        }
                    } else {
                        println("Invalid habit — not saving or navigating")
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ForestGreen) // ✅ Forest Green
            ) {
                Text("Save Habit ", fontSize = 18.sp, color = Color.White)
            }


            if (habit.name.isNotBlank()) {
                Button(
                    onClick = {
                        showDialog = true
                        /**scope.launch {
                            viewModel.deleteHabit()
                            navController.popBackStack()
                        }*/
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Delete Habit", fontSize = 18.sp)
                }
            }
        }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Habit") },
            text = { Text("Are you sure you want to delete this habit?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        scope.launch {
                            viewModel.deleteHabit()
                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = ForestGreen)
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel")
                }
            },
            containerColor = Color(0xFFEAEAEA) // Optional: match input box color
        )
    }
    }

