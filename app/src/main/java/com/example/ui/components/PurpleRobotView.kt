package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RobotEmotion
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurpleDark
import com.example.ui.theme.PurpleDeep
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.RobotEyeAngryRed
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PurpleRobotView(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    emotion: RobotEmotion = RobotEmotion.IDLE,
    visorColorCode: String = "cyan",
    chassisSkin: String = "classic",
    antennaAccessory: String = "orb",
    chestBadge: String = "core",
    speechBubbleText: String? = null,
    onClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "robot_enhanced_anim")

    // Hover bobbing
    val hoverOffset by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "hover"
    )

    // Gentle chassis tilt during hover
    val chassisTilt by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tilt"
    )

    // Antenna wiggle
    val antennaWiggle by infiniteTransition.animateFloat(
        initialValue = -7f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "antenna"
    )

    // Antenna energy wave pulse (0f to 1f)
    val antennaPulse by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "antennaPulse"
    )

    // Thruster / Levitation ring expansion underneath
    val thrusterPulse by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "thrusterPulse"
    )

    // Periodic blinking (eye height scale)
    val blinkAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink"
    )
    val eyeScaleY = if (blinkAnim < 0.18f && emotion == RobotEmotion.IDLE) 0.15f else 1f

    // Steam puffs for SASSY / ANGRY
    val steamPuff by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(750, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "steam"
    )

    // Laser scan sweep for THINKING
    val scanSweep by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(950, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scan"
    )

    // Orbit angle for thinking holographic nodes
    val orbitAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit"
    )

    val tapBounce = remember { Animatable(0f) }
    val tapRotate = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // Angry tremble shake
    val angryTremble = if (emotion == RobotEmotion.SASSY_ANGRY) {
        (sin(System.currentTimeMillis() / 20.0) * 4).toFloat()
    } else 0f

    // Resolved Visor Color
    val resolvedVisorColor = when {
        emotion == RobotEmotion.SASSY_ANGRY -> RobotEyeAngryRed
        visorColorCode == "lime" -> Color(0xFF84CC16)
        visorColorCode == "amber" -> Color(0xFFF59E0B)
        visorColorCode == "purple" -> PurpleBright
        visorColorCode == "red" -> RobotEyeAngryRed
        visorColorCode == "pink" -> Color(0xFFEC4899)
        else -> RobotCyanGlow
    }

    // Resolved Chassis Colors based on Skin
    val (chassisTop, chassisMid, chassisBottom) = when (chassisSkin) {
        "neon" -> Triple(Color(0xFFC084FC), Color(0xFF9333EA), Color(0xFF3B0764))
        "amethyst" -> Triple(Color(0xFFA855F7), Color(0xFF6B21A8), Color(0xFF1E0A3C))
        "royal" -> Triple(Color(0xFF8B5CF6), Color(0xFF5B21B6), Color(0xFF1E103A))
        "magenta" -> Triple(Color(0xFFF472B6), Color(0xFFDB2777), Color(0xFF700A38))
        else -> Triple(PurpleLight, PurplePrimary, PurpleDeep)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        // Speech Bubble if present
        if (!speechBubbleText.isNullOrBlank()) {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (emotion == RobotEmotion.SASSY_ANGRY) Color(0xFF450A0A) else PurplePrimary,
                shadowElevation = 6.dp,
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .offset { IntOffset(0, (hoverOffset * 0.4f).toInt()) }
            ) {
                Text(
                    text = speechBubbleText,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset { IntOffset(angryTremble.toInt(), (hoverOffset + tapBounce.value).toInt()) }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    scope.launch {
                        tapBounce.animateTo(-20f, tween(120, easing = FastOutSlowInEasing))
                        tapRotate.animateTo(12f, tween(80, easing = FastOutSlowInEasing))
                        tapRotate.animateTo(-8f, tween(90, easing = FastOutSlowInEasing))
                        tapRotate.animateTo(0f, tween(120, easing = FastOutSlowInEasing))
                        tapBounce.animateTo(0f, tween(180, easing = FastOutSlowInEasing))
                    }
                    onClick()
                }
        ) {
            Canvas(modifier = Modifier.size(size)) {
                val w = this.size.width
                val h = this.size.height

                // 1. Levitation Thruster Plasma Glow under robot chassis
                val thrustCx = w * 0.5f
                val thrustCy = h * 0.88f
                val thrustRx = (w * 0.28f) * thrusterPulse
                val thrustRy = (h * 0.08f) * thrusterPulse
                drawOval(
                    color = resolvedVisorColor.copy(alpha = 0.35f * (1.2f - thrusterPulse)),
                    topLeft = Offset(thrustCx - thrustRx, thrustCy - thrustRy),
                    size = Size(thrustRx * 2, thrustRy * 2)
                )
                drawOval(
                    color = Color.White.copy(alpha = 0.5f * (1.2f - thrusterPulse)),
                    topLeft = Offset(thrustCx - thrustRx * 0.5f, thrustCy - thrustRy * 0.5f),
                    size = Size(thrustRx, thrustRy)
                )

                // 2. Steam Clouds & Sparks for SASSY_ANGRY
                if (emotion == RobotEmotion.SASSY_ANGRY) {
                    val steamAlpha = (1f - steamPuff).coerceIn(0f, 1f)
                    val steamY1 = h * 0.14f - (steamPuff * 26.dp.toPx())
                    val steamY2 = h * 0.10f - (steamPuff * 32.dp.toPx())

                    // Left smoke puff
                    drawCircle(
                        color = Color(0xFFE2E8F0).copy(alpha = steamAlpha * 0.85f),
                        radius = 6.dp.toPx() * (1f + steamPuff * 0.8f),
                        center = Offset(w * 0.28f, steamY1)
                    )
                    // Right smoke puff
                    drawCircle(
                        color = Color(0xFFE2E8F0).copy(alpha = steamAlpha * 0.85f),
                        radius = 8.dp.toPx() * (1f + steamPuff * 0.8f),
                        center = Offset(w * 0.72f, steamY1 - 2.dp.toPx())
                    )
                    // Center high puff
                    drawCircle(
                        color = Color(0xFFCBD5E1).copy(alpha = steamAlpha * 0.7f),
                        radius = 7.dp.toPx() * (1f + steamPuff * 0.6f),
                        center = Offset(w * 0.5f, steamY2)
                    )

                    // Electric angry sparks popping out
                    val sparkAngle = (steamPuff * 360f)
                    val sparkDist = w * 0.35f
                    val spx = w * 0.5f + (cos(Math.toRadians(sparkAngle.toDouble())) * sparkDist).toFloat()
                    val spy = h * 0.2f + (sin(Math.toRadians(sparkAngle.toDouble())) * (sparkDist * 0.5f)).toFloat()
                    drawCircle(color = Color(0xFFFBBF24), radius = 2.5.dp.toPx(), center = Offset(spx, spy))
                }

                // 3. Holographic Orbit Nodes for THINKING
                if (emotion == RobotEmotion.THINKING) {
                    val rad = Math.toRadians(orbitAngle.toDouble())
                    val ox = w * 0.5f + (cos(rad) * w * 0.42f).toFloat()
                    val oy = h * 0.5f + (sin(rad) * h * 0.22f).toFloat()
                    drawCircle(color = resolvedVisorColor, radius = 3.5.dp.toPx(), center = Offset(ox, oy))
                    drawCircle(color = Color.White.copy(alpha = 0.7f), radius = 1.8.dp.toPx(), center = Offset(ox, oy))
                }

                // Base Head / Chassis Coordinates
                val headW = w * 0.74f
                val headH = h * 0.56f
                val headX = (w - headW) / 2f
                val headY = h * 0.28f

                // Chassis Tilt Transform
                val appliedRotation = (if (emotion == RobotEmotion.SASSY_ANGRY) 0f else chassisTilt) + tapRotate.value
                rotate(appliedRotation, pivot = Offset(w / 2f, headY + headH / 2f)) {

                    // 4. Antenna / Head Accessory
                    val antennaBaseX = w * 0.5f
                    val antennaBaseY = headY + 2.dp.toPx()
                    val antennaTopX = antennaBaseX + (if (emotion == RobotEmotion.SASSY_ANGRY) antennaWiggle * 1.6f else antennaWiggle)
                    val antennaTopY = h * 0.12f

                    when (antennaAccessory) {
                        "crown" -> {
                            // Royal Golden Crown atop head
                            val crownW = headW * 0.52f
                            val crownH = headH * 0.32f
                            val crownX = (w - crownW) / 2f
                            val crownY = headY - crownH * 0.85f

                            val crownPath = Path().apply {
                                moveTo(crownX, crownY + crownH)
                                lineTo(crownX, crownY + crownH * 0.3f)
                                lineTo(crownX + crownW * 0.25f, crownY + crownH * 0.65f)
                                lineTo(crownX + crownW * 0.5f, crownY)
                                lineTo(crownX + crownW * 0.75f, crownY + crownH * 0.65f)
                                lineTo(crownX + crownW, crownY + crownH * 0.3f)
                                lineTo(crownX + crownW, crownY + crownH)
                                close()
                            }
                            drawPath(crownPath, color = GoldCrown, style = Fill)
                            drawCircle(color = Color(0xFFEF4444), radius = 3.dp.toPx(), center = Offset(crownX + crownW * 0.5f, crownY))
                        }

                        "dual" -> {
                            // Twin Cyber Antennas
                            val lTopX = headX + headW * 0.25f + antennaWiggle
                            val rTopX = headX + headW * 0.75f - antennaWiggle
                            val topY = h * 0.13f

                            drawLine(color = PurpleBright, start = Offset(headX + headW * 0.25f, headY), end = Offset(lTopX, topY), strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)
                            drawLine(color = PurpleBright, start = Offset(headX + headW * 0.75f, headY), end = Offset(rTopX, topY), strokeWidth = 3.dp.toPx(), cap = StrokeCap.Round)

                            drawCircle(color = resolvedVisorColor, radius = 4.5.dp.toPx(), center = Offset(lTopX, topY))
                            drawCircle(color = resolvedVisorColor, radius = 4.5.dp.toPx(), center = Offset(rTopX, topY))
                        }

                        "lightning" -> {
                            // Electric Lightning Rod Antenna
                            val boltPath = Path().apply {
                                moveTo(antennaBaseX, antennaBaseY)
                                lineTo(antennaBaseX - 4.dp.toPx(), antennaBaseY - 12.dp.toPx())
                                lineTo(antennaBaseX + 2.dp.toPx(), antennaBaseY - 12.dp.toPx())
                                lineTo(antennaBaseX - 3.dp.toPx(), antennaBaseY - 26.dp.toPx())
                                lineTo(antennaBaseX + 6.dp.toPx(), antennaBaseY - 16.dp.toPx())
                                lineTo(antennaBaseX, antennaBaseY - 16.dp.toPx())
                                close()
                            }
                            drawPath(boltPath, color = Color(0xFFFBBF24), style = Fill)
                        }

                        "cap" -> {
                            // Graduation Scholar Cap
                            val capW = headW * 0.65f
                            val capH = headH * 0.2f
                            val capX = (w - capW) / 2f
                            val capY = headY - capH * 0.7f

                            val capPath = Path().apply {
                                moveTo(capX + capW * 0.5f, capY)
                                lineTo(capX + capW, capY + capH * 0.5f)
                                lineTo(capX + capW * 0.5f, capY + capH)
                                lineTo(capX, capY + capH * 0.5f)
                                close()
                            }
                            drawPath(capPath, color = Color(0xFF1E1B4B), style = Fill)
                            drawPath(capPath, color = GoldCrown, style = Stroke(width = 1.5.dp.toPx()))
                            // Tassel
                            drawLine(color = GoldCrown, start = Offset(capX + capW * 0.5f, capY + capH * 0.5f), end = Offset(capX + capW * 0.9f, capY + capH * 1.3f), strokeWidth = 2.dp.toPx())
                        }

                        else -> {
                            // Classic Orb Beacon with Wave Pulse
                            drawLine(
                                color = PurpleBright,
                                start = Offset(antennaBaseX, antennaBaseY),
                                end = Offset(antennaTopX, antennaTopY),
                                strokeWidth = 3.5.dp.toPx(),
                                cap = StrokeCap.Round
                            )

                            // Wave pulse running along antenna
                            val pulseY = antennaBaseY - (antennaBaseY - antennaTopY) * antennaPulse
                            val pulseX = antennaBaseX + (antennaTopX - antennaBaseX) * antennaPulse
                            drawCircle(color = Color.White.copy(alpha = 0.8f), radius = 2.dp.toPx(), center = Offset(pulseX, pulseY))

                            val beaconColor = if (emotion == RobotEmotion.SASSY_ANGRY) RobotEyeAngryRed else resolvedVisorColor
                            drawCircle(color = beaconColor, radius = 5.5.dp.toPx(), center = Offset(antennaTopX, antennaTopY))
                            drawCircle(color = beaconColor.copy(alpha = 0.4f), radius = 10.dp.toPx(), center = Offset(antennaTopX, antennaTopY))
                        }
                    }

                    // 5. Ears / Side Cylindrical Bolts
                    val earW = w * 0.08f
                    val earH = h * 0.2f
                    val earY = headY + headH * 0.35f
                    // Left Ear
                    drawRoundRect(
                        color = PurpleDark,
                        topLeft = Offset(headX - earW * 0.8f, earY),
                        size = Size(earW, earH),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                    // Right Ear
                    drawRoundRect(
                        color = PurpleDark,
                        topLeft = Offset(headX + headW - earW * 0.2f, earY),
                        size = Size(earW, earH),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )

                    // 6. Main Chassis Body (Chibi Rounded Box with Skin Gradient)
                    val chassisBrush = Brush.verticalGradient(
                        colors = listOf(chassisTop, chassisMid, chassisBottom),
                        startY = headY,
                        endY = headY + headH
                    )

                    drawRoundRect(
                        brush = chassisBrush,
                        topLeft = Offset(headX, headY),
                        size = Size(headW, headH),
                        cornerRadius = CornerRadius(22.dp.toPx())
                    )

                    // Royal gold or cyber border if skin is royal
                    val borderColor = if (chassisSkin == "royal") GoldCrown.copy(alpha = 0.7f) else PurpleBright.copy(alpha = 0.5f)
                    drawRoundRect(
                        color = borderColor,
                        topLeft = Offset(headX, headY),
                        size = Size(headW, headH),
                        cornerRadius = CornerRadius(22.dp.toPx()),
                        style = Stroke(width = if (chassisSkin == "royal") 2.5.dp.toPx() else 1.8.dp.toPx())
                    )

                    // 7. Visor Screen
                    val visorW = headW * 0.82f
                    val visorH = headH * 0.52f
                    val visorX = headX + (headW - visorW) / 2f
                    val visorY = headY + headH * 0.2f

                    drawRoundRect(
                        color = Color(0xFF130B29),
                        topLeft = Offset(visorX, visorY),
                        size = Size(visorW, visorH),
                        cornerRadius = CornerRadius(14.dp.toPx())
                    )
                    // Visor glowing border
                    drawRoundRect(
                        color = resolvedVisorColor.copy(alpha = 0.28f),
                        topLeft = Offset(visorX, visorY),
                        size = Size(visorW, visorH),
                        cornerRadius = CornerRadius(14.dp.toPx()),
                        style = Stroke(width = 1.5.dp.toPx())
                    )

                    // Goggles / VR Visor Accessory if equipped
                    if (antennaAccessory == "goggles") {
                        drawRoundRect(
                            color = Color(0xFF0284C7).copy(alpha = 0.5f),
                            topLeft = Offset(visorX - 2.dp.toPx(), visorY - 4.dp.toPx()),
                            size = Size(visorW + 4.dp.toPx(), visorH * 0.5f),
                            cornerRadius = CornerRadius(8.dp.toPx()),
                            style = Stroke(width = 3.dp.toPx())
                        )
                    }

                    // 8. Eyes & Dynamic Emotion Expressions
                    val leftEyeCenterX = visorX + visorW * 0.32f
                    val rightEyeCenterX = visorX + visorW * 0.68f
                    val eyeCenterY = visorY + visorH * 0.5f

                    when (emotion) {
                        RobotEmotion.HAPPY -> {
                            // Cute cheerful arches ^ ^ with blinking bounce
                            val archPathL = Path().apply {
                                moveTo(leftEyeCenterX - 9.dp.toPx(), eyeCenterY + 4.dp.toPx())
                                cubicTo(
                                    leftEyeCenterX - 5.dp.toPx(), eyeCenterY - 8.dp.toPx(),
                                    leftEyeCenterX + 5.dp.toPx(), eyeCenterY - 8.dp.toPx(),
                                    leftEyeCenterX + 9.dp.toPx(), eyeCenterY + 4.dp.toPx()
                                )
                            }
                            val archPathR = Path().apply {
                                moveTo(rightEyeCenterX - 9.dp.toPx(), eyeCenterY + 4.dp.toPx())
                                cubicTo(
                                    rightEyeCenterX - 5.dp.toPx(), eyeCenterY - 8.dp.toPx(),
                                    rightEyeCenterX + 5.dp.toPx(), eyeCenterY - 8.dp.toPx(),
                                    rightEyeCenterX + 9.dp.toPx(), eyeCenterY + 4.dp.toPx()
                                )
                            }
                            drawPath(archPathL, color = resolvedVisorColor, style = Stroke(width = 4.5.dp.toPx(), cap = StrokeCap.Round))
                            drawPath(archPathR, color = resolvedVisorColor, style = Stroke(width = 4.5.dp.toPx(), cap = StrokeCap.Round))

                            // Glowing pink blush cheeks
                            drawCircle(color = Color(0xFFF472B6).copy(alpha = 0.7f), radius = 4.5.dp.toPx(), center = Offset(visorX + visorW * 0.18f, eyeCenterY + 8.dp.toPx()))
                            drawCircle(color = Color(0xFFF472B6).copy(alpha = 0.7f), radius = 4.5.dp.toPx(), center = Offset(visorX + visorW * 0.82f, eyeCenterY + 8.dp.toPx()))
                        }

                        RobotEmotion.SASSY_ANGRY -> {
                            // Angry angled sharp LED slants \ /
                            val slantL = Path().apply {
                                moveTo(leftEyeCenterX - 10.dp.toPx(), eyeCenterY - 6.dp.toPx())
                                lineTo(leftEyeCenterX + 10.dp.toPx(), eyeCenterY + 5.dp.toPx())
                            }
                            val slantR = Path().apply {
                                moveTo(rightEyeCenterX - 10.dp.toPx(), eyeCenterY + 5.dp.toPx())
                                lineTo(rightEyeCenterX + 10.dp.toPx(), eyeCenterY - 6.dp.toPx())
                            }
                            drawPath(slantL, color = RobotEyeAngryRed, style = Stroke(width = 5.5.dp.toPx(), cap = StrokeCap.Round))
                            drawPath(slantR, color = RobotEyeAngryRed, style = Stroke(width = 5.5.dp.toPx(), cap = StrokeCap.Round))

                            // Overheated glowing red cheeks
                            drawCircle(color = RobotEyeAngryRed.copy(alpha = 0.85f), radius = 5.5.dp.toPx(), center = Offset(visorX + visorW * 0.16f, eyeCenterY + 8.dp.toPx()))
                            drawCircle(color = RobotEyeAngryRed.copy(alpha = 0.85f), radius = 5.5.dp.toPx(), center = Offset(visorX + visorW * 0.84f, eyeCenterY + 8.dp.toPx()))
                        }

                        RobotEmotion.THINKING -> {
                            // Squinted analytical eyes
                            drawCircle(color = resolvedVisorColor, radius = 6.dp.toPx(), center = Offset(leftEyeCenterX, eyeCenterY))
                            drawRoundRect(
                                color = resolvedVisorColor,
                                topLeft = Offset(rightEyeCenterX - 7.dp.toPx(), eyeCenterY - 3.5.dp.toPx()),
                                size = Size(14.dp.toPx(), 7.dp.toPx()),
                                cornerRadius = CornerRadius(3.5.dp.toPx())
                            )
                            // Laser scan beam
                            drawLine(
                                color = resolvedVisorColor.copy(alpha = 0.8f),
                                start = Offset(visorX + visorW * scanSweep, visorY + 2.dp.toPx()),
                                end = Offset(visorX + visorW * scanSweep, visorY + visorH - 2.dp.toPx()),
                                strokeWidth = 2.5.dp.toPx()
                            )
                        }

                        RobotEmotion.CELEBRATING -> {
                            // Polyglot Celebration Star Eyes
                            drawStar(center = Offset(leftEyeCenterX, eyeCenterY), radius = 9.dp.toPx(), color = GoldCrown)
                            drawStar(center = Offset(rightEyeCenterX, eyeCenterY), radius = 9.dp.toPx(), color = GoldCrown)
                            // Celebration sparkle dot
                            drawCircle(color = resolvedVisorColor, radius = 3.dp.toPx(), center = Offset(visorX + visorW * 0.5f, eyeCenterY - 6.dp.toPx()))
                        }

                        RobotEmotion.IDLE -> {
                            // Glowing oval LED eyes with smooth blink cycle!
                            val eyeH = 16.dp.toPx() * eyeScaleY
                            val eyeW = 12.dp.toPx()
                            drawRoundRect(
                                color = resolvedVisorColor,
                                topLeft = Offset(leftEyeCenterX - eyeW / 2f, eyeCenterY - eyeH / 2f),
                                size = Size(eyeW, eyeH),
                                cornerRadius = CornerRadius(5.dp.toPx())
                            )
                            drawRoundRect(
                                color = resolvedVisorColor,
                                topLeft = Offset(rightEyeCenterX - eyeW / 2f, eyeCenterY - eyeH / 2f),
                                size = Size(eyeW, eyeH),
                                cornerRadius = CornerRadius(5.dp.toPx())
                            )
                            // White reflection gloss
                            if (eyeScaleY > 0.4f) {
                                drawCircle(color = Color.White.copy(alpha = 0.85f), radius = 2.2.dp.toPx(), center = Offset(leftEyeCenterX - 2.dp.toPx(), eyeCenterY - 4.dp.toPx()))
                                drawCircle(color = Color.White.copy(alpha = 0.85f), radius = 2.2.dp.toPx(), center = Offset(rightEyeCenterX - 2.dp.toPx(), eyeCenterY - 4.dp.toPx()))
                            }
                        }
                    }

                    // 9. Chest Emblem / Badge Customization
                    val chestY = headY + headH * 0.84f
                    val chestCx = w * 0.5f

                    when (chestBadge) {
                        "heart" -> {
                            // Mechanical heart badge
                            val hw = 10.dp.toPx()
                            val hpath = Path().apply {
                                moveTo(chestCx, chestY + 4.dp.toPx())
                                cubicTo(chestCx - hw, chestY - 4.dp.toPx(), chestCx - hw, chestY - 8.dp.toPx(), chestCx, chestY - 4.dp.toPx())
                                cubicTo(chestCx + hw, chestY - 8.dp.toPx(), chestCx + hw, chestY - 4.dp.toPx(), chestCx, chestY + 4.dp.toPx())
                                close()
                            }
                            drawPath(hpath, color = Color(0xFFE11D48), style = Fill)
                        }

                        "bolt" -> {
                            // Electric lightning bolt crest
                            val bpath = Path().apply {
                                moveTo(chestCx + 2.dp.toPx(), chestY - 7.dp.toPx())
                                lineTo(chestCx - 5.dp.toPx(), chestY)
                                lineTo(chestCx, chestY)
                                lineTo(chestCx - 3.dp.toPx(), chestY + 7.dp.toPx())
                                lineTo(chestCx + 5.dp.toPx(), chestY - 1.dp.toPx())
                                lineTo(chestCx + 1.dp.toPx(), chestY - 1.dp.toPx())
                                close()
                            }
                            drawPath(bpath, color = Color(0xFFFBBF24), style = Fill)
                        }

                        "star" -> {
                            drawStar(center = Offset(chestCx, chestY), radius = 6.dp.toPx(), color = GoldCrown)
                        }

                        "shield" -> {
                            val sw = 7.dp.toPx()
                            val shpath = Path().apply {
                                moveTo(chestCx, chestY - 6.dp.toPx())
                                lineTo(chestCx + sw, chestY - 3.dp.toPx())
                                lineTo(chestCx + sw * 0.8f, chestY + 3.dp.toPx())
                                lineTo(chestCx, chestY + 7.dp.toPx())
                                lineTo(chestCx - sw * 0.8f, chestY + 3.dp.toPx())
                                lineTo(chestCx - sw, chestY - 3.dp.toPx())
                                close()
                            }
                            drawPath(shpath, color = Color(0xFF0284C7), style = Fill)
                        }

                        else -> {
                            // Core LED reactor lights
                            val dotR = 2.5.dp.toPx()
                            drawCircle(color = PurpleLight.copy(alpha = 0.7f), radius = dotR, center = Offset(w * 0.44f, chestY))
                            drawCircle(color = resolvedVisorColor, radius = dotR * 1.2f, center = Offset(w * 0.50f, chestY))
                            drawCircle(color = PurpleLight.copy(alpha = 0.7f), radius = dotR, center = Offset(w * 0.56f, chestY))
                        }
                    }
                }
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawStar(
    center: Offset,
    radius: Float,
    color: Color
) {
    val rIn = radius * 0.45f
    val path = Path().apply {
        for (i in 0 until 5) {
            val angleOut = Math.toRadians((i * 72 - 90).toDouble())
            val xOut = (center.x + radius * cos(angleOut)).toFloat()
            val yOut = (center.y + radius * sin(angleOut)).toFloat()
            if (i == 0) moveTo(xOut, yOut) else lineTo(xOut, yOut)

            val angleIn = Math.toRadians((i * 72 + 36 - 90).toDouble())
            val xIn = (center.x + rIn * cos(angleIn)).toFloat()
            val yIn = (center.y + rIn * sin(angleIn)).toFloat()
            lineTo(xIn, yIn)
        }
        close()
    }
    drawPath(path, color = color, style = Fill)
}
