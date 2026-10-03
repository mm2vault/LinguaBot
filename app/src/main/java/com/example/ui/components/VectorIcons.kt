package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GemTeal
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.HeartColor
import com.example.ui.theme.StreakFlameColor

/**
 * Custom Vector Graphics - Strictly No Emojis!
 */

@Composable
fun FlameIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = StreakFlameColor
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val path = Path().apply {
            moveTo(w * 0.5f, 0f)
            cubicTo(w * 0.7f, h * 0.25f, w * 0.95f, h * 0.5f, w * 0.85f, h * 0.8f)
            cubicTo(w * 0.78f, h * 0.98f, w * 0.35f, h * 1.02f, w * 0.18f, h * 0.82f)
            cubicTo(w * 0.05f, h * 0.65f, w * 0.15f, h * 0.45f, w * 0.35f, h * 0.38f)
            cubicTo(w * 0.28f, h * 0.25f, w * 0.38f, h * 0.12f, w * 0.5f, 0f)
            close()
        }
        drawPath(path, color = color, style = Fill)

        // Inner lighter core
        val innerPath = Path().apply {
            moveTo(w * 0.5f, h * 0.35f)
            cubicTo(w * 0.65f, h * 0.5f, w * 0.75f, h * 0.7f, w * 0.65f, h * 0.88f)
            cubicTo(w * 0.55f, h * 0.95f, w * 0.4f, h * 0.95f, w * 0.35f, h * 0.85f)
            cubicTo(w * 0.28f, h * 0.75f, w * 0.35f, h * 0.6f, w * 0.5f, h * 0.35f)
            close()
        }
        drawPath(innerPath, color = Color(0xFFFDE047), style = Fill)
    }
}

@Composable
fun HeartIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = HeartColor,
    isFilled: Boolean = true
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.85f)
            cubicTo(w * 0.15f, h * 0.6f, 0f, h * 0.35f, 0f, h * 0.2f)
            cubicTo(0f, 0.05f, w * 0.2f, 0f, w * 0.5f, h * 0.25f)
            cubicTo(w * 0.8f, 0f, w, 0.05f, w, h * 0.2f)
            cubicTo(w, h * 0.35f, w * 0.85f, h * 0.6f, w * 0.5f, h * 0.85f)
            close()
        }
        if (isFilled) {
            drawPath(path, color = color, style = Fill)
        } else {
            drawPath(path, color = color, style = Stroke(width = 3.dp.toPx()))
        }
    }
}

@Composable
fun GemIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = GemTeal
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val path = Path().apply {
            moveTo(w * 0.25f, h * 0.1f)
            lineTo(w * 0.75f, h * 0.1f)
            lineTo(w * 0.95f, h * 0.4f)
            lineTo(w * 0.5f, h * 0.95f)
            lineTo(w * 0.05f, h * 0.4f)
            close()
        }
        drawPath(path, color = color, style = Fill)

        // Highlights
        val facetPath = Path().apply {
            moveTo(w * 0.25f, h * 0.1f)
            lineTo(w * 0.5f, h * 0.4f)
            lineTo(w * 0.75f, h * 0.1f)
        }
        drawPath(facetPath, color = Color.White.copy(alpha = 0.6f), style = Stroke(width = 2.dp.toPx()))

        val centerLine = Path().apply {
            moveTo(w * 0.5f, h * 0.4f)
            lineTo(w * 0.5f, h * 0.95f)
        }
        drawPath(centerLine, color = Color.White.copy(alpha = 0.4f), style = Stroke(width = 2.dp.toPx()))
    }
}

@Composable
fun CrownIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = GoldCrown
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val path = Path().apply {
            moveTo(w * 0.1f, h * 0.8f)
            lineTo(w * 0.05f, h * 0.35f)
            lineTo(w * 0.32f, h * 0.55f)
            lineTo(w * 0.5f, h * 0.2f)
            lineTo(w * 0.68f, h * 0.55f)
            lineTo(w * 0.95f, h * 0.35f)
            lineTo(w * 0.9f, h * 0.8f)
            close()
        }
        drawPath(path, color = color, style = Fill)

        // Jewel circles on points
        drawCircle(color = Color(0xFFEF4444), radius = w * 0.06f, center = Offset(w * 0.5f, h * 0.2f))
        drawCircle(color = Color(0xFF06B6D4), radius = w * 0.05f, center = Offset(w * 0.05f, h * 0.35f))
        drawCircle(color = Color(0xFF06B6D4), radius = w * 0.05f, center = Offset(w * 0.95f, h * 0.35f))
    }
}

@Composable
fun StarBadgeIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = GoldCrown
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val cx = w / 2f
        val cy = h / 2f
        val rOut = w * 0.48f
        val rIn = w * 0.22f

        val path = Path().apply {
            for (i in 0 until 5) {
                val angleOut = Math.toRadians((i * 72 - 90).toDouble())
                val xOut = (cx + rOut * Math.cos(angleOut)).toFloat()
                val yOut = (cy + rOut * Math.sin(angleOut)).toFloat()
                if (i == 0) moveTo(xOut, yOut) else lineTo(xOut, yOut)

                val angleIn = Math.toRadians((i * 72 + 36 - 90).toDouble())
                val xIn = (cx + rIn * Math.cos(angleIn)).toFloat()
                val yIn = (cy + rIn * Math.sin(angleIn)).toFloat()
                lineTo(xIn, yIn)
            }
            close()
        }
        drawPath(path, color = color, style = Fill)
    }
}

@Composable
fun LightningIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color(0xFFFACC15)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val path = Path().apply {
            moveTo(w * 0.55f, 0f)
            lineTo(w * 0.15f, h * 0.55f)
            lineTo(w * 0.48f, h * 0.55f)
            lineTo(w * 0.35f, h)
            lineTo(w * 0.85f, h * 0.42f)
            lineTo(w * 0.52f, h * 0.42f)
            close()
        }
        drawPath(path, color = color, style = Fill)
    }
}

@Composable
fun ShieldIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color(0xFF3B82F6)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val path = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w * 0.95f, h * 0.15f)
            lineTo(w * 0.9f, h * 0.6f)
            cubicTo(w * 0.85f, h * 0.85f, w * 0.5f, h, w * 0.5f, h)
            cubicTo(w * 0.5f, h, w * 0.15f, h * 0.85f, w * 0.1f, h * 0.6f)
            lineTo(w * 0.05f, h * 0.15f)
            close()
        }
        drawPath(path, color = color, style = Fill)
    }
}
