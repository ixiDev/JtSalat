package com.ixidev.jtsalat.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.ixidev.jtsalat.utils.toRadians
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun CompassView(modifier: Modifier = Modifier, orientation: Float) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val compassRadius = size.minDimension / 2
        val needleLength = compassRadius * 0.8f

        drawCircle(
            color = Color.DarkGray,
            style = Stroke(width = 10f),
        )
        drawDegreesLines()

        drawCircle(
            color = Color(0xffbaffe5),
            radius = compassRadius * 0.7f,
        )
        drawCircle(
            color = Color(0xff24bb86),
            radius = compassRadius * 0.7f,
            style = Stroke(width = 10f)
        )

        drawCircle(
            color = Color.Black,
            radius = compassRadius * 0.5f,
        )


        // Draw compass circle
//        drawCircle(color = Color.Gray, radius = compassRadius)

        // Draw directions
//        drawCompassDirections(centerX, centerY, compassRadius, textMeasurer)

//        // Draw compass needle
//        rotate(orientation - 90f, pivot = Offset(centerX, centerY)) {
//            drawLine(
//                color = Color.Red,
//                start = Offset(centerX, centerY),
//                end = Offset(centerX, centerY - needleLength)
//            )
//        }
    }
}

private fun DrawScope.drawDegreesLines() {
    val compassRadius = size.minDimension / 2
    val degreeInterval = 3
    for (i in 0 until 360 step degreeInterval) {
        val angleRadians = i.toDouble().toRadians()
        val startX = center.x + (compassRadius - 15) * cos(angleRadians).toFloat()
        val startY = center.y + (compassRadius - 15) * sin(angleRadians).toFloat()

        val endX = center.x + (compassRadius - 40) * cos(angleRadians).toFloat()
        val endY = center.y + (compassRadius - 40) * sin(angleRadians).toFloat()

        drawLine(
            Color.White,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
//            strokeWidth = 4f
        )
    }
}

private fun DrawScope.drawCompassDirections(
    centerX: Float,
    centerY: Float,
    compassRadius: Float,
    textMeasurer: TextMeasurer
) {
    val directions = listOf("N", "E", "S", "W")
    val degreeOffset = 360f / directions.size

//    val paint = android.graphics.Paint().apply {
//        color = android.graphics.Color.White
//        textSize = 30f
//        textAlign = android.graphics.Paint.Align.CENTER
//    }

    directions.forEachIndexed { index, direction ->
        val angle = index * degreeOffset
        val textX = centerX + compassRadius * cos(angle.toDouble().toRadians()).toFloat()
        val textY = centerY + compassRadius * sin(angle.toDouble().toRadians()).toFloat()

        drawText(
            textMeasurer = textMeasurer,
            text = direction,
            topLeft = Offset(textX, textY)
        )
//        drawContext.canvas.nativeCanvas.drawText(direction, textX, textY, paint)
    }

    val degreeInterval = 15
    for (i in 0 until 360 step degreeInterval) {
        val angleRadians = i.toDouble().toRadians()
        val startX = centerX + compassRadius * cos(angleRadians).toFloat()
        val startY = centerY + compassRadius * sin(angleRadians).toFloat()

        val endX = centerX + (compassRadius - 20) * cos(angleRadians).toFloat()
        val endY = centerY + (compassRadius - 20) * sin(angleRadians).toFloat()

        drawLine(Color.White, start = Offset(startX, startY), end = Offset(endX, endY))
    }
}