package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.UserProfile
import com.example.ui.components.FlameIcon
import com.example.ui.components.GemIcon
import com.example.ui.components.ShieldIcon
import com.example.ui.components.StarBadgeIcon
import com.example.ui.theme.GemTeal
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.StreakFlameColor
import com.example.ui.theme.SuccessGreen

@Composable
fun StreakHubDialog(
    userProfile: UserProfile,
    onBuyStreakFreeze: () -> Unit,
    onStartLesson: () -> Unit,
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "flame_pulse")
    val flameScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameScale"
    )

    val daysOfWeek = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmt", "Paz")
    val mask = userProfile.weeklyStreakMask.split(",").map { it.trim() == "1" }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E133D)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Günlük Seri Kulübü",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF94A3B8))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pulsing Flame Mascot
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(90.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .scale(flameScale)
                            .clip(CircleShape)
                            .background(StreakFlameColor.copy(alpha = 0.2f))
                    )
                    FlameIcon(size = 54.dp, modifier = Modifier.scale(flameScale))
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${userProfile.streakDays} Günlük Seri!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = StreakFlameColor
                )
                Text(
                    text = "Alevini canlı tutmak için her gün 1 ders tamamla!",
                    fontSize = 12.sp,
                    color = Color(0xFFCBD5E1),
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Weekly Calendar Tracker Row
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF2B1C54),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        daysOfWeek.forEachIndexed { index, day ->
                            val isActive = mask.getOrNull(index) == true
                            val isToday = index == 3 // Thursday (Perşembe)

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = day,
                                    fontSize = 11.sp,
                                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isToday) RobotCyanGlow else Color(0xFF94A3B8)
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isActive -> StreakFlameColor
                                                isToday -> Color(0xFF4C2A78)
                                                else -> Color(0xFF1E143B)
                                            }
                                        )
                                        .border(
                                            width = if (isToday) 2.dp else 0.dp,
                                            color = if (isToday) RobotCyanGlow else Color.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isActive) {
                                        FlameIcon(size = 18.dp, color = Color.White)
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF64748B))
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Streak Freeze Safeguard Card
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF281C50)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        ShieldIcon(size = 28.dp, color = Color(0xFF38BDF8))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Seri Kalkanı (${userProfile.streakFreezeCount} Hazır)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Bir gün kaçırsan bile serin kırılmaz.",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        Button(
                            onClick = onBuyStreakFreeze,
                            enabled = userProfile.gems >= 150 && userProfile.streakFreezeCount < 2,
                            colors = ButtonDefaults.buttonColors(containerColor = GemTeal),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                GemIcon(size = 14.dp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("150", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Milestone Badge Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MilestonePill(
                        label = "3 Gün",
                        isAchieved = userProfile.streakDays >= 3,
                        modifier = Modifier.weight(1f)
                    )
                    MilestonePill(
                        label = "7 Gün",
                        isAchieved = userProfile.streakDays >= 7,
                        modifier = Modifier.weight(1f)
                    )
                    MilestonePill(
                        label = "14 Gün",
                        isAchieved = userProfile.streakDays >= 14,
                        modifier = Modifier.weight(1f)
                    )
                    MilestonePill(
                        label = "30 Gün",
                        isAchieved = userProfile.streakDays >= 30,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action button: Extend streak by practicing
                Button(
                    onClick = {
                        onDismiss()
                        onStartLesson()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = "Seriyi Uzat: Ders Başlat",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun MilestonePill(
    label: String,
    isAchieved: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isAchieved) Color(0xFF1E3A2B) else Color(0xFF251A46),
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            StarBadgeIcon(size = 16.dp, color = if (isAchieved) GoldCrown else Color(0xFF64748B))
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isAchieved) SuccessGreen else Color(0xFF94A3B8)
            )
        }
    }
}
