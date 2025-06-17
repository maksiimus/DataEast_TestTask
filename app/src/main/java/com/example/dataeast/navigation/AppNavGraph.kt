package com.example.dataeast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dataeast.ui.CalculatorScreen
import com.example.dataeast.ui.MainScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Calculator : Screen("calculator")
    object Task2 : Screen("task2")
    object Task3 : Screen("task3")
    object Task4 : Screen("task4")
    object Task5 : Screen("task5")
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Calculator.route) { CalculatorScreen() }
    }
}
