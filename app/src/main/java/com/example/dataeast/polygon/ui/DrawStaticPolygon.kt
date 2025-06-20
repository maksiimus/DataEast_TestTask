package com.example.dataeast.polygon.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun DrawStaticPolygon(points: List<Offset>, color: Color = Color.Green) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.LightGray)
    ) {
        if (points.size > 1) {
            val path = Path().apply {
                moveTo(points.first().x, points.first().y)
                points.drop(1).forEach {
                    lineTo(it.x, it.y)
                }
                close()
            }
            drawPath(path, color = color.copy(alpha = 0.5f))
            points.forEach {
                drawCircle(color, 4f, it)
            }
        }
    }
}


