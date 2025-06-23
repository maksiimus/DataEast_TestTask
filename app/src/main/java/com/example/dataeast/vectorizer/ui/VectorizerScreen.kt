package com.example.vectorizer

import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import georegression.struct.point.Point2D_I32
import androidx.compose.ui.geometry.Offset

@Composable
fun VectorizerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModel: VectorizerViewModel = viewModel()

    var bitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
    var processed by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val inputStream = context.assets.open("circle.png")
        bitmap = BitmapFactory.decodeStream(inputStream)
    }

    LaunchedEffect(bitmap, processed) {
        if (processed && bitmap != null) {
            viewModel.process(bitmap!!)
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        bitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Original Image",
                modifier = Modifier.height(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { processed = true }) {
                Text("Векторизовать")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        if (viewModel.contours.value.isNotEmpty()) {
            Text("Найдено контуров: ${viewModel.contours.value.size}")
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)) {
                for (polygon in viewModel.contours.value) {
                    drawPolygon(polygon)
                }
            }
        }
    }
}

private fun DrawScope.drawPolygon(points: List<Point2D_I32>) {
    if (points.size < 2) return
    for (i in 0 until points.size - 1) {
        drawLine(
            start = Offset(points[i].x.toFloat(), points[i].y.toFloat()),
            end = Offset(points[i + 1].x.toFloat(), points[i + 1].y.toFloat()),
            color = androidx.compose.ui.graphics.Color.Red,
            strokeWidth = 2f
        )
    }
    drawLine(
        start = Offset(points.last().x.toFloat(), points.last().y.toFloat()),
        end = Offset(points.first().x.toFloat(), points.first().y.toFloat()),
        color = androidx.compose.ui.graphics.Color.Red,
        strokeWidth = 2f
    )
}
