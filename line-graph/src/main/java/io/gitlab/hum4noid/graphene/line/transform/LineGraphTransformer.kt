package io.gitlab.hum4noid.graphene.line.transform

import androidx.compose.ui.geometry.Size
import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint
import io.gitlab.hum4noid.graphene.core.util.*
import kotlin.math.sign

class LineGraphTransformer(
    private val originalPoints: List<CoordinatePoint>,
    private val canvasSize: Size
) {
    val graphBounds = originalPoints.bounds()
    val graphSize = originalPoints.maxDimensions()

    private val graphWidth = graphSize.first
    private val graphHeight = graphSize.second

    private val canvasWidth = canvasSize.width
    private val canvasHeight = canvasSize.height

    private val xScale = canvasWidth / graphWidth
    private val yScale = canvasHeight / graphHeight

    private val xOriginIncluded: Boolean
    private val yOriginIncluded: Boolean

    private val scaledMinPoint: CoordinatePoint
    private val scaledMaxPoint: CoordinatePoint

    init {
        val (minPoint, maxPoint) = graphBounds

        xOriginIncluded = minPoint.x.sign != maxPoint.x.sign
        yOriginIncluded = minPoint.y.sign != maxPoint.y.sign

        scaledMinPoint = minPoint.scaleBy(xScale, -yScale)
        scaledMaxPoint = maxPoint.scaleBy(xScale, -yScale)
    }

    fun transform(): MutableList<CoordinatePoint> {
        return originalPoints
            .scale(xScale, -yScale)
            .translate(0f, -scaledMaxPoint.y)
            .toMutableList()
    }

    fun transform(coordinatePoint: CoordinatePoint) = coordinatePoint
        .scaleBy(xScale, -yScale)
        .offsetBy(0f, -scaledMaxPoint.y)

    fun transform(coordinatePoints: Pair<CoordinatePoint, CoordinatePoint>) = Pair(
        transform(coordinatePoints.first),
        transform(coordinatePoints.second)
    )
}