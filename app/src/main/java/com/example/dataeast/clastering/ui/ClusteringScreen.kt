package com.example.dataeast.clastering.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dataeast.clastering.viewmodel.ClusteringViewModel
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset

@Composable
fun ClusteringScreen() {
    val context = LocalContext.current
    val viewModel: ClusteringViewModel = viewModel()

    val clusters by viewModel.clusters.collectAsState()

    val bitmap = remember {
        val inputStream = context.assets.open("cluster_test_image.jpg")
        BitmapFactory.decodeStream(inputStream)
    }

    var epsilon by remember { mutableStateOf(800.0) }

    Column (Modifier.fillMaxSize().padding(16.dp)){
        Text("Порог близости (epsilon): ${epsilon.toInt()}")
        Slider(
            value = epsilon.toFloat(),
            onValueChange = {epsilon = it.toDouble()},
            valueRange = 5f..800f
        )
        Button(onClick = {viewModel.clusterize(epsilon)}) {
            Text("Выполнить кластеризацию")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(bitmap.width/bitmap.height.toFloat())
        ) {
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Растр")

            val color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)

            Canvas(modifier = Modifier.fillMaxSize()) {
                clusters.forEachIndexed { i, cluster ->

                    cluster.points.forEach { point ->
                        drawCircle(
                            color = color,
                            radius = 6f,
                            center = Offset(point.x.toFloat(), point.y.toFloat())
                        )
                    }
                }
            }
        }
    }
}