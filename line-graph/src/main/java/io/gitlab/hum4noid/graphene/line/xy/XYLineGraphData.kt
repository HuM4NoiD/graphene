package io.gitlab.hum4noid.graphene.line.xy

import androidx.compose.ui.graphics.Color
import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint

data class XYLineGraphOptions(
    val drawHorizontalGuides: Boolean = false,
    val drawVerticalGuides: Boolean = false,
    val highlightXAxis: Boolean = false,
    val highlightYAxis: Boolean = false,
    val showLegend: Boolean = false,
    val markPoints: Boolean = false,
    val pointStyle: PointStyle = PointStyle.ROUND_FILLED,
    val gradientFill: Boolean = false,
    val graphTitle: String? = null,
    val xLabel: String? = null,
    val yLabel: String? = null,
)

enum class PointStyle {
    ROUND_FILLED, ROUND_STROKE
}

data class XYLineGraphData(
    val label: String,
    val color: Color,
    val points: List<CoordinatePoint>
)

