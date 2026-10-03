package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainViewModel
import com.example.ui.components.DuolingoBottomBar
import com.example.ui.components.DuolingoTopBar
import com.example.ui.screens.AiAdaptivePlanScreen
import com.example.ui.screens.ByteChatScreen
import com.example.ui.screens.LanguageSelectScreen
import com.example.ui.screens.LearnPathScreen
import com.example.ui.screens.LessonPlayScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RobotLabScreen
import com.example.ui.screens.StreakHubDialog
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                LinguaBotApp()
            }
        }
    }
}

@Composable
fun LinguaBotApp(viewModel: MainViewModel = viewModel()) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val isLanguagePickerOpen by viewModel.isLanguagePickerOpen.collectAsState()
    val activeLesson by viewModel.activeLesson.collectAsState()
    val robotEmotion by viewModel.robotEmotion.collectAsState()
    val robotDialogue by viewModel.robotDialogueText.collectAsState()
    val aiPlan by viewModel.aiPlan.collectAsState()
    val isGeneratingAiPlan by viewModel.isGeneratingAiPlan.collectAsState()
    val dailyQuests by viewModel.dailyQuests.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val isAiTyping by viewModel.isAiTyping.collectAsState()
    var showStreakHub by remember { mutableStateOf(false) }

    // When playing a lesson, render full screen interactive game
    activeLesson?.let { lesson ->
        BackHandler {
            viewModel.closeLesson()
        }
        LessonPlayScreen(
            lesson = lesson,
            userHearts = userProfile.hearts,
            tts = viewModel.tts,
            robotVisorColor = userProfile.robotVisorColor,
            robotChassisSkin = userProfile.robotChassisSkin,
            robotAntennaAccessory = userProfile.robotAntennaAccessory,
            robotChestBadge = userProfile.robotChestBadge,
            onClose = { viewModel.closeLesson() },
            onFinish = { score, mistakes, xp, gems ->
                viewModel.soundFx.playLevelUp()
                viewModel.completeLesson(lesson.id, score, mistakes, xp, gems)
                viewModel.closeLesson()
            },
            onMistake = { word, translation ->
                viewModel.soundFx.playWrong()
                viewModel.recordMistake(word, translation)
            },
            onCorrect = {
                viewModel.soundFx.playCorrect()
                viewModel.haptics.vibrateCorrect()
            }
        )
        return
    }

    // When 100+ language picker is open
    if (isLanguagePickerOpen) {
        BackHandler {
            viewModel.openLanguagePicker(false)
        }
        LanguageSelectScreen(
            currentLanguageCode = userProfile.activeLanguageCode,
            uiLang = userProfile.appUiLanguage,
            onLanguageSelected = { code ->
                viewModel.setActiveLanguage(code)
            },
            onClose = {
                viewModel.openLanguagePicker(false)
            }
        )
        return
    }

    // Daily Streak Hub Dialog
    if (showStreakHub) {
        StreakHubDialog(
            userProfile = userProfile,
            onBuyStreakFreeze = { viewModel.buyStreakFreeze() },
            onStartLesson = {
                showStreakHub = false
                val lessons = viewModel.getLessonsForCurrentLanguage()
                lessons.firstOrNull()?.let { viewModel.startLesson(it) }
            },
            onDismiss = { showStreakHub = false }
        )
    }

    // Sub-tab back handling: back navigates to Tab 0 (Learn)
    if (currentTab != 0) {
        BackHandler {
            viewModel.selectTab(0)
        }
    }

    Scaffold(
        topBar = {
            DuolingoTopBar(
                languageCode = userProfile.activeLanguageCode,
                streakDays = userProfile.streakDays,
                gems = userProfile.gems,
                hearts = userProfile.hearts,
                onLanguageClick = { viewModel.openLanguagePicker(true) },
                onStreakClick = { showStreakHub = true },
                onHeartsClick = { viewModel.selectTab(3) },
                uiLang = userProfile.appUiLanguage
            )
        },
        bottomBar = {
            DuolingoBottomBar(
                currentTabIndex = currentTab,
                onTabSelected = { viewModel.selectTab(it) },
                uiLang = userProfile.appUiLanguage
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                0 -> {
                    val lessons = viewModel.getLessonsForCurrentLanguage()
                    LearnPathScreen(
                        userProfile = userProfile,
                        lessons = lessons,
                        robotEmotion = robotEmotion,
                        robotDialogue = robotDialogue,
                        onLessonSelected = { lesson -> viewModel.startLesson(lesson) },
                        onRobotPoke = { viewModel.pokeRobot() },
                        onSwitchLanguage = { code -> viewModel.setActiveLanguage(code) },
                        onOpenLanguagePicker = { viewModel.openLanguagePicker(true) },
                        tts = viewModel.tts
                    )
                }
                1 -> {
                    ByteChatScreen(
                        userProfile = userProfile,
                        chatMessages = chatMessages,
                        isAiTyping = isAiTyping,
                        tts = viewModel.tts,
                        onSendMessage = { text, img ->
                            viewModel.sendMessageToByte(text, img)
                        }
                    )
                }
                2 -> {
                    AiAdaptivePlanScreen(
                        userProfile = userProfile,
                        aiPlan = aiPlan,
                        isLoading = isGeneratingAiPlan,
                        onRefreshPlan = { viewModel.refreshAiPlan() },
                        onStartAdaptiveLesson = {
                            val lessons = viewModel.getLessonsForCurrentLanguage()
                            val lesson = lessons.firstOrNull() ?: return@AiAdaptivePlanScreen
                            viewModel.startLesson(lesson)
                        }
                    )
                }
                3 -> {
                    RobotLabScreen(
                        userProfile = userProfile,
                        currentEmotion = robotEmotion,
                        robotDialogue = robotDialogue,
                        dailyQuests = dailyQuests,
                        onSetEmotion = { emotion -> viewModel.setRobotEmotion(emotion) },
                        onSetVisorColor = { color -> viewModel.setRobotVisorColor(color) },
                        onSetChassisSkin = { skin -> viewModel.setRobotChassisSkin(skin) },
                        onSetAntennaAccessory = { acc -> viewModel.setRobotAntennaAccessory(acc) },
                        onSetChestBadge = { badge -> viewModel.setRobotChestBadge(badge) },
                        onRefillHearts = { viewModel.refillHeartsWithGems() }
                    )
                }
                4 -> {
                    ProfileScreen(
                        userProfile = userProfile,
                        onUpdateProfile = { name, uName, email ->
                            viewModel.updateUserProfile(name, uName, email)
                        },
                        onSwitchAccount = { uName ->
                            viewModel.switchAccount(uName)
                        },
                        onSetAvatar = { avatarId ->
                            viewModel.setAvatar(avatarId)
                        },
                        onLogout = {
                            viewModel.logoutAccount()
                        },
                        onSetAppLanguage = { langCode ->
                            viewModel.setAppUiLanguage(langCode)
                        },
                        onResetProgress = {
                            viewModel.resetAllProgress()
                        },
                        onOpenLanguagePicker = {
                            viewModel.openLanguagePicker(true)
                        },
                        tts = viewModel.tts
                    )
                }
            }
        }
    }
}
