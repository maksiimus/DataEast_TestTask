package com.example.dataeast.polygon.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DrawArea(
    onPolygonFinished: (List<Offset>) -> Unit
) {
    var points by remember { mutableStateOf(listOf<Offset>()) }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        if (points.size > 2) {
                            onPolygonFinished(points + points.first())
                        }
                        points = emptyList()
                    }
                ) {
                    change, _ ->
                    points = points + change.position
                }
            }
    ) {
        if (points.isNotEmpty()) {
            val path = Path().apply {
                moveTo(points.first().x, points.first().y)
                points.drop(1).forEach{
                    lineTo(it.x, it.y)
                }
            }
            drawPath(path, color = Color.Black, alpha = 0.5f)
            points.forEach{
                drawCircle(Color.Red, radius = 5f, center = it)
            }
        }
    }
}