package com.example.dataeast.vectorizer.logic

import android.graphics.Bitmap
import boofcv.alg.filter.binary.BinaryImageOps
import boofcv.android.ConvertBitmap
import boofcv.struct.ConnectRule
import boofcv.struct.image.GrayU8
import georegression.struct.point.Point2D_I32


object Vectorizer {
    fun extractContours(bitmap: Bitmap): List<List<Point2D_I32>> {
        val gray = GrayU8(bitmap.width, bitmap.height)
        ConvertBitmap.bitmapToGray(bitmap, gray, null)

        val binary = GrayU8(bitmap.width, bitmap.height)
        for (y in 0 until gray.height) {
            for (x in 0 until gray.width) {
                binary.set(x, y, if (gray.get(x, y) < 128) 1 else 0)
            }
        }

        val found = BinaryImageOps.contour(binary, ConnectRule.EIGHT, null)
        return found.map { it.external }
    }
}
