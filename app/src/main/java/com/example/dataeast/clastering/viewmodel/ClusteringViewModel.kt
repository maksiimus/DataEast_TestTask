package com.example.dataeast.clastering.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataeast.clastering.logic.Clusterizer
import com.example.dataeast.clastering.model.Cluster
import com.example.dataeast.clastering.model.RasterPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClusteringViewModel : ViewModel() {

    private val _clusters = MutableStateFlow<List<Cluster>>(emptyList())
    val clusters: StateFlow<List<Cluster>> = _clusters

    private val samplePoints = listOf(
        RasterPoint(100, 100), RasterPoint(105, 100), RasterPoint(110, 100),
        RasterPoint(400, 400), RasterPoint(405, 400), RasterPoint(400, 200),
        RasterPoint(300, 300), RasterPoint(310, 310), RasterPoint(305, 315)
    )

    fun clusterize(epsilon: Double = 50.0, minPoints: Int = 2) {
        viewModelScope.launch {
            val result = Clusterizer.clusterize(samplePoints, epsilon, minPoints)
            _clusters.value = result
        }
    }
}