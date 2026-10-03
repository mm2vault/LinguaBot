package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_attempts")
data class LessonAttempt(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val lessonId: String,
    val languageCode: String,
    val scorePercent: Int,
    val mistakesCount: Int,
    val struggleFlag: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
