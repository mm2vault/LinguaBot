package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserProfile
import com.example.data.model.DailyQuest
import com.example.data.model.RobotEmotion
import com.example.ui.components.CrownIcon
import com.example.ui.components.GemIcon
import com.example.ui.components.HeartIcon
import com.example.ui.components.LightningIcon
import com.example.ui.components.PurpleRobotView
import com.example.ui.components.ShieldIcon
import com.example.ui.components.StarBadgeIcon
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.GemTeal
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.RobotEyeAngryRed
import com.example.ui.theme.SuccessGreen

@Composable
fun RobotLabScreen(
    userProfile: UserProfile,
    currentEmotion: RobotEmotion,
    robotDialogue: String?,
    dailyQuests: List<DailyQuest>,
    onSetEmotion: (RobotEmotion) -> Unit,
    onSetVisorColor: (String) -> Unit,
    onSetChassisSkin: (String) -> Unit,
    onSetAntennaAccessory: (String) -> Unit,
    onSetChestBadge: (String) -> Unit,
    onRefillHearts: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCustomizationTab by remember { mutableIntStateOf(0) }
    val customizationTabs = listOf("Zırh & Renk", "Aksesuar", "Rozet", "Duygular")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        item {
            Text(
                text = "Robot Laboratuvarı & Gardırop",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = "Yol arkadaşın Byte'ı özelleştir ve donat",
                fontSize = 12.sp,
                color = RobotCyanGlow,
                modifier = Modifier.padding(top = 2.dp, bottom = 14.dp)
            )
        }

        // Live Interactive Robot Showcase Stage
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF231648)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PurpleRobotView(
                        size = 140.dp,
                        emotion = currentEmotion,
                        visorColorCode = userProfile.robotVisorColor,
                        chassisSkin = userProfile.robotChassisSkin,
                        antennaAccessory = userProfile.robotAntennaAccessory,
                        chestBadge = userProfile.robotChestBadge,
                        speechBubbleText = robotDialogue
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Byte v2.5 • Hazır ve Enerjik",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFCBD5E1)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Customization Categories Tab Row
        item {
            TabRow(
                selectedTabIndex = selectedCustomizationTab,
                containerColor = Color(0xFF221644),
                contentColor = PurpleBright,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedCustomizationTab]),
                        color = RobotCyanGlow
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
            ) {
                customizationTabs.forEachIndexed { index, tabTitle ->
                    Tab(
                        selected = selectedCustomizationTab == index,
                        onClick = { selectedCustomizationTab = index },
                        text = {
                            Text(
                                text = tabTitle,
                                fontSize = 11.sp,
                                fontWeight = if (selectedCustomizationTab == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedCustomizationTab == index) Color.White else Color(0xFF94A3B8)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
        }

        // Tab Content Panel
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF251A4D)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    when (selectedCustomizationTab) {
                        0 -> {
                            // 1. Zırh Rengi & Visor LED
                            Text("Gövde Zırh Deseni", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.height(8.dp))

                            val skins = listOf(
                                "classic" to "Klasik Mor",
                                "neon" to "Siber Neon",
                                "amethyst" to "Ametist",
                                "royal" to "Kraliyet Altın",
                                "magenta" to "Magenta"
                            )

                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(skins) { (skinKey, skinName) ->
                                    val isSelected = userProfile.robotChassisSkin == skinKey
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) PurplePrimary else Color(0xFF33235A),
                                        modifier = Modifier.clickable { onSetChassisSkin(skinKey) }
                                    ) {
                                        Text(
                                            text = skinName,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Göz & LED Rengi", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.height(8.dp))

                            val colors = listOf(
                                "cyan" to RobotCyanGlow,
                                "purple" to PurpleBright,
                                "lime" to Color(0xFF84CC16),
                                "amber" to Color(0xFFF59E0B),
                                "red" to RobotEyeAngryRed,
                                "pink" to Color(0xFFEC4899)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                colors.forEach { (code, col) ->
                                    val isSelected = userProfile.robotVisorColor == code
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(col)
                                            .border(
                                                width = if (isSelected) 3.dp else 1.dp,
                                                color = if (isSelected) Color.White else Color.Transparent,
                                                shape = CircleShape
                                            )
                                            .clickable { onSetVisorColor(code) }
                                    )
                                }
                            }
                        }

                        1 -> {
                            // 2. Başlık & Anten Aksesuarı
                            Text("Anten & Başlık Aksesuarı", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.height(10.dp))

                            val accessories = listOf(
                                "orb" to "Klasik Sinyal Küresi",
                                "dual" to "Çift Siber Anten",
                                "crown" to "Altın Kraliyet Tacı",
                                "lightning" to "Yıldırım Çubuğu",
                                "goggles" to "Siber VR Gözlük",
                                "cap" to "Mezuniyet Kepi"
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                accessories.forEach { (accKey, accName) ->
                                    val isSelected = userProfile.robotAntennaAccessory == accKey
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) PurplePrimary else Color(0xFF33235A),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onSetAntennaAccessory(accKey) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                when (accKey) {
                                                    "crown" -> CrownIcon(size = 18.dp)
                                                    "lightning" -> LightningIcon(size = 18.dp)
                                                    else -> StarBadgeIcon(size = 18.dp, color = RobotCyanGlow)
                                                }
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text(accName, color = Color.White, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                            }
                                            if (isSelected) {
                                                Text("Kullanımda", color = RobotCyanGlow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        2 -> {
                            // 3. Göğüs Reaktörü Rozeti
                            Text("Göğüs Reaktörü Rozeti", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.height(10.dp))

                            val badges = listOf(
                                "core" to "Standart LED Çekirdek",
                                "heart" to "Sibernetik Kalp",
                                "bolt" to "Şimşek Güç Rozeti",
                                "star" to "Poliglot Yıldız Rozeti",
                                "shield" to "Elmas Kalkan Rozeti"
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                badges.forEach { (badgeKey, badgeName) ->
                                    val isSelected = userProfile.robotChestBadge == badgeKey
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) PurplePrimary else Color(0xFF33235A),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onSetChestBadge(badgeKey) }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                when (badgeKey) {
                                                    "heart" -> HeartIcon(size = 18.dp)
                                                    "bolt" -> LightningIcon(size = 18.dp)
                                                    "star" -> StarBadgeIcon(size = 18.dp)
                                                    "shield" -> ShieldIcon(size = 18.dp)
                                                    else -> StarBadgeIcon(size = 16.dp, color = RobotCyanGlow)
                                                }
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text(badgeName, color = Color.White, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                            }
                                            if (isSelected) {
                                                Text("Kullanımda", color = RobotCyanGlow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        3 -> {
                            // 4. Duygular & Animasyon Testi
                            Text("Duygu & Reaksiyon Simülatörü", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                EmotionButton("Normal", currentEmotion == RobotEmotion.IDLE, { onSetEmotion(RobotEmotion.IDLE) }, Modifier.weight(1f))
                                EmotionButton("Mutlu", currentEmotion == RobotEmotion.HAPPY, { onSetEmotion(RobotEmotion.HAPPY) }, Modifier.weight(1f))
                                EmotionButton("Düşünceli", currentEmotion == RobotEmotion.THINKING, { onSetEmotion(RobotEmotion.THINKING) }, Modifier.weight(1f))
                                EmotionButton("Sinirli", currentEmotion == RobotEmotion.SASSY_ANGRY, { onSetEmotion(RobotEmotion.SASSY_ANGRY) }, Modifier.weight(1f), RobotEyeAngryRed)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Heart Refill Station
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF28184C)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    HeartIcon(size = 32.dp, isFilled = true)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Canları Doldur (${userProfile.hearts}/${userProfile.maxHearts})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "100 Mücevher ile 5 can yenile",
                            fontSize = 12.sp,
                            color = Color(0xFFCBD5E1)
                        )
                    }

                    Button(
                        onClick = onRefillHearts,
                        enabled = userProfile.hearts < 5 && userProfile.gems >= 100,
                        colors = ButtonDefaults.buttonColors(containerColor = GemTeal),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Doldur", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Daily Quests
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Günlük Görevler",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(dailyQuests) { quest ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF241648)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = quest.getTitle(userProfile.appUiLanguage),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GemIcon(size = 16.dp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "+${quest.gemReward}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GemTeal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    val prog = quest.currentProgress.toFloat() / quest.targetProgress
                    LinearProgressIndicator(
                        progress = { prog },
                        color = if (quest.isCompleted) SuccessGreen else PurplePrimary,
                        trackColor = Color(0xFF140D2A),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${quest.currentProgress}/${quest.targetProgress}",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmotionButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = PurplePrimary
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) activeColor else Color(0xFF33235A),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
