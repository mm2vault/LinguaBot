package com.example.data.model

data class DailyQuest(
    val id: String,
    val titleTr: String,
    val titleAz: String,
    val titleEn: String,
    val currentProgress: Int,
    val targetProgress: Int,
    val gemReward: Int,
    val isCompleted: Boolean = currentProgress >= targetProgress,
    val iconKey: String // "streak", "target", "perfect", "lightning"
) {
    fun getTitle(uiLang: String): String = when (uiLang) {
        "az" -> titleAz
        "en" -> titleEn
        else -> titleTr
    }
}

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val xp: Int,
    val streak: Int,
    val isCurrentUser: Boolean = false,
    val avatarTintHex: Long = 0xFF7C3AED
)
