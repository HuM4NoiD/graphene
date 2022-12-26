package io.gitlab.hum4noid.graphene.common.legend

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.gitlab.hum4noid.graphene.common.legend.LegendDatum
import java.nio.file.Files.size

enum class LegendItemOrientation {
    HORIZONTAL, VERTICAL
}

@Composable
fun Legend(
    title: @Composable () -> Unit,
    data: List<LegendDatum>,
    orientation: LegendItemOrientation = LegendItemOrientation.HORIZONTAL,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        title()
        when (orientation) {
            LegendItemOrientation.HORIZONTAL -> Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (datum in data) {
                    LegendItem(datum = datum)
                }
            }
            LegendItemOrientation.VERTICAL -> Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                for (datum in data) {
                    LegendItem(datum = datum)
                }
            }
        }
    }
}

@Composable
fun LegendItem(
    datum: LegendDatum
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .background(datum.color)
                .size(8.dp),
        )
        BasicText(text = datum.label, style = TextStyle.Default)
    }
}

@Preview
@Composable
fun LegendPreview() {
    Legend(
        title = { BasicText(text = "Sample Title") },
        data = listOf(LegendDatum("code", Color.Cyan), LegendDatum("manual", Color.Red)),
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    )
}