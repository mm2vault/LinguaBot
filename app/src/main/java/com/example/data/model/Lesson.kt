package com.example.data.model

data class Lesson(
    val id: String,
    val unitNumber: Int,
    val unitTitle: String,
    val unitDescription: String,
    val title: String,
    val subtitle: String,
    val languageCode: String = "es",
    val iconType: String = "star", // star, speech, coffee, airplane, book, chip, crown
    val xpReward: Int = 20,
    val gemReward: Int = 10,
    val isMilestone: Boolean = false,
    val exercises: List<Exercise>
)
