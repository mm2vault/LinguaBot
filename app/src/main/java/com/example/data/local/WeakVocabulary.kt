package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weak_vocabulary")
data class WeakVocabulary(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val languageCode: String,
    val word: String,
    val translation: String,
    val errorCount: Int = 1,
    val lastPracticed: Long = System.currentTimeMillis()
)
