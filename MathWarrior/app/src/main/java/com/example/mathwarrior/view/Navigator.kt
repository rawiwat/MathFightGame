package com.example.mathwarrior.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigator(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Main_Menu"
    ) {
        composable()
    }
}