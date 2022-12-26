package io.gitlab.hum4noid.graphene.line.xy

import android.util.Log
import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint
import kotlin.math.*

private const val TAG = "LineGraph"

fun generateGuides(
    bounds: Pair<CoordinatePoint, CoordinatePoint>,
    xGuides: Boolean,
    yGuides: Boolean,
    includeZero: Boolean
): List<Pair<CoordinatePoint, CoordinatePoint>> {
    val (minPoint, maxPoint) = bounds

    val xSize = maxPoint.x - minPoint.x
    val ySize = maxPoint.y - minPoint.y
    val resultList = ArrayList<Pair<CoordinatePoint, CoordinatePoint>>()

    if (includeZero) {
        // Add X Axis:
        resultList.add(Pair(CoordinatePoint(minPoint.x, 0f), CoordinatePoint(maxPoint.x, 0f)))
        // Add Y Axis
        resultList.add(Pair(CoordinatePoint(0f, minPoint.y), CoordinatePoint(0f, maxPoint.y)))
    }

    // Calculate Order of magnitude (power of 10) to decide distance between graphs
    val xOOM = floor(log10(xSize)).toInt()
    val yOOM = floor(log10(ySize)).toInt()

    // Parallel to X Axis
    if (xGuides) {
        val minYRounded = minPoint.y.roundToOrder(yOOM)
        val maxYRounded = maxPoint.y.roundToOrder(yOOM)
        val yStep = 10f.pow(yOOM - 1)

        Log.d(
            TAG,
            "generateGuides: Y: min: $minYRounded max: $maxYRounded order: $yOOM pow: $yStep"
        )
        var y = max(minYRounded, minPoint.y)
        while (y <= maxPoint.y) {
            resultList.add(
                Pair(
                    CoordinatePoint(minPoint.x, y),
                    CoordinatePoint(maxPoint.x, y)
                )
            )
            y += yStep
        }
    }


    // Parallel to Y Axis
    if (yGuides) {
        val minXRounded = minPoint.x.roundToOrder(xOOM)
        val maxXRounded = maxPoint.x.roundToOrder(xOOM)
        val xStep = 10f.pow(xOOM - 1)

        Log.d(
            TAG,
            "generateGuides: X: min: $minXRounded max: $maxXRounded order: $xOOM pow: $xStep"
        )

        var x = max(minXRounded, minPoint.x)
        while (x <= maxPoint.x) {
            resultList.add(
                Pair(
                    CoordinatePoint(x, minPoint.y),
                    CoordinatePoint(x, maxPoint.y)
                )
            )
            x += xStep
        }
    }

    return resultList
}

fun Float.roundToOrder(
    powerOfTen: Int
): Float {
    val denominator = 10f.pow(powerOfTen)
    return ceil(this / denominator) * denominator
}

fun main() {
    print("order for 4652: ${floor(log10(4652f)).toInt()}")
    print("rounded to 100 for 4652: ${4652f.roundToOrder(2)}")
}