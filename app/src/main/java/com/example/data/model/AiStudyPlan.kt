package com.example.data.model

data class AiStudyPlan(
    val languageCode: String,
    val overallScore: Int, // 0-100
    val calculatedLevel: String,
    val mainWeakness: String,
    val robotDiagnosis: String,
    val recommendedDailyFocus: List<String>,
    val weakWordsToReview: List<String>,
    val adaptiveDrillTopic: String,
    val generatedDate: Long = System.currentTimeMillis()
)
