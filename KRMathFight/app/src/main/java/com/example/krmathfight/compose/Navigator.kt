package com.example.krmathfight.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.krmathfight.viewModel.CombatViewModel

@Composable
fun Navigator(
    navController: NavHostController,
    combatViewModel: CombatViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "Main"
    ) {
       composable(route = "Main") {
           HomeScreen(navController = navController)
       }
       composable(route = "Combat") {
           CombatScreen(viewModel = combatViewModel)
       }
    }
}