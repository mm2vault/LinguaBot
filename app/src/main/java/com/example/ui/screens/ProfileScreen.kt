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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.UserProfile
import com.example.data.model.LanguageCatalog
import com.example.service.TtsHelper
import com.example.ui.components.CrownIcon
import com.example.ui.components.FlameIcon
import com.example.ui.components.GemIcon
import com.example.ui.components.ShieldIcon
import com.example.ui.components.StarBadgeIcon
import com.example.ui.theme.BackgroundDark
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.GoldCrown
import com.example.ui.theme.PurpleBright
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.RobotCyanGlow
import com.example.ui.theme.StreakFlameColor
import com.example.ui.theme.SuccessGreen

@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    onUpdateProfile: (name: String, username: String, email: String) -> Unit,
    onSwitchAccount: (username: String) -> Unit,
    onSetAvatar: (avatarId: String) -> Unit = {},
    onLogout: () -> Unit = {},
    onSetAppLanguage: (String) -> Unit = {},
    onResetProgress: () -> Unit = {},
    onOpenLanguagePicker: () -> Unit = {},
    tts: TtsHelper? = null,
    modifier: Modifier = Modifier
) {
    var showLoginDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showAvatarDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    if (showSettingsDialog) {
        ProfileSettingsDialog(
            userProfile = userProfile,
            onSetAppLanguage = onSetAppLanguage,
            onResetProgress = onResetProgress,
            onOpenLanguagePicker = onOpenLanguagePicker,
            tts = tts,
            onDismiss = { showSettingsDialog = false }
        )
    }

    if (showAvatarDialog) {
        AvatarPickerDialog(
            currentAvatarId = userProfile.avatarId,
            onDismiss = { showAvatarDialog = false },
            onSelectAvatar = { id ->
                onSetAvatar(id)
                showAvatarDialog = false
            }
        )
    }

    if (showLoginDialog) {
        LoginRegisterDialog(
            currentUsername = userProfile.username,
            onDismiss = { showLoginDialog = false },
            onLogin = { name, uName, mail ->
                onUpdateProfile(name, uName, mail)
                showLoginDialog = false
            },
            onQuickSwitch = { uName ->
                onSwitchAccount(uName)
                showLoginDialog = false
            }
        )
    }

    if (showEditDialog) {
        EditProfileDialog(
            currentDisplayName = userProfile.displayName,
            currentUsername = userProfile.username,
            currentEmail = userProfile.email,
            onDismiss = { showEditDialog = false },
            onSave = { name, uName, mail ->
                onUpdateProfile(name, uName, mail)
                showEditDialog = false
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
    ) {
        // Header Profile Card
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF221544)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Bar inside Card: Settings on top-left
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF352468),
                            modifier = Modifier.clickable { showSettingsDialog = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Ayarlar",
                                    tint = RobotCyanGlow,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (userProfile.appUiLanguage == "az") "Tənzimləmələr" else if (userProfile.appUiLanguage == "en") "Settings" else "Ayarlar",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF1E133D)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FlameIcon(size = 14.dp, color = StreakFlameColor)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${userProfile.streakDays} Gün",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = StreakFlameColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Avatar Icon with edit badge
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                when (userProfile.avatarId) {
                                    "scholar" -> Color(0xFF2563EB)
                                    "cadet" -> Color(0xFF059669)
                                    "astronaut" -> Color(0xFFD97706)
                                    else -> PurplePrimary
                                }
                            )
                            .border(3.dp, RobotCyanGlow, CircleShape)
                            .clickable { showAvatarDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (userProfile.avatarId) {
                                "scholar" -> "B"
                                "cadet" -> "C"
                                "astronaut" -> "A"
                                else -> userProfile.displayName.take(1).uppercase()
                            },
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Avatarı Değiştir",
                        fontSize = 11.sp,
                        color = RobotCyanGlow,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { showAvatarDialog = true }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = userProfile.displayName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = "@${userProfile.username} • ${userProfile.email}",
                        fontSize = 12.sp,
                        color = Color(0xFFCBD5E1)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Buttons: Edit Profile, Login / Switch & Logout
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { showEditDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF352468)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Düzenle", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { showLoginDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.SwitchAccount, contentDescription = "Account", tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Giriş / Profil", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        if (userProfile.isLoggedIn) {
                            IconButton(
                                onClick = onLogout,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF351A2C))
                            ) {
                                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Log out", tint = ErrorRed, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Stats Grid
        item {
            Text(
                text = "İstatistikler",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    title = "Günlük Seri",
                    value = "${userProfile.streakDays} Gün",
                    icon = { FlameIcon(size = 22.dp) },
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Toplam XP",
                    value = "${userProfile.xp} XP",
                    icon = { StarBadgeIcon(size = 22.dp, color = GoldCrown) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    title = "Kelime Dağarcığı",
                    value = "${userProfile.totalWordsLearned} Kelime",
                    icon = { CrownIcon(size = 22.dp, color = RobotCyanGlow) },
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Mevcut Lig",
                    value = userProfile.levelTier,
                    icon = { ShieldIcon(size = 22.dp, color = PurpleBright) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Achievements / Badges Section
        item {
            Text(
                text = "Kazanılan Rozetler",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))

            val badges = listOf(
                BadgeItem("İlk Adım", "İlk ders başarıyla tamamlandı", true, { StarBadgeIcon(size = 26.dp, color = GoldCrown) }),
                BadgeItem("Alev Ustası", "3 günlük kesintisiz seri yapıldı", true, { FlameIcon(size = 26.dp) }),
                BadgeItem("Robot Yoldaşı", "Byte'ı hiç kızdırmadan bölüm geçildi", true, { ShieldIcon(size = 26.dp, color = RobotCyanGlow) }),
                BadgeItem("Çokdilli Dahi", "100+ dil kataloğundan diller keşfedildi", true, { CrownIcon(size = 26.dp) })
            )

            badges.forEach { badge ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF221644)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(14.dp)
                    ) {
                        badge.icon()
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(badge.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                            Text(badge.desc, fontSize = 12.sp, color = Color(0xFFCBD5E1))
                        }
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF1E3A2B)
                        ) {
                            Text("Açıldı", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 11.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF221644)),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            icon()
            Spacer(modifier = Modifier.height(6.dp))
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(title, fontSize = 11.sp, color = Color(0xFFCBD5E1))
        }
    }
}

private data class BadgeItem(
    val title: String,
    val desc: String,
    val unlocked: Boolean,
    val icon: @Composable () -> Unit
)

@Composable
private fun LoginRegisterDialog(
    currentUsername: String,
    onDismiss: () -> Unit,
    onLogin: (name: String, username: String, email: String) -> Unit,
    onQuickSwitch: (username: String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var isRegisterMode by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E133D)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isRegisterMode) "Yeni Profil Oluştur" else "Profil ile Giriş Yap",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Toggle Tab: Giriş vs Kayıt
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF130A2A))
                        .padding(4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (!isRegisterMode) PurplePrimary else Color.Transparent,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { isRegisterMode = false }
                    ) {
                        Text(
                            text = "Giriş Yap",
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isRegisterMode) PurplePrimary else Color.Transparent,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { isRegisterMode = true }
                    ) {
                        Text(
                            text = "Kayıt Ol",
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("Kullanıcı Adı (Örn: alex_polyglot)", fontSize = 12.sp, color = Color(0xFF94A3B8)) },
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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("E-posta adresi", fontSize = 12.sp, color = Color(0xFF94A3B8)) },
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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Şifre", fontSize = 12.sp, color = Color(0xFF94A3B8)) },
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

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        val finalUser = if (username.isNotBlank()) username.trim() else "yeni_ogrenci"
                        val finalMail = if (email.isNotBlank()) email.trim() else "$finalUser@linguabot.com"
                        onLogin(finalUser.replaceFirstChar { it.uppercase() }, finalUser, finalMail)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text(if (isRegisterMode) "Profil Oluştur & Giriş Yap" else "Giriş Yap", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("Tek Dokunuşla Hesap Değiştir:", fontSize = 11.sp, color = Color(0xFF94A3B8), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                val demoUsers = listOf(
                    Triple("alex_polyglot", "Alex M. (Poliglot)", "14 Gün Seri • 840 XP • Japonca"),
                    Triple("murad_eliyev", "Murad Əliyev", "9 Gün Seri • 520 XP • İngiliscə"),
                    Triple("zeynep_ogrenci", "Zeynep Kaya", "5 Gün Seri • 320 XP • İspanyolca")
                )

                demoUsers.forEach { (uId, dName, sub) ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A1B4E)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable { onQuickSwitch(uId) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(PurplePrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(dName.take(1), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(dName, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                                Text(sub, fontSize = 10.sp, color = Color(0xFFCBD5E1))
                            }
                            Text("Seç", color = RobotCyanGlow, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AvatarPickerDialog(
    currentAvatarId: String,
    onDismiss: () -> Unit,
    onSelectAvatar: (String) -> Unit
) {
    val avatars = listOf(
        Triple("robot", "Robot Yoldaş", PurplePrimary),
        Triple("cadet", "Siber Harbiyeli", Color(0xFF059669)),
        Triple("scholar", "Dil Bilgini", Color(0xFF2563EB)),
        Triple("astronaut", "Uzay Kaşifi", Color(0xFFD97706))
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E133D)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Avatarını Seç", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                avatars.forEach { (id, title, color) ->
                    val isSelected = currentAvatarId == id
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFF3B256E) else Color(0xFF160D2E)
                        ),
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, RobotCyanGlow) else null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { onSelectAvatar(id) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(color),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = title.take(1),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(title, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp)
                                Text(
                                    if (isSelected) "Seçili Avatar" else "Seçmek için dokun",
                                    fontSize = 12.sp,
                                    color = if (isSelected) RobotCyanGlow else Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EditProfileDialog(
    currentDisplayName: String,
    currentUsername: String,
    currentEmail: String,
    onDismiss: () -> Unit,
    onSave: (name: String, username: String, email: String) -> Unit
) {
    var name by remember { mutableStateOf(currentDisplayName) }
    var username by remember { mutableStateOf(currentUsername) }
    var email by remember { mutableStateOf(currentEmail) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E133D)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Profili Düzenle",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Görünen İsim") },
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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Kullanıcı Adı") },
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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-posta") },
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

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33205E)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("İptal")
                    }

                    Button(
                        onClick = { onSave(name, username, email) },
                        colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Kaydet", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileSettingsDialog(
    userProfile: UserProfile,
    onSetAppLanguage: (String) -> Unit,
    onResetProgress: () -> Unit,
    onOpenLanguagePicker: () -> Unit,
    tts: TtsHelper?,
    onDismiss: () -> Unit
) {
    var showResetConfirm by remember { mutableStateOf(false) }
    var voiceMode by remember { mutableStateOf("natural") } // "natural" or "robot"
    var voiceSpeed by remember { mutableStateOf(1.0f) }

    val appLanguages = listOf(
        "tr" to "Türkçe",
        "az" to "Azərbaycan dili",
        "en" to "English",
        "es" to "Español",
        "de" to "Deutsch",
        "fr" to "Français",
        "ru" to "Русский"
    )

    if (showResetConfirm) {
        AlertDialog(
            onDismissRequest = { showResetConfirm = false },
            title = { Text("İlerlemeyi Sıfırla?", color = Color.White, fontWeight = FontWeight.Bold) },
            text = { Text("Tüm XP, seriler ve öğrenilen kelimeler sıfırlanacak. Devam etmek istiyor musun?", color = Color(0xFFCBD5E1)) },
            containerColor = Color(0xFF28184C),
            confirmButton = {
                TextButton(onClick = {
                    onResetProgress()
                    showResetConfirm = false
                    onDismiss()
                }) {
                    Text("Evet, Sıfırla", color = ErrorRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirm = false }) {
                    Text("İptal", color = Color.White)
                }
            }
        )
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E133D)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentPadding = PaddingValues(bottom = 12.dp)
            ) {
                // Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = RobotCyanGlow)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (userProfile.appUiLanguage == "az") "Tənzimləmələr" else if (userProfile.appUiLanguage == "en") "Settings" else "Ayarlar",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // App UI Language Section
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF150C2C)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Language, contentDescription = "Lang", tint = PurpleBright, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (userProfile.appUiLanguage == "az") "Sayt / Tətbiq Dili" else if (userProfile.appUiLanguage == "en") "App UI Language" else "Site / Uygulama Dili",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            appLanguages.forEach { (code, name) ->
                                val isSelected = userProfile.appUiLanguage == code
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) PurplePrimary else Color(0xFF24164B),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp)
                                        .clickable { onSetAppLanguage(code) }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(name, color = Color.White, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                        if (isSelected) {
                                            Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.White, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Learning Language
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF150C2C)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (userProfile.appUiLanguage == "az") "Öyrənilən Dil" else if (userProfile.appUiLanguage == "en") "Target Learning Language" else "Öğrenilen Dil",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val currentLang = LanguageCatalog.findByCode(userProfile.activeLanguageCode)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${currentLang.getDisplayName(userProfile.appUiLanguage)} (${currentLang.nativeName})",
                                    color = RobotCyanGlow,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Button(
                                    onClick = {
                                        onDismiss()
                                        onOpenLanguagePicker()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3B246C)),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("100+ Dil Seç", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // AI Robot Voice Settings
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF150C2C)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.RecordVoiceOver, contentDescription = "Voice", tint = RobotCyanGlow, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "AI Robot Ses Ayarı & Test",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            Text("Ses Karakteri:", fontSize = 12.sp, color = Color(0xFF94A3B8))
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (voiceMode == "natural") PurplePrimary else Color(0xFF24164B),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { voiceMode = "natural" }
                                ) {
                                    Text(
                                        "Doğal Akıcı",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (voiceMode == "robot") PurplePrimary else Color(0xFF24164B),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { voiceMode = "robot" }
                                ) {
                                    Text(
                                        "Robotik Ton",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Test Speech Button
                            Button(
                                onClick = {
                                    val testText = when (userProfile.activeLanguageCode) {
                                        "en" -> "Hello! I am your AI language companion Byte. Let's learn together!"
                                        "az" -> "Salam! Mən sənin süni intellekt köməkçin Byte-am. Gəl birlikdə öyrənək!"
                                        "es" -> "¡Hola! Soy Byte, tu compañero robot. ¡Vamos a aprender español!"
                                        "de" -> "Hallo! Ich bin dein Sprachassistent Byte. Lass uns Deutsch lernen!"
                                        "fr" -> "Bonjour! Je suis Byte, ton tuteur de langue. Apprenons ensemble!"
                                        else -> "Merhaba! Ben yapay zeka robotun Byte. Beraber dil öğrenelim!"
                                    }
                                    tts?.speak(
                                        text = testText,
                                        languageCode = userProfile.activeLanguageCode,
                                        speed = voiceSpeed,
                                        isRobotVoice = (voiceMode == "robot"),
                                        customPitch = if (voiceMode == "robot") 1.15f else 1.0f
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Test", modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Sesi Dinle & Test Et", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Reset Progress
                item {
                    Button(
                        onClick = { showResetConfirm = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF450A0A)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.RestartAlt, contentDescription = "Reset", tint = Color(0xFFF87171), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("İlerlemeyi Sıfırla", color = Color(0xFFF87171), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
