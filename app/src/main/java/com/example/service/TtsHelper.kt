package com.example.service

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import java.util.Locale

class TtsHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false
    private var pendingSpeak: (() -> Unit)? = null

    var isSpeaking: Boolean = false
        private set

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            tts?.language = Locale.getDefault()

            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    isSpeaking = true
                }

                override fun onDone(utteranceId: String?) {
                    isSpeaking = false
                }

                override fun onError(utteranceId: String?) {
                    isSpeaking = false
                }
            })

            pendingSpeak?.invoke()
            pendingSpeak = null
        } else {
            Log.e("TtsHelper", "TTS Initialization failed with code $status")
        }
    }

    /**
     * Speaks text using pure, natural human voice synthesis
     */
    fun speak(
        text: String,
        languageCode: String = "en",
        speed: Float = 1.0f,
        isRobotVoice: Boolean = false,
        customPitch: Float = 1.0f
    ) {
        if (!isInitialized || tts == null) {
            pendingSpeak = { speak(text, languageCode, speed, isRobotVoice, customPitch) }
            return
        }
        try {
            val code = languageCode.lowercase().trim()
            val targetLocale = getLocaleForCode(code)

            var res = tts?.setLanguage(targetLocale)
            if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Azerbaijani natural phonetic fallback
                if (code == "az") {
                    res = tts?.setLanguage(Locale.forLanguageTag("tr-TR"))
                }
                if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    val genericLocale = try { Locale(code) } catch (e: Exception) { Locale.US }
                    val fbRes = tts?.setLanguage(genericLocale)
                    if (fbRes == TextToSpeech.LANG_MISSING_DATA || fbRes == TextToSpeech.LANG_NOT_SUPPORTED) {
                        tts?.language = Locale.US
                    }
                }
            }

            // Find the best quality human voice
            try {
                val voices = tts?.voices
                if (voices != null && voices.isNotEmpty()) {
                    val currentLang = tts?.language?.language ?: targetLocale.language
                    // Prefer high quality, neural, natural human voices
                    val bestVoice = voices.firstOrNull { v ->
                        v.locale.language.equals(currentLang, ignoreCase = true) &&
                        !v.isNetworkConnectionRequired &&
                        (v.quality >= Voice.QUALITY_HIGH || v.name.contains("neural", true) || v.name.contains("natural", true) || v.name.contains("wavenet", true))
                    } ?: voices.firstOrNull { v ->
                        v.locale.language.equals(currentLang, ignoreCase = true) &&
                        !v.isNetworkConnectionRequired &&
                        v.quality >= Voice.QUALITY_NORMAL
                    } ?: voices.firstOrNull { v ->
                        v.locale.language.equals(currentLang, ignoreCase = true)
                    }

                    if (bestVoice != null) {
                        tts?.voice = bestVoice
                    }
                }
            } catch (e: Exception) {
                // Voice selection fallback
            }

            // Clean text completely from emojis, markdown symbols, and bracket annotations
            val cleanText = text
                .replace(Regex("[*#_`~>|\\[\\]()\\{\\}]"), " ")
                .replace(Regex("[\\p{So}\\p{Cn}]"), "")
                .replace(Regex("[0-9]+\\s*pairs", RegexOption.IGNORE_CASE), "")
                .replace(Regex("\\s+"), " ")
                .trim()

            if (cleanText.isEmpty()) return

            // Pure natural human voice intonation (Warm, clear, human pace)
            val pitch = 1.0f
            val speechRate = 0.95f * speed.coerceIn(0.75f, 1.3f)

            tts?.setPitch(pitch)
            tts?.setSpeechRate(speechRate)
            tts?.speak(cleanText, TextToSpeech.QUEUE_FLUSH, null, "tts_${System.currentTimeMillis()}")
        } catch (e: Exception) {
            Log.e("TtsHelper", "Speech error: ${e.message}")
        }
    }

    private fun getLocaleForCode(code: String): Locale {
        return when (code) {
            "es" -> Locale.forLanguageTag("es-ES")
            "en" -> Locale.US
            "fr" -> Locale.FRANCE
            "de" -> Locale.GERMANY
            "it" -> Locale.ITALY
            "tr" -> Locale.forLanguageTag("tr-TR")
            "az" -> Locale.forLanguageTag("az-AZ")
            "ru" -> Locale.forLanguageTag("ru-RU")
            "ja" -> Locale.JAPAN
            "ko" -> Locale.KOREA
            "zh" -> Locale.CHINA
            "ar" -> Locale.forLanguageTag("ar-SA")
            "pt" -> Locale.forLanguageTag("pt-PT")
            "nl" -> Locale.forLanguageTag("nl-NL")
            "pl" -> Locale.forLanguageTag("pl-PL")
            "sv" -> Locale.forLanguageTag("sv-SE")
            "no" -> Locale.forLanguageTag("nb-NO")
            "da" -> Locale.forLanguageTag("da-DK")
            "fi" -> Locale.forLanguageTag("fi-FI")
            "el" -> Locale.forLanguageTag("el-GR")
            "hi" -> Locale.forLanguageTag("hi-IN")
            "vi" -> Locale.forLanguageTag("vi-VN")
            "id" -> Locale.forLanguageTag("id-ID")
            "cs" -> Locale.forLanguageTag("cs-CZ")
            "uk" -> Locale.forLanguageTag("uk-UA")
            "ro" -> Locale.forLanguageTag("ro-RO")
            "hu" -> Locale.forLanguageTag("hu-HU")
            "he" -> Locale.forLanguageTag("he-IL")
            "th" -> Locale.forLanguageTag("th-TH")
            "fa" -> Locale.forLanguageTag("fa-IR")
            "bn" -> Locale.forLanguageTag("bn-BD")
            "tl" -> Locale.forLanguageTag("fil-PH")
            "sw" -> Locale.forLanguageTag("sw-KE")
            "ms" -> Locale.forLanguageTag("ms-MY")
            "ur" -> Locale.forLanguageTag("ur-PK")
            "ta" -> Locale.forLanguageTag("ta-IN")
            "te" -> Locale.forLanguageTag("te-IN")
            else -> Locale.forLanguageTag(code)
        }
    }

    fun stop() {
        try {
            tts?.stop()
            isSpeaking = false
        } catch (e: Exception) {
            // Ignore
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
            tts = null
            isInitialized = false
        } catch (e: Exception) {
            // Ignore
        }
    }
}
