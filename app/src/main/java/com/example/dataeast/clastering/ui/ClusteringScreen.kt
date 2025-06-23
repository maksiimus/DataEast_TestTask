package com.example.dataeast.clastering.ui

import android.graphics.BitmapFactory
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dataeast.clastering.logic.Clusterizer
import com.example.dataeast.clastering.model.RasterPoint
import com.example.dataeast.clastering.viewmodel.ClusteringViewModel
import kotlin.random.Random

@Composable
fun ClusteringScreen() {
    val context = LocalContext.current
    val viewModel: ClusteringViewModel = viewModel()
    val clusters by viewModel.clusters.collectAsState()

    var epsilon by remember { mutableStateOf(50.0) }
    var minPoints by remember { mutableStateOf(2f) }

    val bitmap = remember {
        val stream = context.assets.open("cluster_test.png")
        BitmapFactory.decodeStream(stream)
    }

    val clusterColors = remember(clusters.size) {
        List(clusters.size) {
            Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Порог (epsilon): ${epsilon.toInt()}")
        Slider(value = epsilon.toFloat(), onValueChange = { epsilon = it.toDouble() }, valueRange = 10f..200f)

        Text("Мин. точек: ${minPoints.toInt()}")
        Slider(value = minPoints, onValueChange = { minPoints = it }, valueRange = 1f..10f)

        Button(onClick = { viewModel.clusterize(epsilon, minPoints.toInt()) }) {
            Text("Кластеризовать")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(bitmap.width / bitmap.height.toFloat())) {

            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)

            Canvas(modifier = Modifier.fillMaxSize()) {
                clusters.forEachIndexed { i, cluster ->
                    var colorCluster = clusterColors[i % clusterColors.size]

                    cluster.points.forEach { point ->
                        drawCircle(
                            color = colorCluster,
                            radius = 6f,
                            center = Offset(point.x.toFloat(), point.y.toFloat())
                        )
                    }

                    if (cluster.points.isNotEmpty()) {
                        val center = Clusterizer.computeCentroid(cluster.points)
                        drawCircle(
                            color = colorCluster.copy(alpha = 0.3f),
                            radius = epsilon.toFloat(),
                            center = center,
                            style = Stroke(width = 2f)
                        )

                        drawContext.canvas.nativeCanvas.drawText(
                            cluster.points.size.toString(),
                            center.x + 10,
                            center.y + 10,
                            Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 32f
                            }
                        )
                    }
                }
            }
        }
    }
}


