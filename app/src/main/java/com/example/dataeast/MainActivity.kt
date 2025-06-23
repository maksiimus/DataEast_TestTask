package com.example.dataeast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.dataeast.common.navigation.AppNavGraph
import com.example.dataeast.Calculator.ui.components.DrawerContent
import com.example.dataeast.common.navigation.Screen
import com.example.dataeast.Calculator.model.DrawerItem
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.search.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("3a7d15d1-67ef-44b1-a8af-a3b6aa65ec7d")
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val items = listOf(
                DrawerItem("Калькулятор", Screen.Calculator.route),
                DrawerItem("Геокодер", Screen.Geocoder.route),
                DrawerItem("Кластеризация", Screen.Clastering.route),
                DrawerItem("Полигоны", Screen.Polygon.route),
                DrawerItem("Векторизатор", Screen.Vectorizer.route),

            )

            val screenTitles = mapOf(
                Screen.Calculator.route to "Калькулятор",
                Screen.Geocoder.route to "Геокодер",
                Screen.Clastering.route to "Кластеризация",
                Screen.Polygon.route to "Полигональные операции",
                Screen.Vectorizer.route to "Векторизатор",
            )

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerContainerColor = Color.White
                    ) {
                        Column {
                            Text(
                                text = "Меню",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            DrawerContent(items) {
                                navController.navigate(it.route)
                                scope.launch { drawerState.close() }
                            }
                        }
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {Text(screenTitles[currentRoute]?: "DataEast")},
                            navigationIcon = {
                                IconButton (onClick = {
                                    scope.launch {drawerState.open() }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    }
                ) {
                    padding ->
                    AppNavGraph(navController = navController,
                        modifier = Modifier.padding(padding))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop(){
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
