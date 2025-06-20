package com.example.dataeast.polygon.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dataeast.polygon.model.DrawnPolygon
import androidx.compose.ui.geometry.Offset
import com.example.dataeast.polygon.logic.PolygonOperations
import com.example.dataeast.polygon.model.PolygonOperation
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class PolygonViewModel:ViewModel() {
    var polygonA by mutableStateOf<DrawnPolygon?>(null)
        private set

    var polygonB by mutableStateOf<DrawnPolygon?>(null)
        private set

    var operation by mutableStateOf(PolygonOperation.UNION)
        private set

    var result by mutableStateOf<DrawnPolygon?>(null)
        private set

    fun setPolygonA(points: List<Offset>) {
        polygonA = DrawnPolygon(points)
        isPolygonADrawn = true
    }

    fun setPolygonB(points: List<Offset>) {
        polygonB = DrawnPolygon(points)
        isPolygonBDrawn = true
    }

    fun updateOperation(op: PolygonOperation){
        operation = op
    }

    fun applyOperation(){
        val a = polygonA?.points ?: return
        val b = polygonB?.points ?: return

        result = DrawnPolygon(
            PolygonOperations.perform(a, b, operation)
        )
    }

    var isPolygonADrawn by mutableStateOf(false)
        private set

    var isPolygonBDrawn by mutableStateOf(false)
        private set

    fun clearPolygonA() {
        polygonA = null
        isPolygonADrawn = false
    }

    fun clearPolygonB() {
        polygonB = null
        isPolygonBDrawn = false
    }


}