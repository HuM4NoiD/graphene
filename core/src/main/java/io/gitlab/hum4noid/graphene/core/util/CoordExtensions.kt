package io.gitlab.hum4noid.graphene.core.util

import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint

/**
 * Returns a pair of data points where
 * first represents smallest possible coordinate
 * and second represents largest possible coordinate
 */
fun List<CoordinatePoint>.bounds() = bounds(0F)

/**
 * Returns a pair of data points where
 * first represents smallest possible coordinate
 * and second represents largest possible coordinate
 * with padding
 * @param padding additional space added along both axes
 */
fun List<CoordinatePoint>.bounds(
    padding: Float
): Pair<CoordinatePoint, CoordinatePoint> {
    if (padding < 0) throw IllegalArgumentException("padding should not be negative, found: $padding")
    return bounds(padding, padding)
}

/**
 * Returns a pair of data points where
 * first represents smallest possible coordinate
 * and second represents largest possible coordinate
 * with padding
 * @param xPadding additional space to be padded along x axis
 * @param yPadding additional space to be padded along y axis
 */
fun List<CoordinatePoint>.bounds(
    xPadding: Float,
    yPadding: Float
): Pair<CoordinatePoint, CoordinatePoint> {

    if (xPadding < 0) throw IllegalArgumentException("xPadding should not be negative, found: $xPadding")
    if (yPadding < 0) throw IllegalArgumentException("yPadding should not be negative, found: $yPadding")

    val xMin = this.minOf { it.x }
    val yMin = this.minOf { it.y }

    val xMax = this.maxOf { it.x }
    val yMax = this.maxOf { it.y }

    return Pair(
        CoordinatePoint(xMin - xPadding, yMin - yPadding),
        CoordinatePoint(xMax + xPadding, yMax + yPadding)
    )
}

/**
 * Returns the dimensions of the minimum bounding rectangle
 * where first is along x axis and second is along y axis
 */

fun List<CoordinatePoint>.maxDimensions() = maxDimensions(0f)

/**
 * Returns the dimensions of the minimum bounding rectangle
 * where first is along x axis and second is along y axis
 * with padding which is added to both parallel edges
 * @param padding padding added along both axes
 */

fun List<CoordinatePoint>.maxDimensions(
    padding: Float
) = maxDimensions(padding, padding)

/**
 * Returns the dimensions of the minimum bounding rectangle
 * where first is along x axis and second is along y axis
 * with padding which is added to both parallel edges
 * @param xPadding padding along x axis
 * @param yPadding padding along y axis
 */
fun List<CoordinatePoint>.maxDimensions(
    xPadding: Float,
    yPadding: Float
): Pair<Float, Float> {

    if (xPadding < 0) throw IllegalArgumentException("xPadding should not be negative, found: $xPadding")
    if (yPadding < 0) throw IllegalArgumentException("yPadding should not be negative, found: $yPadding")

    val (min, max) = this.bounds();
    return Pair(
        max.x - min.x + xPadding * 2,
        max.y - min.y + yPadding * 2
    );
}


/**
 * Scale the coordinates using given scale for each axis
 */
fun List<CoordinatePoint>.scale(
    xScale: Float,
    yScale: Float
): List<CoordinatePoint> {
    return this.map { point -> CoordinatePoint(point.x * xScale, point.y * yScale) }
}

/**
 * Offset/Translate coordinates by given offsets
 */
fun List<CoordinatePoint>.translate(
    xOffset: Float,
    yOffset: Float
): List<CoordinatePoint> {
    return this.map { point -> point.offsetBy(xOffset, yOffset) }
}

fun List<CoordinatePoint>.transformToScreenCoords(): List<CoordinatePoint> {
    return this.scale(1f, -1f)
}