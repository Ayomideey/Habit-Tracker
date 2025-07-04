package com.UL_ED5042.project.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
//import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.UL_ED5042.project.Data.Habit
import com.UL_ED5042.project.HabitScreenRoute
import com.UL_ED5042.project.ui.HabitViewModel
import com.UL_ED5042.project.ui.calculateHabitScore
import com.UL_ED5042.project.ui.theme.Cream
import com.UL_ED5042.project.ui.theme.ForestGreen



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitAppBar(
    title: String,
    canNavigateBack: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = if (canNavigateBack) 48.dp else 0.dp) // makes room for back icon
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp, color = Color.White),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        ,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = ForestGreen // optional: gives soft blue bg
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },

        modifier = modifier
    )
}


@Composable
fun HabitScreen(viewModel: HabitViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()
    val habitList = uiState.habits
    println("HabitScreen recomposed")

    val score = calculateHabitScore(habitList)


    Column(
            modifier = Modifier
                //.padding(innerPadding)
                .padding(24.dp)
                //.background(Cream)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // You can remove this if using the AppBar
            // Text("Habit Tracker", style = MaterialTheme.typography.headlineLarge)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {/**
                Text("Habit", Modifier.weight(5f), style = MaterialTheme.typography.labelLarge)
                Text("Consistency", Modifier.weight(5f), style = MaterialTheme.typography.labelLarge)
                Text("Days", Modifier.weight(5f), style = MaterialTheme.typography.labelLarge)*/
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(habitList) { habit ->
                    HabitRow(habit = habit, onClick = {
                        viewModel.updateState(habit)
                        navController.navigate(HabitScreenRoute.HabitForm.name)
                    })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Your Habit Score:", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.width(10.dp))
                Text(score, style = MaterialTheme.typography.headlineSmall)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    viewModel.updateState(Habit("", "", "", 0))
                    navController.navigate(HabitScreenRoute.HabitForm.name)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen, // Forest Green
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Habit", fontSize = 18.sp)
            }
        }
    }



@Composable
fun HabitRow(habit: Habit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEAEAEA)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habit.name,
                modifier = Modifier.weight(5f)
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp
                    //fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = habit.consistency,
                modifier = Modifier.weight(5f)
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp
                   // fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = habit.days.toString(),
                modifier = Modifier.weight(5f)
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp
                    //fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


