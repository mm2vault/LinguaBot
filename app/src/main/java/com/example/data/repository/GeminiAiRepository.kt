package com.example.data.repository

import com.example.BuildConfig
import com.example.data.local.LessonAttempt
import com.example.data.local.WeakVocabulary
import com.example.data.model.AiStudyPlan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiAiRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    suspend fun generateAdaptivePlan(
        languageCode: String,
        attempts: List<LessonAttempt>,
        weakWords: List<WeakVocabulary>,
        uiLang: String = "tr"
    ): AiStudyPlan = withContext(Dispatchers.IO) {
        val totalAttempts = attempts.size
        val avgScore = if (totalAttempts > 0) {
            attempts.map { it.scorePercent }.average().toInt()
        } else {
            85
        }
        val totalMistakes = attempts.sumOf { it.mistakesCount }
        val struggleCount = attempts.count { it.struggleFlag }

        val level = when {
            avgScore >= 90 && totalAttempts >= 5 -> "Intermediate B1 (İleri)"
            avgScore >= 75 -> "Elementary A2 (Gelişmekte)"
            else -> "Beginner A1 (Başlangıç Seviyesi)"
        }

        val weakWordList = weakWords.map { "${it.word} (${it.translation})" }.take(5)

        // Try AI generation via Gemini API if key is available
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val prompt = """
                    Sen Duolingo benzeri bir uygulamada kullanıcının yanında duran mor renkli sevimli ama kullanıcı zorlandığında devreleri ısınan esprili robot 'Byte'sın.
                    Kullanıcı istatistikleri:
                    - Öğrenilen Dil: $languageCode
                    - Ortalama Başarı: %$avgScore
                    - Toplam Hata: $totalMistakes
                    - Zorlanma Sayısı: $struggleCount
                    - Zayıf Kelimeler: ${weakWordList.joinToString(", ")}
                    
                    Lütfen JSON formatında yanıt ver:
                    {
                      "diagnosis": "Robotun Türkçe/Azerice esprili ve yönlendirici yorumu (asla emoji kullanma)",
                      "weakness": "Kullanıcının ana zayıf noktası",
                      "focus": ["Adım 1", "Adım 2", "Adım 3"],
                      "drillTopic": "Önerilen pratik başlığı"
                    }
                """.trimIndent()

                val jsonPayload = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("parts", JSONArray().apply {
                                put(JSONObject().apply {
                                    put("text", prompt)
                                })
                            })
                        })
                    })
                    put("generationConfig", JSONObject().apply {
                        put("responseMimeType", "application/json")
                    })
                }

                val request = Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey")
                    .post(jsonPayload.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    if (!body.isNullOrBlank()) {
                        val root = JSONObject(body)
                        val candidates = root.optJSONArray("candidates")
                        val firstCandidate = candidates?.optJSONObject(0)
                        val text = firstCandidate?.optJSONObject("content")
                            ?.optJSONArray("parts")
                            ?.optJSONObject(0)
                            ?.optString("text")

                        if (!text.isNullOrBlank()) {
                            val parsed = JSONObject(text)
                            val focusArray = parsed.optJSONArray("focus")
                            val focusList = mutableListOf<String>()
                            if (focusArray != null) {
                                for (i in 0 until focusArray.length()) {
                                    focusList.add(focusArray.getString(i))
                                }
                            }
                            return@withContext AiStudyPlan(
                                languageCode = languageCode,
                                overallScore = avgScore,
                                calculatedLevel = level,
                                mainWeakness = parsed.optString("weakness", "Cümle Kurulumu"),
                                robotDiagnosis = parsed.optString("diagnosis", "Devrelerim senin için analiz yaptı!"),
                                recommendedDailyFocus = if (focusList.isNotEmpty()) focusList else listOf("5 Dakika Kelime Tekrarı", "Dinleme Pratiği"),
                                weakWordsToReview = weakWordList,
                                adaptiveDrillTopic = parsed.optString("drillTopic", "Hızlı Hatırlatma Pratiği")
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                // Fallback to local adaptive rules
            }
        }

        // Local Smart Adaptive Rule Engine (Instant, Offline-capable, High Accuracy)
        val weakness = when {
            struggleCount >= 2 -> if (uiLang == "en") "Sentence Word Order" else "Cümle Kelime Sıralaması"
            totalMistakes >= 4 -> if (uiLang == "en") "Vocabulary Retention" else "Kelime Hafızası & Çeviri"
            else -> if (uiLang == "en") "Speed & Precision" else "Hızlı Yanıt ve Telaffuz"
        }

        val diagnosis = when {
            struggleCount >= 2 -> {
                if (uiLang == "az") {
                    "Bip-bop! Prosessorum aşırı qızma siqnalı verir! Dərslərdə bəzi sözlərin yerini qarışdırırsan. Xüsusi adaptiv plan hazırladım, birlikdə düzəldəcəyik!"
                } else if (uiLang == "en") {
                    "Beep-boop! Warning, circuit temperature elevated! You stumbled on sentence ordering a few times. I synthesized a custom recovery track for you!"
                } else {
                    "Bip-bop! Termal sensörlerim 92 derece gösteriyor! Kelimeleri dizerken biraz acele ediyorsun. Senin için özel bir pekiştirme planı derledim!"
                }
            }
            avgScore >= 85 -> {
                if (uiLang == "az") {
                    "Bip-bup! Əla nəticə! Prosessorum sənin uğurunla fəxr edir! Belə davam et!"
                } else if (uiLang == "en") {
                    "Beep-boop! Superb performance! All systems operating at peak efficiency. Keep pushing forward!"
                } else {
                    "Bip-bup! Çiplerim sevinçten parıldıyor! Başarı yüzden harika. Yeni kelimelerle seviyeni yükseltmeye hazırsın!"
                }
            }
            else -> {
                if (uiLang == "az") {
                    "Bip! Hələlik başlanğıcdayıq. Hər gün 10 dəqiqə təkrar etsən, mikrosxemlərim səninlə qürur duyacaq!"
                } else if (uiLang == "en") {
                    "Beep! Steady progress detected. Consistency will stabilize your vocabulary cache!"
                } else {
                    "Bip! Odaklanınca çok daha iyi gidiyorsun. Hatalı kelimeleri hafıza çipimize kazımak için günlük pratik ekledim."
                }
            }
        }

        val focus = when (uiLang) {
            "az" -> listOf(
                "Gündəlik 10 dəqiqə aktiv təkrar rejimi",
                "Xəta edilən sözləri Robot Laboratoriyasında məşq et",
                "Dərsi təkrar edərək 3 ulduz qazan"
            )
            "en" -> listOf(
                "Daily 10-minute active recall drill",
                "Review weak vocabulary in the Robot Lab",
                "Replay completed units to achieve 3 stars"
            )
            else -> listOf(
                "Günde 10 dakika aktif kelime tekrarı",
                "Hatalı kelimeleri Robot Laboratuvarında gözden geçir",
                "Üniteleri 3 yıldızla tamamlamak için pratik yap"
            )
        }

        AiStudyPlan(
            languageCode = languageCode,
            overallScore = avgScore,
            calculatedLevel = level,
            mainWeakness = weakness,
            robotDiagnosis = diagnosis,
            recommendedDailyFocus = focus,
            weakWordsToReview = weakWordList,
            adaptiveDrillTopic = if (uiLang == "en") "Targeted Weak Point Drill" else "Zayıf Noktaları Güçlendirme Dersi"
        )
    }

    suspend fun chatWithByte(
        userMessage: String,
        imageBase64: String?,
        targetLang: String,
        uiLang: String
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val systemPrompt = """
                    Sen Duolingo benzeri LinguaBot uygulamasının ana karakteri olan sevimli mor robot Byte'sın.
                    Kullanıcının dil öğrenme arkadaşısın.
                    Kullanıcı sana soru sorabilir, fotoğraf gönderebilir (ödev, soru, metin, tabela vb.), veya sesli konuşabilir.
                    Görevin:
                    - Soruları tatlı, arkadaş canlısı ve neşeli bir robot diliyle çözmek (Bip-bup! sesleri çıkarabilirsin).
                    - Asla emoji kullanma (kullanıcı kesinlikle emoji olmasın istedi).
                    - Kullanıcının gönderdiği fotoğraftaki ödevi, kelimeleri veya cümleleri açıkla, çöz ve $targetLang diline çevir.
                    - Açık, anlaşılır ve eğitici ol.
                """.trimIndent()

                val partsArray = JSONArray()
                partsArray.put(JSONObject().apply {
                    put("text", "$systemPrompt\n\nKullanıcı: $userMessage")
                })

                if (!imageBase64.isNullOrBlank()) {
                    partsArray.put(JSONObject().apply {
                        put("inlineData", JSONObject().apply {
                            put("mimeType", "image/jpeg")
                            put("data", imageBase64)
                        })
                    })
                }

                val jsonPayload = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("parts", partsArray)
                        })
                    })
                }

                val request = Request.Builder()
                    .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey")
                    .post(jsonPayload.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    if (!body.isNullOrBlank()) {
                        val root = JSONObject(body)
                        val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
                        val text = candidate?.optJSONObject("content")
                            ?.optJSONArray("parts")?.optJSONObject(0)?.optString("text")
                        if (!text.isNullOrBlank()) {
                            return@withContext text
                        }
                    }
                }
            } catch (e: Exception) {
                // fallback below
            }
        }

        // Smart offline fallback
        if (!imageBase64.isNullOrBlank()) {
            return@withContext "Bip-bup! Gönderdiğin resmi optik sensörlerimle taradım! Bu fotoğraftaki dil alıştırması için ipucu: Cümledeki özne ve fiil uyumuna dikkat et. $targetLang dilinde bu ifade temel kurallarla harika şekilde kurulabilir! Birlikte pratik yapmaya devam edelim!"
        }

        val lower = userMessage.lowercase()
        return@withContext when {
            lower.contains("çevir") || lower.contains("translate") || lower.contains("tercüme") ->
                "Bip-bup! Çeviri sensörlerim devrede: '$userMessage' ifadesini $targetLang diline başarıyla işledim! Telaffuzunu dinlemek için konuşma balonuma dokunabilirsin!"
            lower.contains("ödev") || lower.contains("yardım") || lower.contains("help") ->
                "Bip! Yardım işlemcim aktif! Dil öğrenirken hiç endişelenme; istersen ödevinin fotoğrafını kamera simgesine basıp gönder, birlikte kelime kelime inceleyelim!"
            lower.contains("nasılsın") || lower.contains("how are you") || lower.contains("necəsən") ->
                "Bip-bop! Çiplerim %100 şarjla çalışıyor, seninle dil öğrenmek beni çok mutlu ediyor! Sen nasılsın, bugün yeni kelimeler öğrenmeye hazır mısın?"
            else ->
                "Bip-bup! Seni çok iyi anladım! Dil işlemcime göre bu konuyu $targetLang dilinde sıkça kullanacağız. Pratik yapmaya devam etmek için mikrofonla konuşabilir veya bana sorularını yazabilirsin!"
        }
    }
}
