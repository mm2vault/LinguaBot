package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Exercise
import com.example.data.model.ExerciseType
import com.example.data.model.Lesson
import com.example.data.model.RobotEmotion
import com.example.service.SpeechRecognizerHelper
import com.example.service.TtsHelper
import com.example.ui.components.CrownIcon
import com.example.ui.components.FlameIcon
import com.example.ui.components.GemIcon
import com.example.ui.components.HeartIcon
import com.example.ui.components.PurpleRobotView
import com.example.ui.components.StarBadgeIcon
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GemTeal
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.HeartColor
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurpleDark
import com.example.ui.theme.PurpleLight
import com.example.ui.theme.PurplePastel
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.StreakFlameColor
import com.example.ui.theme.SuccessGreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LessonPlayScreen(
    lesson: Lesson,
    userHearts: Int,
    tts: TtsHelper,
    robotVisorColor: String,
    robotChassisSkin: String = "classic",
    robotAntennaAccessory: String = "orb",
    robotChestBadge: String = "core",
    onClose: () -> Unit,
    onFinish: (scorePercent: Int, mistakes: Int, earnedXp: Int, earnedGems: Int) -> Unit,
    onMistake: (word: String, translation: String) -> Unit,
    onCorrect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var currentExerciseIndex by remember { mutableIntStateOf(0) }
    var mistakeCount by remember { mutableIntStateOf(0) }
    var comboCount by remember { mutableIntStateOf(0) }
    var isAnswerChecked by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }
    var localRobotEmotion by remember { mutableStateOf(RobotEmotion.IDLE) }
    var robotFeedbackText by remember { mutableStateOf<String?>(null) }
    var isLessonCompleted by remember { mutableStateOf(false) }
    var showGrammarTip by remember { mutableStateOf(false) }

    // Speech recognition state for pronunciation exercises
    var spokenPronunciation by remember(currentExerciseIndex) { mutableStateOf("") }
    var isListeningSpeech by remember { mutableStateOf(false) }

    val speechHelper = remember {
        SpeechRecognizerHelper(
            context = context,
            onResult = { text ->
                spokenPronunciation = text
            },
            onError = { _ -> },
            onListeningStateChanged = { listening ->
                isListeningSpeech = listening
            }
        )
    }

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            speechHelper.startListening(lesson.languageCode)
        }
    }

    val currentExercise = lesson.exercises.getOrNull(currentExerciseIndex)

    // State for interactive answers
    var selectedOption by remember(currentExerciseIndex) { mutableStateOf<String?>(null) }
    val orderedWords = remember(currentExerciseIndex) { mutableStateListOf<String>() }
    val availableWords = remember(currentExerciseIndex) {
        mutableStateListOf<String>().apply {
            currentExercise?.options?.let { addAll(it) }
        }
    }

    // Matching pairs state
    var selectedLeftPair by remember(currentExerciseIndex) { mutableStateOf<String?>(null) }
    var selectedRightPair by remember(currentExerciseIndex) { mutableStateOf<String?>(null) }
    val matchedPairs = remember(currentExerciseIndex) { mutableStateListOf<String>() }

    // Auto-TTS pronunciation on prompt load
    LaunchedEffect(currentExerciseIndex) {
        isAnswerChecked = false
        isAnswerCorrect = false
        showGrammarTip = false
        localRobotEmotion = RobotEmotion.IDLE
        robotFeedbackText = "Bip! Hazırsan başlayalım!"

        currentExercise?.ttsTargetText?.let { phrase ->
            tts.speak(phrase, lesson.languageCode, isRobotVoice = false)
        }
    }

    if (isLessonCompleted) {
        LessonCompleteView(
            xp = lesson.xpReward,
            gems = lesson.gemReward,
            mistakes = mistakeCount,
            robotVisorColor = robotVisorColor,
            robotChassisSkin = robotChassisSkin,
            robotAntennaAccessory = robotAntennaAccessory,
            robotChestBadge = robotChestBadge,
            onContinue = {
                val score = ((lesson.exercises.size - mistakeCount).coerceAtLeast(0) * 100) / lesson.exercises.size
                onFinish(score, mistakeCount, lesson.xpReward, lesson.gemReward)
            }
        )
        return
    }

    if (currentExercise == null) return

    val progress = (currentExerciseIndex.toFloat()) / lesson.exercises.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(16.dp)
    ) {
        // Top Bar: Close, Progress, Hearts
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(1f)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp)),
                color = PurplePrimary,
                trackColor = Color(0xFF2C224D),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                HeartIcon(size = 20.dp, isFilled = userHearts > 0)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = userHearts.toString(),
                    color = HeartColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Center Content Scrollable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Robot Companion on Screen Watching
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                PurpleRobotView(
                    size = 76.dp,
                    emotion = localRobotEmotion,
                    visorColorCode = robotVisorColor,
                    chassisSkin = robotChassisSkin,
                    antennaAccessory = robotAntennaAccessory,
                    chestBadge = robotChestBadge,
                    speechBubbleText = robotFeedbackText
                )
                Spacer(modifier = Modifier.width(12.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF231942)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = currentExercise.prompt,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            if (comboCount >= 2) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF3B1F2B))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    FlameIcon(size = 14.dp)
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${comboCount}x Seri!",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = StreakFlameColor
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            if (!currentExercise.ttsTargetText.isNullOrBlank()) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = PurplePrimary,
                                    modifier = Modifier.clickable {
                                        tts.speak(currentExercise.ttsTargetText, lesson.languageCode, isRobotVoice = false)
                                    }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.VolumeUp,
                                            contentDescription = "Speak",
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Robot Sesi",
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }

                            if (currentExercise.explanation.isNotBlank()) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF312258),
                                    modifier = Modifier.clickable {
                                        showGrammarTip = !showGrammarTip
                                    }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Lightbulb,
                                            contentDescription = "Grammar Tip",
                                            tint = RobotCyanGlow,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "İpucu",
                                            color = RobotCyanGlow,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }

                        if (showGrammarTip && currentExercise.explanation.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1238))
                            ) {
                                Text(
                                    text = currentExercise.explanation,
                                    fontSize = 12.sp,
                                    color = Color(0xFFE2E8F0),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Exercise Input View based on Type
            when (currentExercise.type) {
                ExerciseType.MULTIPLE_CHOICE, ExerciseType.FILL_BLANK, ExerciseType.LISTEN_TRANSLATE -> {
                    currentExercise.options.forEach { option ->
                        val isSelected = selectedOption == option
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) PurplePrimary else Color(0xFF261C48)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable(enabled = !isAnswerChecked) {
                                    selectedOption = option
                                }
                        ) {
                            Text(
                                text = option,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }

                ExerciseType.SENTENCE_ORDER -> {
                    // Constructed Sentence Tray
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF160F30)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            orderedWords.forEach { word ->
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = PurplePrimary,
                                    modifier = Modifier.clickable(enabled = !isAnswerChecked) {
                                        orderedWords.remove(word)
                                        availableWords.add(word)
                                    }
                                ) {
                                    Text(
                                        text = word,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Available Word Tiles
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        availableWords.forEach { word ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFF352664),
                                modifier = Modifier.clickable(enabled = !isAnswerChecked) {
                                    availableWords.remove(word)
                                    orderedWords.add(word)
                                }
                            ) {
                                Text(
                                    text = word,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                }

                ExerciseType.MATCH_PAIRS -> {
                    val pairs = currentExercise.pairMap
                    val leftItems = pairs.keys.toList()
                    val rightItems = pairs.values.shuffled(remember { kotlin.random.Random(123) })

                    Row(modifier = Modifier.fillMaxWidth()) {
                        // Left column
                        Column(modifier = Modifier.weight(1f)) {
                            leftItems.forEach { item ->
                                val isMatched = matchedPairs.contains(item)
                                val isSelected = selectedLeftPair == item
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = when {
                                            isMatched -> SuccessGreen.copy(alpha = 0.4f)
                                            isSelected -> PurplePrimary
                                            else -> Color(0xFF261C48)
                                        }
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .clickable(enabled = !isMatched && !isAnswerChecked) {
                                            selectedLeftPair = item
                                            // Check match if right is already selected
                                            selectedRightPair?.let { right ->
                                                if (pairs[item] == right) {
                                                    matchedPairs.add(item)
                                                    matchedPairs.add(right)
                                                    selectedLeftPair = null
                                                    selectedRightPair = null
                                                }
                                            }
                                        }
                                ) {
                                    Text(
                                        text = item,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Right column
                        Column(modifier = Modifier.weight(1f)) {
                            rightItems.forEach { item ->
                                val isMatched = matchedPairs.contains(item)
                                val isSelected = selectedRightPair == item
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = when {
                                            isMatched -> SuccessGreen.copy(alpha = 0.4f)
                                            isSelected -> PurplePrimary
                                            else -> Color(0xFF261C48)
                                        }
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp)
                                        .clickable(enabled = !isMatched && !isAnswerChecked) {
                                            selectedRightPair = item
                                            // Check match if left is already selected
                                            selectedLeftPair?.let { left ->
                                                if (pairs[left] == item) {
                                                    matchedPairs.add(left)
                                                    matchedPairs.add(item)
                                                    selectedLeftPair = null
                                                    selectedRightPair = null
                                                }
                                            }
                                        }
                                ) {
                                    Text(
                                        text = item,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                ExerciseType.SPEAK_PRONOUNCE -> {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF221644)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Kelimeyi Robot Sesiyle Dinle ve Mikrofona Konuş",
                                fontSize = 13.sp,
                                color = Color(0xFF94A3B8)
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFF332060),
                                modifier = Modifier.clickable {
                                    tts.speak(
                                        currentExercise.ttsTargetText ?: currentExercise.correctAnswer,
                                        lesson.languageCode,
                                        isRobotVoice = false
                                    )
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Listen",
                                        tint = RobotCyanGlow,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Örnek Telaffuz Dinle",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // Big Round Mic Button
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(if (isListeningSpeech) ErrorRed else PurplePrimary)
                                    .clickable(enabled = !isAnswerChecked) {
                                        if (isListeningSpeech) {
                                            speechHelper.stopListening()
                                        } else {
                                            audioPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    if (isListeningSpeech) Icons.Default.Mic else Icons.Default.MicNone,
                                    contentDescription = "Speak now",
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = when {
                                    isListeningSpeech -> "Dinleniyor... Şimdi söyle!"
                                    spokenPronunciation.isNotBlank() -> "Algılanan: \"$spokenPronunciation\""
                                    else -> "Mikrofona dokun ve söyle"
                                },
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isListeningSpeech) RobotCyanGlow else Color.White
                            )

                            if (spokenPronunciation.isNotBlank() && !isAnswerChecked) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Onaylamak için alttaki 'Kontrol Et' butonuna dokun",
                                    fontSize = 11.sp,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bottom Feedback Banner / Action Button
        AnimatedVisibility(
            visible = isAnswerChecked,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isAnswerCorrect) Color(0xFF0F3D24) else Color(0xFF450A0A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (isAnswerCorrect) "Harika! Doğru Cevap!" else "Hata! Robotun devreleri ısındı!",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = if (isAnswerCorrect) SuccessGreen else Color(0xFFFCA5A5)
                    )
                    Text(
                        text = currentExercise.explanation,
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        // Check / Continue Button
        Button(
            onClick = {
                if (!isAnswerChecked) {
                    // Evaluate Answer
                    val correct = when (currentExercise.type) {
                        ExerciseType.MULTIPLE_CHOICE, ExerciseType.FILL_BLANK, ExerciseType.LISTEN_TRANSLATE -> {
                            selectedOption.equals(currentExercise.correctAnswer, ignoreCase = true)
                        }
                        ExerciseType.SENTENCE_ORDER -> {
                            val userSentence = orderedWords.joinToString(" ").trim()
                            userSentence.equals(currentExercise.correctAnswer.trim(), ignoreCase = true)
                        }
                        ExerciseType.MATCH_PAIRS -> {
                            matchedPairs.size >= currentExercise.pairMap.size * 2
                        }
                        ExerciseType.SPEAK_PRONOUNCE -> {
                            val cleanSpoken = spokenPronunciation.trim().lowercase()
                            val cleanTarget = currentExercise.correctAnswer.trim().lowercase()
                            cleanSpoken.contains(cleanTarget) || cleanTarget.contains(cleanSpoken) || (cleanSpoken.isNotEmpty() && cleanSpoken.length >= 2)
                        }
                    }

                    isAnswerChecked = true
                    isAnswerCorrect = correct

                    if (correct) {
                        comboCount++
                        localRobotEmotion = RobotEmotion.HAPPY
                        robotFeedbackText = if (comboCount >= 3) {
                            "Bip-bup! ${comboCount}x Süper Seri! Alev alev parıldıyorsun!"
                        } else {
                            "Bip-bup! Harika yanıt! Devrelerim parıldıyor!"
                        }
                        onCorrect()
                    } else {
                        comboCount = 0
                        mistakeCount++
                        localRobotEmotion = RobotEmotion.SASSY_ANGRY
                        robotFeedbackText = "Bip-bop! Devrelerim aşırı ısınıyor! Sıcaklık 95°C!"
                        onMistake(currentExercise.prompt, currentExercise.correctAnswer)
                    }
                } else {
                    // Next question
                    if (currentExerciseIndex < lesson.exercises.size - 1) {
                        currentExerciseIndex++
                    } else {
                        isLessonCompleted = true
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAnswerChecked && isAnswerCorrect) SuccessGreen else PurplePrimary
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = if (!isAnswerChecked) "Kontrol Et" else "Devam Et",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun LessonCompleteView(
    xp: Int,
    gems: Int,
    mistakes: Int,
    robotVisorColor: String,
    robotChassisSkin: String = "classic",
    robotAntennaAccessory: String = "orb",
    robotChestBadge: String = "core",
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PurpleRobotView(
            size = 120.dp,
            emotion = RobotEmotion.CELEBRATING,
            visorColorCode = robotVisorColor,
            chassisSkin = robotChassisSkin,
            antennaAccessory = robotAntennaAccessory,
            chestBadge = robotChestBadge,
            speechBubbleText = "Bölüm Tamamlandı! Devrelerim seninle gurur duyuyor!"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ders Tamamlandı!",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // XP Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1B54)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    StarBadgeIcon(size = 28.dp, color = GoldCrown)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("+$xp XP", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    Text("Kazanıldı", fontSize = 12.sp, color = Color(0xFFCBD5E1))
                }
            }

            // Gems Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2B1B54)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    GemIcon(size = 28.dp, color = GemTeal)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("+$gems Mücevher", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                    Text("Ödül", fontSize = 12.sp, color = Color(0xFFCBD5E1))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onContinue,
            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("Harika, Devam!", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
