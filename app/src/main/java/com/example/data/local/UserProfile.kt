package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1,
    val activeLanguageCode: String = "es", // Spanish by default
    val appUiLanguage: String = "tr", // Turkish interface default as requested
    val xp: Int = 120,
    val hearts: Int = 5,
    val maxHearts: Int = 5,
    val lastHeartRefillTime: Long = System.currentTimeMillis(),
    val gems: Int = 350,
    val streakDays: Int = 4,
    val lastStreakDate: String = "2026-10-03",
    val completedLessonsIds: String = "unit1_l1,unit1_l2", // comma separated
    val robotVisorColor: String = "cyan", // cyan, purple, lime, red, amber, pink
    val robotChassisSkin: String = "classic", // classic, neon, amethyst, royal, magenta
    val robotAntennaAccessory: String = "orb", // orb, dual, crown, lightning, goggles, cap
    val robotChestBadge: String = "core", // core, heart, bolt, star, shield
    val streakFreezeCount: Int = 1,
    val weeklyStreakMask: String = "1,1,1,1,0,0,0",
    val longestStreak: Int = 14,
    val username: String = "dil_ogrencisi",
    val displayName: String = "Öğrenci",
    val email: String = "ogrenci@linguabot.com",
    val isLoggedIn: Boolean = true,
    val avatarId: String = "robot", // robot, cadet, scholar, polyglot, astronaut
    val totalWordsLearned: Int = 48,
    val levelTier: String = "Ametist Ligi",
    val dailyMinutesGoal: Int = 15,
    val ttsSpeed: Float = 1.0f,
    val hapticsEnabled: Boolean = true,
    val soundEnabled: Boolean = true
)
