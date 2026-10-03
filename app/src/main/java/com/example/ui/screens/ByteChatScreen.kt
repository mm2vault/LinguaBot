package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserProfile
import com.example.data.model.ChatMessage
import com.example.data.model.LanguageCatalog
import com.example.data.model.RobotEmotion
import com.example.service.SpeechRecognizerHelper
import com.example.service.TtsHelper
import com.example.ui.components.LanguageFlagBadge
import com.example.ui.components.PurpleRobotView
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.RobotEyeAngryRed
import com.example.ui.theme.SuccessGreen
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Composable
fun ByteChatScreen(
    userProfile: UserProfile,
    chatMessages: List<ChatMessage>,
    isAiTyping: Boolean,
    tts: TtsHelper,
    onSendMessage: (text: String, imageBase64: String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var selectedImageBase64 by remember { mutableStateOf<String?>(null) }
    var selectedImageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isListening by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    var showTranslatorMode by remember { mutableStateOf(false) }
    var translatorTargetLang by remember { mutableStateOf(userProfile.activeLanguageCode) }
    var translatorInput by remember { mutableStateOf("") }

    // Scroll to bottom when new messages arrive
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    // Android Speech Recognizer helper
    val speechHelper = remember {
        SpeechRecognizerHelper(
            context = context,
            onResult = { text ->
                inputText = text
            },
            onError = { _ -> },
            onListeningStateChanged = { listening ->
                isListening = listening
            }
        )
    }

    // Audio permission launcher
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            speechHelper.startListening(userProfile.appUiLanguage)
        }
    }

    // Zero-permission Android Photo Picker compliant with Play Policy
    val photoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                // Resize to max 1024 to optimize speed
                val maxDim = 1024
                val scale = if (originalBitmap.width > maxDim || originalBitmap.height > maxDim) {
                    val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
                    if (ratio > 1f) {
                        Bitmap.createScaledBitmap(originalBitmap, maxDim, (maxDim / ratio).toInt(), true)
                    } else {
                        Bitmap.createScaledBitmap(originalBitmap, (maxDim * ratio).toInt(), maxDim, true)
                    }
                } else originalBitmap

                val stream = ByteArrayOutputStream()
                scale.compress(Bitmap.CompressFormat.JPEG, 80, stream)
                val bytes = stream.toByteArray()
                selectedImageBase64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
                selectedImageBitmap = scale
            } catch (e: Exception) {
                // ignore
            }
        }
    }

    val activeLang = LanguageCatalog.findByCode(userProfile.activeLanguageCode)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // Chat Header with Byte
        Surface(
            color = Color(0xFF201444),
            tonalElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                PurpleRobotView(
                    size = 52.dp,
                    emotion = if (isAiTyping) RobotEmotion.THINKING else RobotEmotion.HAPPY,
                    visorColorCode = userProfile.robotVisorColor,
                    chassisSkin = userProfile.robotChassisSkin,
                    antennaAccessory = userProfile.robotAntennaAccessory,
                    chestBadge = userProfile.robotChestBadge
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Byte (Yapay Zeka Yoldaş)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SuccessGreen)
                        )
                    }
                    Text(
                        text = "Fotoğraflı Ödev Çözümü & Canlı Sesli Çeviri",
                        fontSize = 11.sp,
                        color = RobotCyanGlow
                    )
                }

                // Voice Pronunciation Test Button
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF332066),
                    modifier = Modifier.clickable {
                        tts.speak("Merhaba! Ben senin dil öğretmeninim. Sana yardım etmeye hazırım!", userProfile.appUiLanguage, isRobotVoice = false)
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Test Voice",
                        tint = RobotCyanGlow,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }
            }
        }

        // Quick Action Chips Row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF332064),
                    modifier = Modifier.clickable {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Image, contentDescription = "Photo", tint = RobotCyanGlow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Ödev Fotoğrafı Çöz", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (showTranslatorMode) PurplePrimary else Color(0xFF332064),
                    modifier = Modifier.clickable {
                        showTranslatorMode = !showTranslatorMode
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Translate, contentDescription = "Translate", tint = if (showTranslatorMode) Color.White else RobotCyanGlow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("100+ Dil Canlı Çevirici", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF332064),
                    modifier = Modifier.clickable {
                        inputText = "Byte, bugün motivasyona ihtiyacım var, bana robot yoldaşım olarak tavsiye verir misin?"
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.QuestionAnswer, contentDescription = "Chat", tint = RobotCyanGlow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Byte ile Dertleş", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF332064),
                    modifier = Modifier.clickable {
                        audioPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.RecordVoiceOver, contentDescription = "Voice", tint = RobotCyanGlow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Sesli Pratik", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1E3A8A),
                    modifier = Modifier.clickable {
                        onSendMessage("Sen kibar bir garson ol. Ben kafeye geldim. Bana menüyü sun, siparişimi al ve sohbet başlat.", null)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("☕ Kafe Rolü", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF047857),
                    modifier = Modifier.clickable {
                        onSendMessage("Sen otel resepsiyonistisin. Ben otele giriş yapıyorum. Bana rezervasyonumu ve odamı sor.", null)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("🏨 Otel Rolü", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFB45309),
                    modifier = Modifier.clickable {
                        onSendMessage("Sen havalimanı pasaport görevlisisin. Pasaportumu ve uçuş biletimi incele, sorular sor.", null)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("✈️ Havalimanı", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFBE123C),
                    modifier = Modifier.clickable {
                        onSendMessage("Seninle yeni tanıştık! Bana hedef dilde nereli olduğumu, hobilerimi sor ve arkadaş olalım.", null)
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("🤝 Arkadaş Tanışması", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        // Live 100+ Language Translator Card if expanded
        if (showTranslatorMode) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF241648)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Translate, contentDescription = "Translator", tint = RobotCyanGlow, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("100+ Dil Anlık Sesli Çevirmen", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                        }
                        IconButton(
                            onClick = { showTranslatorMode = false },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close translator", tint = Color(0xFF94A3B8), modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Hedef Dili Seç (100+ Dil):", fontSize = 11.sp, color = Color(0xFFCBD5E1), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))

                    val quickLangs = listOf("es", "en", "tr", "az", "de", "fr", "it", "ja", "ru", "ar")
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(quickLangs) { code ->
                            val isSel = translatorTargetLang.equals(code, ignoreCase = true)
                            val langObj = LanguageCatalog.findByCode(code)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSel) PurplePrimary else Color(0xFF332064),
                                modifier = Modifier.clickable {
                                    translatorTargetLang = code
                                }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    LanguageFlagBadge(languageCode = code, size = 14.dp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = langObj.getDisplayName(userProfile.appUiLanguage),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Input for translation
                    OutlinedTextField(
                        value = translatorInput,
                        onValueChange = { translatorInput = it },
                        placeholder = { Text("Çevrilecek ifadeyi girin...", fontSize = 12.sp, color = Color(0xFF94A3B8)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFF140C2C),
                            unfocusedContainerColor = Color(0xFF140C2C),
                            focusedBorderColor = PurpleBright,
                            unfocusedBorderColor = Color(0xFF2F2156),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            val textToTranslate = translatorInput.trim()
                            if (textToTranslate.isNotEmpty()) {
                                onSendMessage(
                                    "Lütfen bunu ${LanguageCatalog.findByCode(translatorTargetLang).getDisplayName(userProfile.appUiLanguage)} diline çevir ve açıkla: \"$textToTranslate\"",
                                    null
                                )
                                translatorInput = ""
                                showTranslatorMode = false
                            }
                        },
                        enabled = translatorInput.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(42.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Speak", tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Çevir & Tatlı Robot Sesiyle Dinle", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(chatMessages) { message ->
                val isUser = message.sender == "user"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    if (!isUser) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(PurplePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("B", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Card(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isUser) 16.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 16.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isUser) PurplePrimary else Color(0xFF26194E)
                        ),
                        modifier = Modifier.fillMaxWidth(0.82f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            // If user attached an image, show thumbnail
                            val attachedBitmap = remember(message.imageBase64) {
                                if (!message.imageBase64.isNullOrBlank()) {
                                    try {
                                        val decodedBytes = Base64.decode(message.imageBase64, Base64.NO_WRAP)
                                        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                                    } catch (e: Exception) {
                                        null
                                    }
                                } else null
                            }

                            if (attachedBitmap != null) {
                                Image(
                                    bitmap = attachedBitmap.asImageBitmap(),
                                    contentDescription = "Attached homework photo",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Text(
                                text = message.text,
                                fontSize = 14.sp,
                                color = Color.White,
                                lineHeight = 20.sp
                            )

                            // Voice button on Byte's responses to pronounce out loud
                            if (!isUser) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFF382370))
                                        .clickable {
                                            tts.speak(message.text, userProfile.activeLanguageCode, isRobotVoice = false)
                                        }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Speak",
                                        tint = RobotCyanGlow,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Doğal İnsan Sesiyle Dinle",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = RobotCyanGlow
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // AI Typing indicator
            if (isAiTyping) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(PurplePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("B", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF26194E))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(14.dp),
                                    color = RobotCyanGlow,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Byte düşünüyor ve çeviriyor...",
                                    fontSize = 12.sp,
                                    color = Color(0xFFCBD5E1)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Image Attachment Preview Banner if selected
        if (selectedImageBitmap != null) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2D1E5E)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        bitmap = selectedImageBitmap!!.asImageBitmap(),
                        contentDescription = "Attachment preview",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Ödev/Metin Fotoğrafı Eklendi",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        selectedImageBitmap = null
                        selectedImageBase64 = null
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Remove photo", tint = Color.White)
                    }
                }
            }
        }

        // Bottom Input Row: Photo, Text, Mic, Send
        Surface(
            color = Color(0xFF1E133D),
            tonalElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                // Photo Picker Button (PickVisualMedia)
                IconButton(onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {
                    Icon(
                        Icons.Default.Image,
                        contentDescription = "Attach Homework Photo",
                        tint = RobotCyanGlow
                    )
                }

                // Text Input
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = if (isListening) "Dinliyorum, konuş..." else "Byte'a sor veya çevir...",
                            fontSize = 13.sp,
                            color = Color(0xFF94A3B8)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF140C2C),
                        unfocusedContainerColor = Color(0xFF140C2C),
                        focusedBorderColor = PurpleBright,
                        unfocusedBorderColor = Color(0xFF2F2156),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                )

                // Microphone Voice Input Button
                IconButton(onClick = {
                    if (isListening) {
                        speechHelper.stopListening()
                    } else {
                        audioPermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                    }
                }) {
                    Icon(
                        if (isListening) Icons.Default.Mic else Icons.Default.MicNone,
                        contentDescription = "Voice Input",
                        tint = if (isListening) RobotEyeAngryRed else Color(0xFFCBD5E1)
                    )
                }

                // Send Button
                IconButton(
                    onClick = {
                        val trimmed = inputText.trim()
                        if (trimmed.isNotEmpty() || selectedImageBase64 != null) {
                            onSendMessage(trimmed, selectedImageBase64)
                            inputText = ""
                            selectedImageBase64 = null
                            selectedImageBitmap = null
                        }
                    },
                    enabled = inputText.isNotBlank() || selectedImageBase64 != null
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (inputText.isNotBlank() || selectedImageBase64 != null) PurpleBright else Color(0xFF64748B)
                    )
                }
            }
        }
    }
}
