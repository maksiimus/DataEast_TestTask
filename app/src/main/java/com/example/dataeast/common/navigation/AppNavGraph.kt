package com.example.dataeast.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dataeast.Calculator.ui.CalculatorScreen
import com.example.dataeast.common.ui.MainScreen
import com.example.dataeast.Geocoder.ui.GeocoderScreen
import com.example.dataeast.clastering.ui.ClusteringScreen
import androidx.compose.ui.unit.dp
import com.example.dataeast.polygon.ui.PolygonScreen
import com.example.vectorizer.VectorizerScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Calculator : Screen("calculator")
    object Geocoder : Screen("geocoder")
    object Clastering : Screen("clastering")
    object Polygon : Screen("polygon")
    object Vectorizer : Screen("vectorizer")
}


@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = Screen.Main.route, modifier = modifier) {
        composable(Screen.Main.route) { MainScreen(navController) }
        composable(Screen.Calculator.route) { CalculatorScreen() }
        composable(Screen.Geocoder.route) { GeocoderScreen(padding = PaddingValues(0.dp)) }
        composable(Screen.Clastering.route) { ClusteringScreen() }
        composable(Screen.Polygon.route) { PolygonScreen()  }
        composable(Screen.Vectorizer.route) { VectorizerScreen() }
    }
}
