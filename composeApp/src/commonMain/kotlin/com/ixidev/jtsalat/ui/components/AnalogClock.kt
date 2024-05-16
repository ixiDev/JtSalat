package com.ixidev.jtsalat.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ixidev.jtsalat.utils.ClockFlow
import com.ixidev.jtsalat.utils.toRadians
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.cos
import kotlin.math.sin


data class ClockStyle(
    val hoursHandColor: Color = Color.Black,
    val minutesHandColor: Color = Color.Black,
    val secondsHandColor: Color = Color.Gray,
    val clockCircleColor: Color = Color.Black,
    val textClockColor: Color = Color.Black,
    val centreDotColor: Color = Color.Black,
    val textSize: TextUnit = 10.sp,
    val strokeWidth: Float = 2f,
    val handWidth: Float = 6f,
    val centreDotRadius: Float = 4f,
    val textPadding: Float = 0.85f
)

@Composable
fun AnalogClock(
    modifier: Modifier = Modifier,
    style: ClockStyle = ClockStyle(),
    clockFlow: Flow<LocalDateTime>
) {
    val textMeasurer = rememberTextMeasurer()
    val clock by clockFlow.collectAsState(null)
    val hours = clock?.hour ?: 1
    val minutes = clock?.minute ?: 3
    val seconds = clock?.second ?: 45
    Canvas(
        modifier = modifier
    ) {
        drawClockFace(textMeasurer, style)
        drawClockHands(hours, minutes, seconds, style)
        // draw centred dot
        drawCircle(
            color = style.centreDotColor,
            radius = style.centreDotRadius.dp.toPx(),
            center = center
        )
    }
}

private fun DrawScope.drawClockFace(textMeasurer: TextMeasurer, style: ClockStyle) {
    val radius = size.minDimension / 2
    val center = Offset(size.width / 2, size.height / 2)

    drawCircle(
        color = style.clockCircleColor,
        radius = radius,
        center = center,
        style = Stroke(width = style.strokeWidth.dp.toPx())
    )

    repeat(12) { i ->
        val hour = i + 1
        val angle = (hour * 30 - 30 * 3).toDouble().toRadians()
        val startX = center.x + radius * style.textPadding * cos(angle).toFloat()
        val startY = center.y + radius * style.textPadding * sin(angle).toFloat()
        drawText(
            textMeasurer = textMeasurer,
            text = "$hour",
            topLeft = Offset(
                startX - textMeasurer.measure(text = "$hour").size.width / 2,
                startY - textMeasurer.measure(text = "$hour").size.height / 2
            ),
            style = TextStyle(
                color = style.textClockColor,
                fontSize = style.textSize
            )

        )

    }
}

private fun DrawScope.drawClockHands(
    hours: Int,
    minutes: Int,
    seconds: Int,
    style: ClockStyle
) {
    val radius = size.minDimension / 2
    val center = Offset(size.width / 2, size.height / 2)

    drawHand(
        center,
        radius * 0.3f,
        hours % 12 * 30 + minutes / 2,
        color = style.hoursHandColor,
        strokeWidth = style.strokeWidth
    )
    drawHand(
        center,
        radius * 0.5f,
        minutes * 6,
        color = style.minutesHandColor,
        strokeWidth = style.strokeWidth
    )
    drawHand(
        center,
        radius * 0.7f,
        seconds * 6,
        color = style.secondsHandColor,
        strokeWidth = style.strokeWidth
    )
}

private fun DrawScope.drawHand(
    center: Offset,
    length: Float,
    angle: Int,
    color: Color = Color.White,
    strokeWidth: Float
) {
    rotate(angle.toFloat(), center) {
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x, center.y - length),
            strokeWidth = strokeWidth.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

