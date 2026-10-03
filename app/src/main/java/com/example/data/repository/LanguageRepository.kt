package com.example.data.repository

import com.example.data.model.LanguageCatalog
import com.example.data.model.Lesson

class LanguageRepository {

    fun getLessonsForLanguage(languageCode: String, uiLang: String = "tr"): List<Lesson> {
        val lang = LanguageCatalog.findByCode(languageCode)
        return when (languageCode.lowercase().trim()) {
            "es" -> CurriculumEuropean.getSpanishLessons(lang, uiLang)
            "en" -> CurriculumEuropean.getEnglishLessons(lang, uiLang)
            "de" -> CurriculumEuropean.getGermanLessons(lang, uiLang)
            "fr" -> CurriculumEuropean.getFrenchLessons(lang, uiLang)
            "it" -> CurriculumEuropean.getItalianLessons(lang, uiLang)
            "pt" -> CurriculumEuropean.getPortugueseLessons(lang, uiLang)
            "tr" -> CurriculumTurkicSlavic.getTurkishLessons(lang, uiLang)
            "az" -> CurriculumTurkicSlavic.getAzerbaijaniLessons(lang, uiLang)
            "ru" -> CurriculumTurkicSlavic.getRussianLessons(lang, uiLang)
            "ja" -> CurriculumAsianMiddleEast.getJapaneseLessons(lang, uiLang)
            "ko" -> CurriculumAsianMiddleEast.getKoreanLessons(lang, uiLang)
            "zh" -> CurriculumAsianMiddleEast.getChineseLessons(lang, uiLang)
            "ar" -> CurriculumAsianMiddleEast.getArabicLessons(lang, uiLang)
            else -> CurriculumAsianMiddleEast.generateGenericCurriculum(lang, uiLang)
        }
    }
}
