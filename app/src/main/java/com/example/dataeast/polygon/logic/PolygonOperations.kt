package com.example.dataeast.polygon.logic

import androidx.compose.ui.geometry.Offset
import com.example.dataeast.polygon.model.PolygonOperation
import org.locationtech.jts.geom.*
import org.locationtech.jts.operation.overlay.OverlayOp

object PolygonOperations {
    private val geometryFactory = GeometryFactory()

    fun perform(aPoints: List<Offset>, bPoints: List<Offset>, op: PolygonOperation): List<Offset> {
        val polyA = toJTSPolygon(aPoints).buffer(0.0)
        val polyB = toJTSPolygon(bPoints).buffer(0.0)

        val result: Geometry = when (op) {
            PolygonOperation.UNION -> polyA.union(polyB)
            PolygonOperation.INTERSECTION -> polyA.intersection(polyB)
            PolygonOperation.DIFFERENCE -> polyA.difference(polyB)
        }

        return fromJTSGeometry(result)
    }

    private fun toJTSPolygon(points: List<Offset>): Geometry {
        val coordinates = points.map {
            Coordinate(it.x.toDouble(), it.y.toDouble())
        }.toMutableList()

        if (coordinates.first() != coordinates.last()) {
            coordinates.add(coordinates.first()) // автозамыкание
        }

        val linearRing = geometryFactory.createLinearRing(coordinates.toTypedArray())
        return geometryFactory.createPolygon(linearRing)
    }

    private fun fromJTSGeometry(geometry: Geometry): List<Offset> {
        val offsets = mutableListOf<Offset>()
        for (i in 0 until geometry.numGeometries) {
            val g = geometry.getGeometryN(i)
            offsets += g.coordinates.map { Offset(it.x.toFloat(), it.y.toFloat()) }
        }
        return offsets
    }
}
