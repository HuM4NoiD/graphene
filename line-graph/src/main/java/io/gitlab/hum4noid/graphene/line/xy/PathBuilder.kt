package io.gitlab.hum4noid.graphene.line.xy

import androidx.compose.ui.graphics.Path
import io.gitlab.hum4noid.graphene.core.data.CoordinatePoint
import io.gitlab.hum4noid.graphene.line.transform.LineGraphTransformer

fun buildPath(
    points: List<CoordinatePoint>,
    transformer: LineGraphTransformer,
    encloseAreaUnderGraph: Boolean = false
): Path {
    val (minPoint, _) = transformer.transform(transformer.graphBounds)
    return Path().apply {
        var lastPoint = points.first()
        if (encloseAreaUnderGraph) {
            moveTo(lastPoint.x, minPoint.y)
            lineTo(lastPoint.x, lastPoint.y)
        } else {
            moveTo(lastPoint.x, lastPoint.y)
        }
        for ((index, point) in points.withIndex()) {
            if (index == 0) continue
            else lineTo(point.x, point.y)
            lastPoint = point
        }
        if (encloseAreaUnderGraph) {
            lineTo(lastPoint.x, minPoint.y)
            close()
        }
    }
}
