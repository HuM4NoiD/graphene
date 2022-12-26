package io.gitlab.hum4noid.graphene.line.xy

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import io.gitlab.hum4noid.graphene.common.legend.Legend
import io.gitlab.hum4noid.graphene.common.legend.LegendDatum
import io.gitlab.hum4noid.graphene.common.legend.LegendItem
import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint
import io.gitlab.hum4noid.graphene.core.util.*
import io.gitlab.hum4noid.graphene.line.transform.LineGraphTransformer

private const val TAG = "LineGraph"

@Composable
fun XYLineGraph(
    graphs: List<XYLineGraphData>,
    graphOptions: XYLineGraphOptions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(
            modifier = Modifier
        ) {
            for (graphData in graphs) {
                val transformer = LineGraphTransformer(graphData.points, size)

                drawDataPoints(graphData.color, graphOptions, transformer)
                drawGuides(transformer.graphBounds, graphOptions, transformer)
            }
        }
        if (graphOptions.showLegend) {
            Legend(
                title = {},
                data = graphs.map { graph -> LegendDatum(label = graph.label, color = graph.color) }
            )
        }
    }
}

/*private fun measurePolicy(graphWidth: Float, graphHeight: Float): MeasurePolicy {
    return object: MeasurePolicy {
        override fun MeasureScope.measure(
            measurables: List<Measurable>,
            constraints: Constraints
        ): MeasureResult {

        }
    }
}*/

private fun DrawScope.drawDataPoints(
    lineColor: Color,
    graphOptions: XYLineGraphOptions,
    transformer: LineGraphTransformer
) {
    val scaledPoints = transformer.transform()

    if (graphOptions.gradientFill) {
        val areaPath = buildPath(scaledPoints, transformer, true)

        drawPath(
            path = areaPath,
            brush = Brush.verticalGradient(
                listOf(
                    lineColor.copy(alpha = lineColor.alpha * 0.6f),
                    Color.Transparent
                )
            ),
            style = Fill
        )
    }

    val path = buildPath(scaledPoints, transformer)

    drawPath(path = path, color = lineColor, style = Stroke(2.dp.toPx()))

    for (point in scaledPoints) {
        drawCircle(
            color = lineColor,
            radius = 6.dp.toPx(),
            center = Offset(point.x, point.y),
            style = if (graphOptions.pointStyle == PointStyle.ROUND_FILLED) {
                Fill
            } else {
                Stroke(2.dp.toPx())
            }
        )
    }

//    var lastPoint = scaledPoints.first()
//    scaledPoints.removeAt(0)
//    drawCircle(
//        color = lineColor,
//        radius = 2.dp.toPx(),
//        center = Offset(lastPoint.x, lastPoint.y),
//        style = Fill
//    )
//
//    for (point in scaledPoints) {
//        drawLine(
//            color = lineColor,
//            start = Offset(lastPoint.x, lastPoint.y),
//            end = Offset(point.x, point.y)
//        )
//        lastPoint = point
//        drawCircle(
//            color = lineColor,
//            radius = 2.dp.toPx(),
//            center = Offset(lastPoint.x, lastPoint.y),
//            style = Fill
//        )
//    }
}

private fun DrawScope.drawGuides(
    bounds: Pair<CoordinatePoint, CoordinatePoint>,
    graphOptions: XYLineGraphOptions,
    transformer: LineGraphTransformer
) {
    val guides = generateGuides(
        bounds,
        graphOptions.drawHorizontalGuides,
        graphOptions.drawVerticalGuides,
        graphOptions.highlightXAxis || graphOptions.highlightYAxis
    )

    for (line in guides) {
        val transformedLine = transformer.transform(line)
        drawLine(
            color = Color(0xff888888),
            start = Offset(transformedLine.first.x, transformedLine.first.y),
            end = Offset(transformedLine.second.x, transformedLine.second.y)
        )
    }
}

@Preview
@Composable
fun LineGraphPreview() {

    val points = remember {
        mutableStateOf(generateCoords(50))
    }

    Box(
        modifier = Modifier
            .background(Color(0xFF121212))
            .fillMaxSize()
    ) {
        XYLineGraph(
//            dataPoints = listOf(DataPoint(20.0, 25.0), DataPoint(30.0, 35.0)),
            graphs = listOf(XYLineGraphData("Sample", Color.Red, points.value)),
            graphOptions = XYLineGraphOptions(
                drawHorizontalGuides = true,
                markPoints = true,
                pointStyle = PointStyle.ROUND_FILLED,
                highlightXAxis = true,
                highlightYAxis = true,
                gradientFill = true,
                showLegend = true,
            ),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(32.dp)
                .border(width = 1.dp, shape = RoundedCornerShape(16.dp), color = Color.Cyan)
                .padding(16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}
