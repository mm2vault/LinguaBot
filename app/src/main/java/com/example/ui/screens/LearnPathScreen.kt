package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.data.local.UserProfile
import com.example.data.model.LanguageCatalog
import com.example.data.model.Lesson
import com.example.data.model.RobotEmotion
import com.example.service.TtsHelper
import com.example.ui.components.CrownIcon
import com.example.ui.components.LanguageFlagBadge
import com.example.ui.components.PurpleRobotView
import com.example.ui.components.StarBadgeIcon
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurpleDark
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.SuccessGreen

@Composable
fun LearnPathScreen(
    userProfile: UserProfile,
    lessons: List<Lesson>,
    robotEmotion: RobotEmotion,
    robotDialogue: String?,
    onLessonSelected: (Lesson) -> Unit,
    onRobotPoke: () -> Unit,
    onSwitchLanguage: (String) -> Unit = {},
    onOpenLanguagePicker: () -> Unit = {},
    tts: TtsHelper? = null,
    modifier: Modifier = Modifier
) {
    val completedSet = userProfile.completedLessonsIds.split(",").filter { it.isNotBlank() }.toSet()
    val activeLanguage = LanguageCatalog.findByCode(userProfile.activeLanguageCode)
    var showFlashcards by remember { mutableStateOf(false) }

    if (showFlashcards) {
        FlashcardsDialog(
            languageCode = userProfile.activeLanguageCode,
            languageName = activeLanguage.getDisplayName(userProfile.appUiLanguage),
            uiLang = userProfile.appUiLanguage,
            tts = tts,
            onDismiss = { showFlashcards = false }
        )
    }

    // Pulse animation for active node
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val popularLanguages = listOf(
        "es" to "İspanyolca",
        "en" to "İngilizce",
        "de" to "Almanca",
        "fr" to "Fransızca",
        "it" to "İtalyanca",
        "tr" to "Türkçe",
        "az" to "Azərbaycan",
        "ru" to "Rusça",
        "ja" to "Japonca",
        "ko" to "Korece",
        "ar" to "Arapça",
        "zh" to "Çince"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Quick Language Switcher Bar
        item {
            Column(modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(popularLanguages) { (code, _) ->
                        val isSelected = userProfile.activeLanguageCode.equals(code, ignoreCase = true)
                        val lang = LanguageCatalog.findByCode(code)
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) PurplePrimary else Color(0xFF231548),
                            border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, RobotCyanGlow) else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B2768)),
                            modifier = Modifier.clickable { onSwitchLanguage(code) }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = lang.flagEmoji, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(5.dp))
                                LanguageFlagBadge(languageCode = code, size = 16.dp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = lang.getDisplayName(userProfile.appUiLanguage),
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    item {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF3B2768),
                            border = androidx.compose.foundation.BorderStroke(1.dp, RobotCyanGlow),
                            modifier = Modifier.clickable { onOpenLanguagePicker() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "More", tint = RobotCyanGlow, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "+ 100 Dil",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RobotCyanGlow
                                )
                            }
                        }
                    }
                }
            }
        }

        // Hero Unit Banner
        item {
            UnitHeaderBanner(
                activeLanguageName = activeLanguage.getDisplayName(userProfile.appUiLanguage),
                languageCode = userProfile.activeLanguageCode,
                completedCount = lessons.count { completedSet.contains(it.id) },
                totalCount = lessons.size
            )
        }

        // Flashcards & Vocabulary Vault Button
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF281850),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4C2F8A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { showFlashcards = true }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.MenuBook, contentDescription = "Flashcards", tint = RobotCyanGlow, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (userProfile.appUiLanguage == "az") "Flaş Kartlar & Sözlük Sandığı" else if (userProfile.appUiLanguage == "en") "Vocabulary Vault & Flashcards" else "Kelime Kasası & Flaş Kartlar",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                        Text(
                            text = if (userProfile.appUiLanguage == "az") "Doğal insan səsi ilə sözləri təkrar et" else if (userProfile.appUiLanguage == "en") "Practice words with natural human voice" else "Doğal insan sesiyle kelimeleri tekrar et",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = PurplePrimary
                    ) {
                        Text(
                            text = if (userProfile.appUiLanguage == "az") "Təkrar Et" else if (userProfile.appUiLanguage == "en") "Review" else "Tekrar Et",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Duolingo winding path nodes
        itemsIndexed(lessons) { index, lesson ->
            val isCompleted = completedSet.contains(lesson.id)
            // Active if not completed, but all previous are completed
            val isUnlocked = index == 0 || completedSet.contains(lessons[index - 1].id)
            val isActiveNext = isUnlocked && !isCompleted

            // Winding horizontal offset pattern: 0 -> -45 -> 0 -> +45 -> 0 -> ...
            val xOffsetDp = when (index % 4) {
                1 -> (-45).dp
                3 -> 45.dp
                else -> 0.dp
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                // Vertical dotted path connector to next node
                if (index < lessons.size - 1) {
                    Canvas(
                        modifier = Modifier
                            .offset(y = 36.dp)
                            .size(width = 6.dp, height = 50.dp)
                    ) {
                        drawLine(
                            color = if (isCompleted) SuccessGreen else PurplePrimary.copy(alpha = 0.4f),
                            start = Offset(size.width / 2f, 0f),
                            end = Offset(size.width / 2f, size.height),
                            strokeWidth = 4.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 10f), 0f)
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.offset(x = xOffsetDp)
                ) {
                    // Path Node Circle Button
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        // Outer pulse ring for next active node
                        if (isActiveNext) {
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .scale(pulseScale)
                                    .border(3.dp, RobotCyanGlow.copy(alpha = 0.6f), CircleShape)
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = when {
                                isCompleted -> SuccessGreen
                                isActiveNext -> PurplePrimary
                                else -> Color(0xFF332A54)
                            },
                            shadowElevation = if (isUnlocked) 8.dp else 2.dp,
                            modifier = Modifier
                                .size(64.dp)
                                .clickable(enabled = isUnlocked) {
                                    onLessonSelected(lesson)
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                when {
                                    lesson.isMilestone -> CrownIcon(size = 32.dp, color = if (isCompleted) GoldCrown else Color.White)
                                    isCompleted -> StarBadgeIcon(size = 30.dp, color = GoldCrown)
                                    isActiveNext -> StarBadgeIcon(size = 28.dp, color = Color.White)
                                    else -> Icon(
                                        Icons.Default.Lock,
                                        contentDescription = "Locked",
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Floating Robot companion right next to the active lesson node!
                    if (isActiveNext) {
                        Spacer(modifier = Modifier.width(16.dp))
                        PurpleRobotView(
                            size = 72.dp,
                            emotion = robotEmotion,
                            visorColorCode = userProfile.robotVisorColor,
                            chassisSkin = userProfile.robotChassisSkin,
                            antennaAccessory = userProfile.robotAntennaAccessory,
                            chestBadge = userProfile.robotChestBadge,
                            speechBubbleText = robotDialogue,
                            onClick = onRobotPoke
                        )
                    }
                }
            }

            // Milestone Chest info card when it is a milestone
            if (lesson.isMilestone) {
                MilestoneChestCard(
                    title = lesson.title,
                    subtitle = lesson.subtitle,
                    xp = lesson.xpReward,
                    gems = lesson.gemReward,
                    isUnlocked = isUnlocked,
                    isCompleted = isCompleted,
                    onClick = { if (isUnlocked) onLessonSelected(lesson) }
                )
            }
        }
    }
}

@Composable
private fun UnitHeaderBanner(
    activeLanguageName: String,
    languageCode: String,
    completedCount: Int,
    totalCount: Int
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF24164B)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Ünite 1",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = RobotCyanGlow
                    )
                    Text(
                        text = "$activeLanguageName Temelleri",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
                LanguageFlagBadge(languageCode = languageCode, size = 36.dp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Progress Bar
            val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color(0xFF140D2A))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .height(10.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(PurplePrimary, RobotCyanGlow)
                                )
                            )
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$completedCount/$totalCount",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun MilestoneChestCard(
    title: String,
    subtitle: String,
    xp: Int,
    gems: Int,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) Color(0xFF143E2C) else Color(0xFF2C1D54)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 8.dp)
            .clickable(enabled = isUnlocked, onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(14.dp)
        ) {
            CrownIcon(size = 36.dp)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFFCBD5E1)
                )
            }
            Surface(
                color = Color(0xFF3B2A68),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "+$xp XP",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = GoldCrown,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun FlashcardsDialog(
    languageCode: String,
    languageName: String,
    uiLang: String,
    tts: TtsHelper?,
    onDismiss: () -> Unit
) {
    val sampleCards = when (languageCode.lowercase()) {
        "es" -> listOf(
            Triple("¡Hola!", "Merhaba / Selam", "Günlük selamlaşma"),
            Triple("Muchas gracias", "Çok teşekkür ederim", "Nezaket kalıbı"),
            Triple("Un café, por favor", "Lütfen bir kahve", "Kafede sipariş"),
            Triple("¿Dónde está el hotel?", "Otel nerede?", "Seyahat sorusu"),
            Triple("Mucho gusto", "Tanıştığıma memnun oldum", "Tanışma")
        )
        "en" -> listOf(
            Triple("Hello, nice to meet you!", "Merhaba, tanıştığıma sevindim!", "Tanışma"),
            Triple("Could I have a coffee?", "Bir kahve alabilir miyim?", "Kafede sipariş"),
            Triple("Where is the airport?", "Havalimanı nerede?", "Seyahat"),
            Triple("Thank you so much", "Çok teşekkür ederim", "Nezaket")
        )
        "az" -> listOf(
            Triple("Salam, necəsən?", "Merhaba, nasılsın?", "Salamlaşma"),
            Triple("Çox sağ olun", "Çok teşekkürler", "Nəzakət"),
            Triple("Bir stəkan çay zəhmət olmasa", "Bir bardak çay lütfen", "Çayxana"),
            Triple("Tanış olmağımıza şad oldum", "Tanıştığımıza memnun oldum", "Tanışlıq")
        )
        "de" -> listOf(
            Triple("Guten Morgen!", "Günaydın!", "Selamlaşma"),
            Triple("Danke schön", "Çok teşekkürler", "Nezaket"),
            Triple("Einen Kaffee bitte", "Bir kahve lütfen", "Kafe"),
            Triple("Auf Wiedersehen", "Tekrar görüşmek üzere", "Ayrılış")
        )
        "fr" -> listOf(
            Triple("Bonjour!", "İyi günler / Merhaba", "Salutation"),
            Triple("Merci beaucoup", "Çok teşekkür ederim", "Politesse"),
            Triple("Un croissant s'il vous plaît", "Bir kruvasan lütfen", "Au café"),
            Triple("Enchanté", "Memnun oldum", "Rencontre")
        )
        else -> listOf(
            Triple("Hello!", "Merhaba / Selam", "Greeting"),
            Triple("Thank you", "Teşekkürler", "Politeness"),
            Triple("Please", "Lütfen", "Daily phrase")
        )
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    val currentCard = sampleCards.getOrElse(currentIndex) { sampleCards.first() }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E133D)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LanguageFlagBadge(languageCode = languageCode, size = 24.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "$languageName Kelime Kasası",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive Flippable Flashcard
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isFlipped) Color(0xFF281858) else Color(0xFF332066),
                    border = androidx.compose.foundation.BorderStroke(2.dp, if (isFlipped) PurplePrimary else RobotCyanGlow),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable { isFlipped = !isFlipped }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!isFlipped) {
                            Text(
                                text = currentCard.first,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = PurplePrimary,
                                modifier = Modifier.clickable {
                                    tts?.speak(currentCard.first, languageCode = languageCode, isRobotVoice = false)
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Audio", tint = Color.White, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("İnsan Sesiyle Dinle", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Kartı çevirmek için dokun", fontSize = 11.sp, color = Color(0xFF94A3B8))
                        } else {
                            Text(
                                text = currentCard.second,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = RobotCyanGlow,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Kullanım: ${currentCard.third}",
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("Geri çevirmek için dokun", fontSize = 11.sp, color = Color(0xFF94A3B8))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (currentIndex > 0) {
                                currentIndex--
                                isFlipped = false
                            }
                        },
                        enabled = currentIndex > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF332060)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Önceki")
                    }

                    Text(
                        text = "${currentIndex + 1} / ${sampleCards.size}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    Button(
                        onClick = {
                            if (currentIndex < sampleCards.size - 1) {
                                currentIndex++
                                isFlipped = false
                            } else {
                                currentIndex = 0
                                isFlipped = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (currentIndex < sampleCards.size - 1) "Sonraki" else "Başa Dön", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
