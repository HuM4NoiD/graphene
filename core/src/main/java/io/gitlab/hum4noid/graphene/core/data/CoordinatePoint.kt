package io.gitlab.hum4noid.graphene.core.data

data class CoordinatePoint(
    var x: Float,
    var y: Float,
) {

    fun scaleBy(xScale: Float, yScale: Float): CoordinatePoint {
        return CoordinatePoint(
            x * xScale,
            y * yScale
        )
    }

    fun offsetBy(xOffset: Float, yOffset: Float): CoordinatePoint {
        return CoordinatePoint(
            x + xOffset,
            y + yOffset
        )
    }
}
