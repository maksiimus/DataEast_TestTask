package com.example.vectorizer

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dataeast.vectorizer.logic.Vectorizer
import georegression.struct.point.Point2D_I32

class VectorizerViewModel : ViewModel() {
    var contours = mutableStateOf<List<List<Point2D_I32>>>(emptyList())
        private set

    fun process(bitmap: Bitmap) {
        contours.value = Vectorizer.extractContours(bitmap)
    }
}