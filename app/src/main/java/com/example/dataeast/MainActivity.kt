package com.example.dataeast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.dataeast.Calculator.navigation.AppNavGraph
import com.example.dataeast.Calculator.ui.components.DrawerContent
import com.example.dataeast.Calculator.navigation.Screen
import com.example.dataeast.Calculator.model.DrawerItem
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val items = listOf(
                DrawerItem("Калькулятор", Screen.Calculator.route),
                DrawerItem("Задание 2", Screen.Task2.route),
                DrawerItem("Задание 3", Screen.Task3.route),
                DrawerItem("Задание 4", Screen.Task4.route),
                DrawerItem("Задание 5", Screen.Task5.route),

            )

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerContent(items) {
                        navController.navigate(it.route)
                        scope.launch { drawerState.close()}
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {Text("DataEast")},
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
}
