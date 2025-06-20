package com.example.dataeast.clastering.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataeast.clastering.logic.Clusterizer
import com.example.dataeast.clastering.model.Cluster
import com.example.dataeast.clastering.model.RasterPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClusteringViewModel : ViewModel() {
    private val _image = MutableStateFlow<Bitmap?>(null)
    val image: StateFlow<Bitmap?> = _image

    private val _points = MutableStateFlow<List<RasterPoint>>(emptyList())
    val points: StateFlow<List<RasterPoint>> = _points

    private val _clusters = MutableStateFlow<List<Cluster>>(emptyList())
    val clusters: StateFlow<List<Cluster>> = _clusters

    fun loadImage(bitmap: Bitmap) {
        _image.value = bitmap
    }

    fun loadPoints(newPoints: List<RasterPoint>){
        _points.value = newPoints
    }

    private val testPoints = listOf(
        RasterPoint(100, 100),
        RasterPoint(105, 102),
        RasterPoint(98, 95),
        RasterPoint(400, 400),
        RasterPoint(405, 402),
        RasterPoint(800, 200)
    )

    private val samplePoints = listOf(
        RasterPoint(100, 100), RasterPoint(105, 100), RasterPoint(110, 100), RasterPoint(115, 100), RasterPoint(120, 100),
        RasterPoint(100, 105), RasterPoint(105, 105), RasterPoint(110, 105), RasterPoint(115, 105), RasterPoint(120, 105),
        RasterPoint(100, 110), RasterPoint(105, 110), RasterPoint(110, 110), RasterPoint(115, 110), RasterPoint(120, 110),
        RasterPoint(100, 115), RasterPoint(105, 115), RasterPoint(110, 115), RasterPoint(115, 115), RasterPoint(120, 115),

        RasterPoint(300, 100), RasterPoint(305, 100), RasterPoint(310, 100), RasterPoint(315, 100), RasterPoint(320, 100),
        RasterPoint(300, 105), RasterPoint(305, 105), RasterPoint(310, 105), RasterPoint(315, 105), RasterPoint(320, 105),
        RasterPoint(300, 110), RasterPoint(305, 110), RasterPoint(310, 110), RasterPoint(315, 110), RasterPoint(320, 110),
        RasterPoint(300, 115), RasterPoint(305, 115), RasterPoint(310, 115), RasterPoint(315, 115), RasterPoint(320, 115),

        RasterPoint(200, 300), RasterPoint(205, 300), RasterPoint(210, 300), RasterPoint(215, 300), RasterPoint(220, 300),
        RasterPoint(200, 305), RasterPoint(205, 305), RasterPoint(210, 305), RasterPoint(215, 305), RasterPoint(220, 305),
        RasterPoint(200, 310), RasterPoint(205, 310), RasterPoint(210, 310), RasterPoint(215, 310), RasterPoint(220, 310),
        RasterPoint(200, 315), RasterPoint(205, 315), RasterPoint(210, 315), RasterPoint(215, 315), RasterPoint(220, 315)
    )




    fun clusterize(epsilon: Double = 800.0, minPoints: Int = 1){
        viewModelScope.launch {
            val result = Clusterizer.clusterize(samplePoints, epsilon, minPoints)
            _clusters.value = result
        }
    }
}