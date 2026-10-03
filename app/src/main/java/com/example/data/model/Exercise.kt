package com.example.data.model

enum class ExerciseType {
    MULTIPLE_CHOICE,
    SENTENCE_ORDER,
    MATCH_PAIRS,
    FILL_BLANK,
    LISTEN_TRANSLATE,
    SPEAK_PRONOUNCE
}

data class Exercise(
    val id: String,
    val type: ExerciseType,
    val prompt: String, // Question or phrase to translate
    val ttsTargetText: String? = null, // Audio text for TTS pronunciation
    val correctAnswer: String, // Correct string or ordered comma-separated words
    val options: List<String> = emptyList(), // For multiple choice or word tiles
    val pairMap: Map<String, String> = emptyMap(), // For MATCH_PAIRS: target -> source
    val explanation: String = "" // Robot hint / grammar explanation
)
