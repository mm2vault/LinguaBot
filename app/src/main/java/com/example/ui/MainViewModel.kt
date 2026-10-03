package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.LessonAttempt
import com.example.data.local.UserProfile
import com.example.data.local.WeakVocabulary
import com.example.data.model.AiStudyPlan
import com.example.data.model.ChatMessage
import com.example.data.model.DailyQuest
import com.example.data.model.LanguageCatalog
import com.example.data.model.LeaderboardUser
import com.example.data.model.Lesson
import com.example.data.model.RobotDialogue
import com.example.data.model.RobotEmotion
import com.example.data.repository.GeminiAiRepository
import com.example.data.repository.LanguageRepository
import com.example.service.HapticsHelper
import com.example.service.SoundFxHelper
import com.example.service.TtsHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val userDao = db.userDao()
    private val languageRepo = LanguageRepository()
    private val geminiRepo = GeminiAiRepository()
    val tts = TtsHelper(application)
    val haptics = HapticsHelper(application)
    val soundFx = SoundFxHelper()

    // Current navigation state
    private val _currentTab = MutableStateFlow(0) // 0: Learn, 1: AI Plan, 2: Robot Lab, 3: League, 4: Settings
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    private val _isLanguagePickerOpen = MutableStateFlow(false)
    val isLanguagePickerOpen: StateFlow<Boolean> = _isLanguagePickerOpen.asStateFlow()

    private val _activeLesson = MutableStateFlow<Lesson?>(null)
    val activeLesson: StateFlow<Lesson?> = _activeLesson.asStateFlow()

    // Robot state
    private val _robotEmotion = MutableStateFlow(RobotEmotion.IDLE)
    val robotEmotion: StateFlow<RobotEmotion> = _robotEmotion.asStateFlow()

    private val _robotDialogueText = MutableStateFlow<String?>(null)
    val robotDialogueText: StateFlow<String?> = _robotDialogueText.asStateFlow()

    // User Profile Flow
    val userProfile: StateFlow<UserProfile> = userDao.getUserProfileFlow()
        .filterNotNull()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            UserProfile()
        )

    // AI Study Plan
    private val _aiPlan = MutableStateFlow<AiStudyPlan?>(null)
    val aiPlan: StateFlow<AiStudyPlan?> = _aiPlan.asStateFlow()

    private val _isGeneratingAiPlan = MutableStateFlow(false)
    val isGeneratingAiPlan: StateFlow<Boolean> = _isGeneratingAiPlan.asStateFlow()

    // Quests
    private val _dailyQuests = MutableStateFlow(
        listOf(
            DailyQuest("q1", "2 Ders Tamamla", "2 Dərs Tamamla", "Complete 2 Lessons", 1, 2, 20, false, "target"),
            DailyQuest("q2", "50 XP Kazan", "50 XP Qazan", "Earn 50 XP", 35, 50, 25, false, "lightning"),
            DailyQuest("q3", "Robotu Kızdırmadan Bitir", "Robotu Qəzəbləndirmədən Bitir", "Clear Lesson without Anger", 0, 1, 30, false, "streak")
        )
    )
    val dailyQuests: StateFlow<List<DailyQuest>> = _dailyQuests.asStateFlow()

    // Chat messages with Byte
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "init_1",
                sender = "byte",
                text = "Bip-bup! Merhaba! Ben mor robot yoldaşın Byte. Bana her dilde soru sorabilir, ödev veya soru resimlerini gönderebilir ya da mikrofona basıp konuşabilirsin! Tatlı robot sesimle sana yardım etmeye hazırım!"
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAiTyping = MutableStateFlow(false)
    val isAiTyping: StateFlow<Boolean> = _isAiTyping.asStateFlow()

    // Leaderboard
    val leaderboardUsers = listOf(
        LeaderboardUser(1, "Aylin K.", 480, 12, false, 0xFF3B82F6),
        LeaderboardUser(2, "Sen (Öğrenci)", 360, 4, true, 0xFF7C3AED),
        LeaderboardUser(3, "Murad Ə.", 340, 7, false, 0xFF10B981),
        LeaderboardUser(4, "Carlos M.", 310, 5, false, 0xFFF59E0B),
        LeaderboardUser(5, "Elena V.", 290, 8, false, 0xFFEC4899),
        LeaderboardUser(6, "Kenji T.", 260, 3, false, 0xFF6366F1),
        LeaderboardUser(7, "Sarah W.", 240, 6, false, 0xFF14B8A6),
        LeaderboardUser(8, "Mehmet B.", 210, 2, false, 0xFF8B5CF6)
    )

    init {
        viewModelScope.launch {
            if (userDao.getUserProfile() == null) {
                userDao.insertOrUpdateProfile(UserProfile())
            }
        }
        refreshAiPlan()
        updateRobotSpeech(RobotEmotion.IDLE)
    }

    fun selectTab(tabIndex: Int) {
        _currentTab.value = tabIndex
        if (tabIndex == 1) {
            refreshAiPlan()
        }
    }

    fun openLanguagePicker(open: Boolean) {
        _isLanguagePickerOpen.value = open
    }

    fun setActiveLanguage(code: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(activeLanguageCode = code))
            _isLanguagePickerOpen.value = false
            soundFx.playCorrect()
            val lang = LanguageCatalog.findByCode(code)
            tts.speak(lang.greeting, languageCode = code)
            refreshAiPlan()
        }
    }

    fun setAppUiLanguage(langCode: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(appUiLanguage = langCode))
            soundFx.playPoke()
            updateRobotSpeech(_robotEmotion.value)
        }
    }

    fun setRobotVisorColor(color: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(robotVisorColor = color))
        }
    }

    fun setRobotChassisSkin(skin: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(robotChassisSkin = skin))
        }
    }

    fun setRobotAntennaAccessory(accessory: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(robotAntennaAccessory = accessory))
        }
    }

    fun setRobotChestBadge(badge: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(robotChestBadge = badge))
        }
    }

    fun buyStreakFreeze() {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            if (current.gems >= 150 && current.streakFreezeCount < 2) {
                userDao.insertOrUpdateProfile(
                    current.copy(
                        gems = current.gems - 150,
                        streakFreezeCount = current.streakFreezeCount + 1
                    )
                )
                setRobotEmotion(RobotEmotion.HAPPY)
            }
        }
    }

    fun setRobotEmotion(emotion: RobotEmotion, customMistakeCount: Int = 0) {
        _robotEmotion.value = emotion
        updateRobotSpeech(emotion, customMistakeCount)
        if (emotion == RobotEmotion.SASSY_ANGRY) {
            haptics.vibrateAngryRobot()
        }
    }

    fun pokeRobot() {
        val next = when (_robotEmotion.value) {
            RobotEmotion.IDLE -> RobotEmotion.HAPPY
            RobotEmotion.HAPPY -> RobotEmotion.THINKING
            RobotEmotion.THINKING -> RobotEmotion.SASSY_ANGRY
            RobotEmotion.SASSY_ANGRY -> RobotEmotion.CELEBRATING
            RobotEmotion.CELEBRATING -> RobotEmotion.IDLE
        }
        setRobotEmotion(next)
    }

    private fun updateRobotSpeech(emotion: RobotEmotion, mistakeCount: Int = 0) {
        val uiLang = userProfile.value.appUiLanguage
        _robotDialogueText.value = RobotDialogue.getDialogue(emotion, uiLang, mistakeCount)
    }

    fun startLesson(lesson: Lesson) {
        _activeLesson.value = lesson
        setRobotEmotion(RobotEmotion.IDLE)
    }

    fun closeLesson() {
        _activeLesson.value = null
        setRobotEmotion(RobotEmotion.IDLE)
    }

    fun completeLesson(lessonId: String, scorePercent: Int, mistakes: Int, earnedXp: Int, earnedGems: Int) {
        viewModelScope.launch {
            val profile = userDao.getUserProfile() ?: UserProfile()
            val existingCompleted = profile.completedLessonsIds.split(",").filter { it.isNotBlank() }.toMutableSet()
            existingCompleted.add(lessonId)

            val newGems = profile.gems + earnedGems
            val newXp = profile.xp + earnedXp

            userDao.insertOrUpdateProfile(
                profile.copy(
                    xp = newXp,
                    gems = newGems,
                    completedLessonsIds = existingCompleted.joinToString(",")
                )
            )

            // Save Attempt
            userDao.insertAttempt(
                LessonAttempt(
                    lessonId = lessonId,
                    languageCode = profile.activeLanguageCode,
                    scorePercent = scorePercent,
                    mistakesCount = mistakes,
                    struggleFlag = mistakes >= 2
                )
            )

            // Advance daily quests
            val updatedQuests = _dailyQuests.value.map { q ->
                if (q.id == "q1") q.copy(currentProgress = (q.currentProgress + 1).coerceAtMost(q.targetProgress))
                else if (q.id == "q2") q.copy(currentProgress = (q.currentProgress + earnedXp).coerceAtMost(q.targetProgress))
                else if (q.id == "q3" && mistakes == 0) q.copy(currentProgress = 1)
                else q
            }
            _dailyQuests.value = updatedQuests

            setRobotEmotion(RobotEmotion.CELEBRATING)
        }
    }

    fun recordMistake(word: String, translation: String) {
        viewModelScope.launch {
            val profile = userDao.getUserProfile() ?: UserProfile()
            val newHearts = (profile.hearts - 1).coerceAtLeast(0)
            userDao.insertOrUpdateProfile(profile.copy(hearts = newHearts))
            haptics.vibrateWrong()

            if (word.isNotBlank()) {
                userDao.insertWeakWord(
                    WeakVocabulary(
                        languageCode = profile.activeLanguageCode,
                        word = word,
                        translation = translation,
                        errorCount = 1
                    )
                )
            }

            // Robot gets sassy/angry when hearts are low or mistakes occur
            setRobotEmotion(RobotEmotion.SASSY_ANGRY, customMistakeCount = 3)
        }
    }

    fun refillHeartsWithGems() {
        viewModelScope.launch {
            val profile = userDao.getUserProfile() ?: UserProfile()
            if (profile.gems >= 100) {
                userDao.insertOrUpdateProfile(
                    profile.copy(
                        hearts = 5,
                        gems = profile.gems - 100
                    )
                )
                setRobotEmotion(RobotEmotion.HAPPY)
            }
        }
    }

    fun refreshAiPlan() {
        viewModelScope.launch {
            _isGeneratingAiPlan.value = true
            val profile = userDao.getUserProfile() ?: UserProfile()
            val plan = geminiRepo.generateAdaptivePlan(
                languageCode = profile.activeLanguageCode,
                attempts = emptyList(), // In memory attempts
                weakWords = emptyList(),
                uiLang = profile.appUiLanguage
            )
            _aiPlan.value = plan
            _isGeneratingAiPlan.value = false
        }
    }

    fun resetAllProgress() {
        viewModelScope.launch {
            userDao.insertOrUpdateProfile(UserProfile(id = 1))
            refreshAiPlan()
        }
    }

    fun sendMessageToByte(userText: String, imageBase64: String?) {
        val userMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            sender = "user",
            text = if (userText.isBlank() && imageBase64 != null) "Bu resimdeki ödevi çözer ve açıklar mısın?" else userText,
            imageBase64 = imageBase64
        )
        _chatMessages.value = _chatMessages.value + userMsg

        viewModelScope.launch {
            _isAiTyping.value = true
            setRobotEmotion(RobotEmotion.THINKING)
            val profile = userDao.getUserProfile() ?: UserProfile()
            val replyText = geminiRepo.chatWithByte(
                userMessage = userMsg.text,
                imageBase64 = imageBase64,
                targetLang = profile.activeLanguageCode,
                uiLang = profile.appUiLanguage
            )

            val byteMsg = ChatMessage(
                id = "reply_${System.currentTimeMillis()}",
                sender = "byte",
                text = replyText
            )
            _chatMessages.value = _chatMessages.value + byteMsg
            _isAiTyping.value = false
            setRobotEmotion(RobotEmotion.HAPPY)
            // Natural fluent human voice pronunciation
            tts.speak(replyText.take(130), profile.activeLanguageCode, isRobotVoice = false)
        }
    }

    fun updateUserProfile(displayName: String, username: String, email: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(
                current.copy(
                    displayName = displayName,
                    username = username,
                    email = email,
                    isLoggedIn = true
                )
            )
            setRobotEmotion(RobotEmotion.CELEBRATING)
        }
    }

    fun setAvatar(avatarId: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(current.copy(avatarId = avatarId))
            setRobotEmotion(RobotEmotion.HAPPY)
        }
    }

    fun logoutAccount() {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            userDao.insertOrUpdateProfile(
                current.copy(
                    username = "misafir_kullanici",
                    displayName = "Misafir Öğrenci",
                    email = "misafir@linguabot.com",
                    isLoggedIn = false
                )
            )
            setRobotEmotion(RobotEmotion.IDLE)
        }
    }

    fun switchAccount(username: String) {
        viewModelScope.launch {
            val current = userDao.getUserProfile() ?: UserProfile()
            val newProfile = when (username.lowercase()) {
                "alex_polyglot" -> current.copy(
                    username = "alex_polyglot",
                    displayName = "Alex M. (Poliglot)",
                    email = "alex@linguabot.com",
                    xp = 840,
                    streakDays = 14,
                    totalWordsLearned = 120,
                    levelTier = "Elmas Ligi",
                    activeLanguageCode = "ja",
                    avatarId = "scholar",
                    isLoggedIn = true
                )
                "murad_eliyev" -> current.copy(
                    username = "murad_eliyev",
                    displayName = "Murad Əliyev",
                    email = "murad@linguabot.com",
                    xp = 520,
                    streakDays = 9,
                    totalWordsLearned = 75,
                    levelTier = "Zümrüt Ligi",
                    activeLanguageCode = "en",
                    avatarId = "cadet",
                    isLoggedIn = true
                )
                "zeynep_ogrenci" -> current.copy(
                    username = "zeynep_ogrenci",
                    displayName = "Zeynep Kaya",
                    email = "zeynep@linguabot.com",
                    xp = 320,
                    streakDays = 5,
                    totalWordsLearned = 48,
                    levelTier = "Ametist Ligi",
                    activeLanguageCode = "es",
                    avatarId = "robot",
                    isLoggedIn = true
                )
                else -> {
                    val d = username.replace("_", " ").replaceFirstChar { it.uppercase() }
                    current.copy(
                        username = username,
                        displayName = d,
                        email = "$username@linguabot.com",
                        isLoggedIn = true
                    )
                }
            }
            userDao.insertOrUpdateProfile(newProfile)
            setRobotEmotion(RobotEmotion.CELEBRATING)
        }
    }

    fun getLessonsForCurrentLanguage(): List<Lesson> {
        val code = userProfile.value.activeLanguageCode
        val uiLang = userProfile.value.appUiLanguage
        return languageRepo.getLessonsForLanguage(code, uiLang)
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
    }
}
