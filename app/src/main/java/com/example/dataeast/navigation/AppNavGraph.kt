package com.example.dataeast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dataeast.ui.CalculatorScreen
import com.example.dataeast.ui.MainScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Calculator : Screen("calculator")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Calculator.route) { CalculatorScreen() }
    }
}
