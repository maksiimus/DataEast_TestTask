package com.example.dataeast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.search.*


@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey("3a7d15d1-67ef-44b1-a8af-a3b6aa65ec7d")
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            val items = listOf(
                DrawerItem("Калькулятор", Screen.Calculator.route),
                DrawerItem("Геокодер", Screen.Geocoder.route),
                DrawerItem("Кластеризация", Screen.Clastering.route),
                DrawerItem("Полигоны", Screen.Polygon.route),
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

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop(){
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
