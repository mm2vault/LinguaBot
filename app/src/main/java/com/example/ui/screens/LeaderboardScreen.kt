package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LeaderboardUser
import com.example.ui.components.CrownIcon
import com.example.ui.components.FlameIcon
import com.example.ui.components.StarBadgeIcon
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.StreakFlameColor

@Composable
fun LeaderboardScreen(
    users: List<LeaderboardUser>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // League Header Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF28154D)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    CrownIcon(size = 40.dp, color = GoldCrown)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "Ametist Ligi",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "İlk 3 öğrenci bir üst lige terfi eder!",
                            fontSize = 12.sp,
                            color = PurpleBright
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Leaderboard List
        items(users) { user ->
            val isUser = user.isCurrentUser
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUser) Color(0xFF381F6D) else Color(0xFF20163E)
                ),
                border = if (isUser) androidx.compose.foundation.BorderStroke(1.5.dp, PurpleBright) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                ) {
                    // Rank badge
                    Box(
                        modifier = Modifier.size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        when (user.rank) {
                            1 -> CrownIcon(size = 22.dp, color = GoldCrown)
                            2 -> StarBadgeIcon(size = 20.dp, color = Color(0xFF94A3B8))
                            3 -> StarBadgeIcon(size = 20.dp, color = Color(0xFFB45309))
                            else -> Text(
                                text = user.rank.toString(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Avatar Circle
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(user.avatarTintHex)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.take(1).uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // User Name
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = user.name,
                            fontWeight = if (isUser) FontWeight.ExtraBold else FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FlameIcon(size = 14.dp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${user.streak} gün seri",
                                fontSize = 11.sp,
                                color = StreakFlameColor
                            )
                        }
                    }

                    // XP
                    Text(
                        text = "${user.xp} XP",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = GoldCrown
                    )
                }
            }
        }
    }
}
