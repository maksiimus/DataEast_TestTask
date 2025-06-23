package com.example.dataeast.polygon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dataeast.polygon.model.PolygonOperation
import com.example.dataeast.polygon.viewmodel.PolygonViewModel
import androidx.compose.ui.graphics.Color
@Composable
fun PolygonScreen(viewModel: PolygonViewModel = viewModel()) {
    var step by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Полигон A:")
        if (!viewModel.isPolygonADrawn) {
            DrawArea { points ->
                viewModel.setPolygonA(points)
                step = 2
            }
        } else {
            Box {
                DrawStaticPolygon(viewModel.polygonA!!.points, Color.Red)
                Button(
                    onClick = { viewModel.clearPolygonA(); step = 1 },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Стереть")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (step >= 2) {
            Text("Полигон B:")
            if (!viewModel.isPolygonBDrawn) {
                DrawArea { points ->
                    viewModel.setPolygonB(points)
                    step = 3
                }
            } else {
                Box {
                    DrawStaticPolygon(viewModel.polygonB!!.points, Color.Blue)
                    Button(
                        onClick = { viewModel.clearPolygonB(); step = 2 },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Стереть")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (step >= 3) {
            var expanded by remember { mutableStateOf(false) }

            Box {
                Button(onClick = { expanded = true }) {
                    Text("Операция: ${viewModel.operation}")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    PolygonOperation.values().forEach {
                        DropdownMenuItem(
                            text = { Text(it.label) },
                            onClick = {
                                viewModel.updateOperation(it)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { viewModel.applyOperation() }) {
                Text("Выполнить")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Результат:")
            val result = viewModel.result
            if (result != null && result.points.isNotEmpty()) {
                DrawStaticPolygon(result.points, Color.Green)
            } else {
                Text("Результат пуст или операция не удалась.")
            }
        }
    }
}
