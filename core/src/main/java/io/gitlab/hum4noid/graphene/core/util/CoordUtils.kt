package io.gitlab.hum4noid.graphene.core.util

import android.os.SystemClock
import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint
import kotlin.random.Random

fun generateCoords(firstPoint: CoordinatePoint, numPoints: Int): List<CoordinatePoint> {
    val result = mutableListOf<CoordinatePoint>()
    val random = Random(SystemClock.uptimeMillis())

    var lastPoint = firstPoint
    result.add(lastPoint)

    for (i in 1..(numPoints + 1)) {
        val xNoise = i * 2f
        val yNoise = i * 2 + random.nextDouble(-2.0, +2.0).toFloat()

        val newPoint = lastPoint.offsetBy(xNoise, yNoise)
        result.add(newPoint)
    }

    return result;
}

fun generateCoords(numPoints: Int): List<CoordinatePoint> {
    val lastPoint = CoordinatePoint(0.0f, 0.0f,)

    return generateCoords(lastPoint, numPoints)
}