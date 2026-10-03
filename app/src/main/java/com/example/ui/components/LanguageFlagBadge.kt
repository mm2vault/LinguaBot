package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * HD Vector Flag Badge Generator for All World Languages
 */
@Composable
fun LanguageFlagBadge(
    languageCode: String,
    modifier: Modifier = Modifier,
    size: Dp = 32.dp
) {
    val code = languageCode.lowercase().trim()

    Box(
        modifier = modifier
            .size(size)
            .shadow(elevation = 2.dp, shape = CircleShape)
            .clip(CircleShape)
            .border(1.5.dp, Color.White.copy(alpha = 0.35f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height

            when (code) {
                "es" -> {
                    // Spain (Red - Yellow - Red with crest marker)
                    drawRect(color = Color(0xFFC60B1E), topLeft = Offset(0f, 0f), size = Size(w, h * 0.25f))
                    drawRect(color = Color(0xFFFFC400), topLeft = Offset(0f, h * 0.25f), size = Size(w, h * 0.5f))
                    drawRect(color = Color(0xFFC60B1E), topLeft = Offset(0f, h * 0.75f), size = Size(w, h * 0.25f))
                    drawCircle(color = Color(0xFFC60B1E), radius = w * 0.09f, center = Offset(w * 0.35f, h * 0.5f))
                    drawCircle(color = Color(0xFF003882), radius = w * 0.04f, center = Offset(w * 0.35f, h * 0.5f))
                }
                "en", "gb", "uk" -> {
                    // UK Union Jack
                    drawRect(color = Color(0xFF012169), topLeft = Offset(0f, 0f), size = Size(w, h))
                    // Diagonals White & Red
                    drawLine(color = Color.White, start = Offset(0f, 0f), end = Offset(w, h), strokeWidth = w * 0.18f)
                    drawLine(color = Color.White, start = Offset(0f, h), end = Offset(w, 0f), strokeWidth = w * 0.18f)
                    drawLine(color = Color(0xFFC8102E), start = Offset(0f, 0f), end = Offset(w, h), strokeWidth = w * 0.09f)
                    drawLine(color = Color(0xFFC8102E), start = Offset(0f, h), end = Offset(w, 0f), strokeWidth = w * 0.09f)
                    // Cross White & Red
                    drawRect(color = Color.White, topLeft = Offset(w * 0.38f, 0f), size = Size(w * 0.24f, h))
                    drawRect(color = Color.White, topLeft = Offset(0f, h * 0.38f), size = Size(w, h * 0.24f))
                    drawRect(color = Color(0xFFC8102E), topLeft = Offset(w * 0.42f, 0f), size = Size(w * 0.16f, h))
                    drawRect(color = Color(0xFFC8102E), topLeft = Offset(0f, h * 0.42f), size = Size(w, h * 0.16f))
                }
                "tr" -> {
                    // Turkey (Red with White Crescent and 5-pointed Star)
                    drawRect(color = Color(0xFFE30A17), topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawCircle(color = Color.White, radius = w * 0.27f, center = Offset(w * 0.42f, h * 0.5f))
                    drawCircle(color = Color(0xFFE30A17), radius = w * 0.21f, center = Offset(w * 0.49f, h * 0.5f))
                    // Star
                    drawStar(center = Offset(w * 0.68f, h * 0.5f), radius = w * 0.10f, color = Color.White)
                }
                "az" -> {
                    // Azerbaijan (Blue - Red - Green with Crescent & 8-pointed Star)
                    drawRect(color = Color(0xFF00B5E2), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFFEF3340), topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFF509E2F), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
                    // Crescent & 8-point star in red band
                    drawCircle(color = Color.White, radius = w * 0.12f, center = Offset(w * 0.46f, h * 0.5f))
                    drawCircle(color = Color(0xFFEF3340), radius = w * 0.09f, center = Offset(w * 0.49f, h * 0.5f))
                    drawCircle(color = Color.White, radius = w * 0.04f, center = Offset(w * 0.59f, h * 0.5f))
                }
                "de" -> {
                    // Germany (Black - Red - Gold)
                    drawRect(color = Color(0xFF1E293B), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFFDC2626), topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFFFFCC00), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
                }
                "fr" -> {
                    // France (Blue - White - Red)
                    drawRect(color = Color(0xFF0055A4), topLeft = Offset(0f, 0f), size = Size(w / 3f, h))
                    drawRect(color = Color.White, topLeft = Offset(w / 3f, 0f), size = Size(w / 3f, h))
                    drawRect(color = Color(0xFFEF4135), topLeft = Offset(2f * w / 3f, 0f), size = Size(w / 3f, h))
                }
                "it" -> {
                    // Italy (Green - White - Red)
                    drawRect(color = Color(0xFF009246), topLeft = Offset(0f, 0f), size = Size(w / 3f, h))
                    drawRect(color = Color.White, topLeft = Offset(w / 3f, 0f), size = Size(w / 3f, h))
                    drawRect(color = Color(0xFFCE2B37), topLeft = Offset(2f * w / 3f, 0f), size = Size(w / 3f, h))
                }
                "ru" -> {
                    // Russia (White - Blue - Red)
                    drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFF0039A6), topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFFD52B1E), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
                }
                "ja" -> {
                    // Japan (White with Red Sun)
                    drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawCircle(color = Color(0xFFBC002D), radius = w * 0.28f, center = Offset(w / 2f, h / 2f))
                }
                "ko" -> {
                    // South Korea (White with Taegeuk Red/Blue Circle)
                    drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawCircle(color = Color(0xFFCD2E3A), radius = w * 0.26f, center = Offset(w / 2f, h / 2f))
                    drawArc(color = Color(0xFF0047A0), startAngle = 0f, sweepAngle = 180f, useCenter = true,
                        topLeft = Offset(w * 0.24f, h * 0.24f), size = Size(w * 0.52f, h * 0.52f))
                }
                "zh", "cn" -> {
                    // China (Red with Golden Stars)
                    drawRect(color = Color(0xFFDE2910), topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawStar(center = Offset(w * 0.35f, h * 0.45f), radius = w * 0.16f, color = Color(0xFFFFDE00))
                    drawStar(center = Offset(w * 0.6f, h * 0.25f), radius = w * 0.05f, color = Color(0xFFFFDE00))
                    drawStar(center = Offset(w * 0.7f, h * 0.4f), radius = w * 0.05f, color = Color(0xFFFFDE00))
                    drawStar(center = Offset(w * 0.7f, h * 0.6f), radius = w * 0.05f, color = Color(0xFFFFDE00))
                }
                "ar", "sa" -> {
                    // Saudi Arabia / Arabic (Green with White Sword/Crest)
                    drawRect(color = Color(0xFF006C35), topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawRect(color = Color.White, topLeft = Offset(w * 0.25f, h * 0.55f), size = Size(w * 0.5f, h * 0.08f))
                    drawCircle(color = Color.White, radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.38f))
                }
                "pt" -> {
                    // Portugal (Green - Red with Golden Sphere)
                    drawRect(color = Color(0xFF046A38), topLeft = Offset(0f, 0f), size = Size(w * 0.4f, h))
                    drawRect(color = Color(0xFFDA291C), topLeft = Offset(w * 0.4f, 0f), size = Size(w * 0.6f, h))
                    drawCircle(color = Color(0xFFFFC72C), radius = w * 0.15f, center = Offset(w * 0.4f, h * 0.5f))
                    drawCircle(color = Color.White, radius = w * 0.08f, center = Offset(w * 0.4f, h * 0.5f))
                }
                "nl" -> {
                    // Netherlands (Red - White - Blue)
                    drawRect(color = Color(0xFFAD1D25), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                    drawRect(color = Color.White, topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFF1E4785), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
                }
                "sv", "se" -> {
                    // Sweden (Blue with Yellow Nordic Cross)
                    drawRect(color = Color(0xFF006AA7), topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawRect(color = Color(0xFFFECC00), topLeft = Offset(w * 0.32f, 0f), size = Size(w * 0.18f, h))
                    drawRect(color = Color(0xFFFECC00), topLeft = Offset(0f, h * 0.41f), size = Size(w, h * 0.18f))
                }
                "no" -> {
                    // Norway (Red, White, Blue Nordic Cross)
                    drawRect(color = Color(0xFFBA0C2F), topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawRect(color = Color.White, topLeft = Offset(w * 0.28f, 0f), size = Size(w * 0.24f, h))
                    drawRect(color = Color.White, topLeft = Offset(0f, h * 0.38f), size = Size(w, h * 0.24f))
                    drawRect(color = Color(0xFF00205B), topLeft = Offset(w * 0.34f, 0f), size = Size(w * 0.12f, h))
                    drawRect(color = Color(0xFF00205B), topLeft = Offset(0f, h * 0.44f), size = Size(w, h * 0.12f))
                }
                "fi" -> {
                    // Finland (White with Blue Nordic Cross)
                    drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawRect(color = Color(0xFF002F6C), topLeft = Offset(w * 0.3f, 0f), size = Size(w * 0.2f, h))
                    drawRect(color = Color(0xFF002F6C), topLeft = Offset(0f, h * 0.4f), size = Size(w, h * 0.2f))
                }
                "el", "gr" -> {
                    // Greece (Blue & White Stripes with Cross)
                    drawRect(color = Color(0xFF0D5EAF), topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawRect(color = Color.White, topLeft = Offset(0f, h * 0.2f), size = Size(w, h * 0.2f))
                    drawRect(color = Color.White, topLeft = Offset(0f, h * 0.6f), size = Size(w, h * 0.2f))
                    drawRect(color = Color(0xFF0D5EAF), topLeft = Offset(0f, 0f), size = Size(w * 0.45f, h * 0.5f))
                    drawRect(color = Color.White, topLeft = Offset(w * 0.18f, 0f), size = Size(w * 0.09f, h * 0.5f))
                    drawRect(color = Color.White, topLeft = Offset(0f, h * 0.2f), size = Size(w * 0.45f, h * 0.1f))
                }
                "pl" -> {
                    // Poland (White - Red)
                    drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h * 0.5f))
                    drawRect(color = Color(0xFFDC143C), topLeft = Offset(0f, h * 0.5f), size = Size(w, h * 0.5f))
                }
                "uk", "ua" -> {
                    // Ukraine (Blue - Yellow)
                    drawRect(color = Color(0xFF005BBB), topLeft = Offset(0f, 0f), size = Size(w, h * 0.5f))
                    drawRect(color = Color(0xFFFFD500), topLeft = Offset(0f, h * 0.5f), size = Size(w, h * 0.5f))
                }
                "hi", "in" -> {
                    // India (Saffron - White - Green with Ashoka Chakra)
                    drawRect(color = Color(0xFFFF9933), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                    drawRect(color = Color.White, topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                    drawRect(color = Color(0xFF138808), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
                    drawCircle(color = Color(0xFF000080), radius = w * 0.10f, center = Offset(w * 0.5f, h * 0.5f))
                }
                "br" -> {
                    // Brazil (Green, Yellow Diamond, Blue Globe)
                    drawRect(color = Color(0xFF009B3A), topLeft = Offset(0f, 0f), size = Size(w, h))
                    val diamond = Path().apply {
                        moveTo(w * 0.5f, h * 0.15f)
                        lineTo(w * 0.85f, h * 0.5f)
                        lineTo(w * 0.5f, h * 0.85f)
                        lineTo(w * 0.15f, h * 0.5f)
                        close()
                    }
                    drawPath(diamond, color = Color(0xFFFEDF00), style = Fill)
                    drawCircle(color = Color(0xFF002776), radius = w * 0.18f, center = Offset(w * 0.5f, h * 0.5f))
                }
                else -> {
                    // Clean multi-tone gradient flag badge for all other world languages
                    val baseColor = when (code.hashCode() % 6) {
                        0 -> Color(0xFF2563EB)
                        1 -> Color(0xFF059669)
                        2 -> Color(0xFFDC2626)
                        3 -> Color(0xFFD97706)
                        4 -> Color(0xFF7C3AED)
                        else -> Color(0xFF0891B2)
                    }
                    drawRect(color = baseColor, topLeft = Offset(0f, 0f), size = Size(w, h))
                    drawCircle(color = Color.White.copy(alpha = 0.25f), radius = w * 0.35f, center = Offset(w * 0.5f, h * 0.5f))
                }
            }
        }

        // For rare languages, display a neat, crisp 2-letter uppercase label on top
        if (code !in listOf("es", "en", "gb", "uk", "tr", "az", "de", "fr", "it", "ru", "ja", "ko", "zh", "cn", "ar", "sa", "pt", "nl", "sv", "se", "no", "fi", "el", "gr", "pl", "ua", "hi", "in", "br")) {
            Text(
                text = code.take(2).uppercase(),
                color = Color.White,
                fontSize = (size.value * 0.36f).sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(
    center: Offset,
    radius: Float,
    color: Color
) {
    val path = Path()
    val spikes = 5
    val step = PI / spikes
    var rot = -PI / 2.0

    for (i in 0 until spikes * 2) {
        val r = if (i % 2 == 0) radius else radius * 0.45f
        val x = center.x + (cos(rot) * r).toFloat()
        val y = center.y + (sin(rot) * r).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        rot += step
    }
    path.close()
    drawPath(path, color = color, style = Fill)
}
