package com.example.dataeast.Geocoder.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.example.dataeast.R

@Composable
fun MapViewContainer(
    context: Context,
    point: Point,
    modifier: Modifier = Modifier
) {
    val mapView = remember {
        MapKitFactory.initialize(context)
        MapView(context).apply {
            map.move(
                CameraPosition(Point(55.75, 37.61), 14.0f, 0.0f, 0.0f)
            )
        }
    }

    LaunchedEffect(point) {
        mapView.map.mapObjects.clear()

        mapView.map.mapObjects.addPlacemark(
            point,
            ImageProvider.fromResource(context, R.drawable.ic_marker_red_48)
        )

        mapView.map.move(
            CameraPosition(point, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 1f),
            null
        )
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView }
    )
}
