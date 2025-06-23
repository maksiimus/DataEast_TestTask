package com.example.dataeast.clastering.logic

import androidx.compose.ui.geometry.Offset
import com.example.dataeast.clastering.model.*
import kotlin.math.pow
import kotlin.math.sqrt

object Clusterizer {
    fun clusterize(points: List<RasterPoint>, epsilon: Double, minPts: Int): List<Cluster> {
        val clusters = mutableListOf<Cluster>()
        val visited = mutableSetOf<RasterPoint>()
        var clusterId = 0

        for (point in points) {
            if (point in visited) continue
            visited.add(point)

            val neighbors = regionQuery(points, point, epsilon)
            if (neighbors.size + 1 < minPts) continue

            val clusterPoints = mutableListOf<RasterPoint>()
            expandCluster(point, neighbors, clusterPoints, points, visited, epsilon, minPts)
            clusters.add(Cluster(clusterId++, clusterPoints))
        }

        return clusters
    }

    private fun expandCluster(
        point: RasterPoint,
        neighbors: List<RasterPoint>,
        clusterPoints: MutableList<RasterPoint>,
        allPoints: List<RasterPoint>,
        visited: MutableSet<RasterPoint>,
        epsilon: Double,
        minPts: Int
    ) {
        val queue = ArrayDeque(neighbors)
        clusterPoints.add(point)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current !in visited) {
                visited.add(current)
                val currentNeighbors = regionQuery(allPoints, current, epsilon)
                if (currentNeighbors.size + 1 >= minPts) {
                    queue.addAll(currentNeighbors.filterNot { it in visited || it in clusterPoints })
                }
            }
            if (current !in clusterPoints) {
                clusterPoints.add(current)
            }
        }
    }

    private fun regionQuery(points: List<RasterPoint>, center: RasterPoint, epsilon: Double): List<RasterPoint> {
        return points.filter { it != center && distance(it, center) <= epsilon }
    }

    private fun distance(p1: RasterPoint, p2: RasterPoint): Double {
        return sqrt((p1.x - p2.x).toDouble().pow(2) + (p1.y - p2.y).toDouble().pow(2))
    }

    fun computeCentroid(points: List<RasterPoint>): Offset {
        val x = points.map { it.x }.average().toFloat()
        val y = points.map { it.y }.average().toFloat()
        return Offset(x, y)
    }

}