package com.example.data.repository

import com.example.data.model.Exercise
import com.example.data.model.ExerciseType
import com.example.data.model.Language
import com.example.data.model.Lesson

object CurriculumAsianMiddleEast {

    fun getJapaneseLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Yapon Dili Əsasları" else if (uiLang == "en") "Unit 1: Japanese Basics" else "Ünite 1: Japonca Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Dadlı Çay" else if (uiLang == "en") "Unit 2: Café & Green Tea" else "Ünite 2: Kafe & Yeşil Çay"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Səyahət" else if (uiLang == "en") "Unit 3: City & Travel" else "Ünite 3: Tokyo & Şehir Gezisi"
        val u4 = if (uiLang == "az") "Vahid 4: Ailə & Dostlar" else if (uiLang == "en") "Unit 4: Family & Friends" else "Ünite 4: Aile & Arkadaşlar"
        val u5 = if (uiLang == "az") "Vahid 5: Tokio & Böyük Ustalığ Sandığı" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Japonca Büyük Şampiyonluk Sandığı"

        return listOf(
            Lesson(
                id = "ja_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Temel selamlar",
                title = "Konnichiwa & Arigato", subtitle = "こんにちは", languageCode = "ja", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("ja_1_1", ExerciseType.MULTIPLE_CHOICE, "Japonca 'Konnichiwa' ne demektir?", "こんにちは", "Merhaba / İyi günler", listOf("Merhaba / İyi günler", "Teşekkürler", "Hoşça kal", "Lütfen")),
                    Exercise("ja_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Arigato gozaimasu'", "ありがとうございます", "ありがとうございます", explanation = "Çok teşekkür ederim dedin!"),
                    Exercise("ja_1_2", ExerciseType.MATCH_PAIRS, "Japonca selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("こんにちは (Konnichiwa)" to "Merhaba", "ありがとう (Arigato)" to "Teşekkürler", "さようなら (Sayonara)" to "Hoşça kal", "おはよう (Ohayou)" to "Günaydın")),
                    Exercise("ja_1_3", ExerciseType.SENTENCE_ORDER, "'Hajimemashite' (Tanıştığıma memnun oldum) kur:", "はじめまして", "はじめまして", listOf("はじめまして", "ありがとう", "こんにちは"))
                )
            ),
            Lesson(
                id = "ja_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "Kendini tanıtma",
                title = "Watashi wa...", subtitle = "私は...", languageCode = "ja", iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("ja_1_4", ExerciseType.FILL_BLANK, "Watashi _____ gakusei desu. (Ben öğrenciyim.)", "私は学生です", "は", listOf("は", "の", "を", "に")),
                    Exercise("ja_1_5", ExerciseType.MATCH_PAIRS, "Temel kelimeler:", correctAnswer = "4_pairs", pairMap = mapOf("私 (Watashi)" to "Ben", "あなた (Anata)" to "Sen", "友達 (Tomodachi)" to "Arkadaş", "先生 (Sensei)" to "Öğretmen"))
                )
            ),
            Lesson(
                id = "ja_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede yeşil çay",
                title = "Ocha o kudasai", subtitle = "お茶をください", languageCode = "ja", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("ja_2_1", ExerciseType.SENTENCE_ORDER, "'Ocha o kudasai' (Yeşil çay lütfen) de:", "お茶をください", "お茶をください", listOf("お茶を", "ください", "水", "ありがとう")),
                    Exercise("ja_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Oishii desu'", "おいしいです", "おいしいです", explanation = "Lezzetli dedin!"),
                    Exercise("ja_2_2", ExerciseType.MATCH_PAIRS, "Yemek ve içecek:", correctAnswer = "4_pairs", pairMap = mapOf("お茶 (Ocha)" to "Çay", "水 (Mizu)" to "Su", "ラーメン (Ramen)" to "Ramen", "おいしい (Oishii)" to "Lezzetli"))
                )
            ),
            Lesson(
                id = "ja_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Tokyo ve İstasyon",
                title = "Eki wa doko desu ka?", subtitle = "駅はどこですか", languageCode = "ja", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("ja_3_1", ExerciseType.MATCH_PAIRS, "Ulaşım kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("駅 (Eki)" to "İstasyon", "ホテル (Hoteru)" to "Otel", "電車 (Densha)" to "Tren", "空港 (Kuukou)" to "Havalimanı")),
                    Exercise("ja_3_2", ExerciseType.SENTENCE_ORDER, "'Eki wa doko desu ka?' (İstasyon nerede?) kur:", "駅はどこですか", "駅はどこですか", listOf("駅は", "どこですか", "ホテル", "ありがとう"))
                )
            ),
            Lesson(
                id = "ja_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "Japonca Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "ja", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("ja_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk sözcükleri:", correctAnswer = "4_pairs", pairMap = mapOf("ロボット (Robotto)" to "Robot", "星 (Hoshi)" to "Yıldız", "未来 (Mirai)" to "Gelecek", "勝利 (Shouri)" to "Zafer")),
                    Exercise("ja_5_2", ExerciseType.SENTENCE_ORDER, "'Nihongo ga dekimasu' (Japonca konuşabiliyorum) kur:", "日本語ができます", "日本語ができます", listOf("日本語が", "できます", "友達", "先生"))
                )
            )
        )
    }

    fun getKoreanLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Koreya Dili Əsasları" else if (uiLang == "en") "Unit 1: Korean Basics" else "Ünite 1: Korece Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Dadlı Yeməklər" else if (uiLang == "en") "Unit 2: Café & Food" else "Ünite 2: Kafe & Kore Yemekleri"
        val u3 = if (uiLang == "az") "Vahid 3: Seul & Səyahət" else if (uiLang == "en") "Unit 3: Seoul & Travel" else "Ünite 3: Seul & Seyahat"
        val u5 = if (uiLang == "az") "Vahid 5: Seul & Böyük Ustalığ Sandığı" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Korece Büyük Şampiyonluk Sandığı"

        return listOf(
            Lesson(
                id = "ko_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Temel selamlar",
                title = "Annyeonghaseyo & Gamsahamnida", subtitle = "안녕하세요", languageCode = "ko", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("ko_1_1", ExerciseType.MULTIPLE_CHOICE, "'안녕하세요' (Annyeonghaseyo) ne demektir?", "안녕하세요", "Merhaba", listOf("Merhaba", "Teşekkürler", "Hoşça kal", "Lütfen")),
                    Exercise("ko_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: '감사합니다' (Gamsahamnida)", "감사합니다", "감사합니다", explanation = "Teşekkür ederim dedin!"),
                    Exercise("ko_1_2", ExerciseType.MATCH_PAIRS, "Korece selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("안녕하세요" to "Merhaba", "감사합니다" to "Teşekkürler", "안녕히 계세요" to "Hoşça kal", "반갑습니다" to "Tanıştığıma memnun oldum")),
                    Exercise("ko_1_3", ExerciseType.SENTENCE_ORDER, "'만나서 반갑습니다' (Tanıştığıma sevindim) kur:", "만나서 반갑습니다", "만나서 반갑습니다", listOf("만나서", "반갑습니다", "감사합니다", "안녕"))
                )
            ),
            Lesson(
                id = "ko_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede sipariş",
                title = "Keopi juseyo", subtitle = "커피 주세요", languageCode = "ko", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("ko_2_1", ExerciseType.SENTENCE_ORDER, "'커피 주세요' (Kahve lütfen) de:", "커피 주세요", "커피 주세요", listOf("커피", "주세요", "물", "감사합니다")),
                    Exercise("ko_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: '맛있어요' (Masisseoyo)", "맛있어요", "맛있어요", explanation = "Lezzetli dedin!"),
                    Exercise("ko_2_2", ExerciseType.MATCH_PAIRS, "Yemek ve içecek:", correctAnswer = "4_pairs", pairMap = mapOf("커피 (Keopi)" to "Kahve", "물 (Mul)" to "Su", "김치 (Kimchi)" to "Kimchi", "맛있어요" to "Lezzetli"))
                )
            ),
            Lesson(
                id = "ko_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Seul ve Ulaşım",
                title = "Jihacheol odiyeyo?", subtitle = "지하철 어디예요?", languageCode = "ko", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("ko_3_1", ExerciseType.MATCH_PAIRS, "Kelimeleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("지하철 (Jihacheol)" to "Metro", "호텔 (Hotel)" to "Otel", "로봇 (Robot)" to "Robot", "친구 (Chingu)" to "Arkadaş")),
                    Exercise("ko_3_2", ExerciseType.SENTENCE_ORDER, "'지하철 어디예요?' (Metro nerede?) kur:", "지하철 어디예요", "지하철 어디예요", listOf("지하철", "어디예요", "호텔", "감사합니다"))
                )
            ),
            Lesson(
                id = "ko_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "Korece Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "ko", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("ko_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("승리 (Seungri)" to "Zafer", "미래 (Mirae)" to "Gelecek", "별 (Byeol)" to "Yıldız", "챔피언 (Chaempieon)" to "Şampiyon")),
                    Exercise("ko_5_2", ExerciseType.SENTENCE_ORDER, "'한국어를 잘해요' (Koreceyi iyi konuşuyorum) kur:", "한국어를 잘해요", "한국어를 잘해요", listOf("한국어를", "잘해요", "친구", "감사합니다"))
                )
            )
        )
    }

    fun getChineseLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Çin Dili Əsasları" else if (uiLang == "en") "Unit 1: Chinese Basics" else "Ünite 1: Çince Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Çay & Kafe" else if (uiLang == "en") "Unit 2: Tea & Café" else "Ünite 2: Çay & Kafe"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Metro" else if (uiLang == "en") "Unit 3: City & Subway" else "Ünite 3: Şehir ve Metro"
        val u5 = if (uiLang == "az") "Vahid 5: Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Çince Büyük Şampiyonluk Sandığı"

        return listOf(
            Lesson(
                id = "zh_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Temel selamlar",
                title = "Nǐ hǎo & Xièxie", subtitle = "你好", languageCode = "zh", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("zh_1_1", ExerciseType.MULTIPLE_CHOICE, "'你好' (Nǐ hǎo) ne demektir?", "你好", "Merhaba", listOf("Merhaba", "Teşekkürler", "Hoşça kal", "Lütfen")),
                    Exercise("zh_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: '谢谢' (Xièxie)", "谢谢", "谢谢", explanation = "Teşekkürler dedin!"),
                    Exercise("zh_1_2", ExerciseType.MATCH_PAIRS, "Çince selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("你好 (Nǐ hǎo)" to "Merhaba", "谢谢 (Xièxie)" to "Teşekkürler", "再见 (Zàijiàn)" to "Hoşça kal", "早上好 (Zǎoshang hǎo)" to "Günaydın")),
                    Exercise("zh_1_3", ExerciseType.SENTENCE_ORDER, "'很高兴认识你' (Tanıştığıma sevindim) kur:", "很高兴认识你", "很高兴认识你", listOf("很高兴", "认识你", "你好", "谢谢"))
                )
            ),
            Lesson(
                id = "zh_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Çay ve yemek",
                title = "Yī bēi chá", subtitle = "一杯茶", languageCode = "zh", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("zh_2_1", ExerciseType.SENTENCE_ORDER, "'请给我一杯茶' (Lütfen bir fincan çay verin) de:", "请给我一杯茶", "请给我一杯茶", listOf("请给我", "一杯茶", "咖啡", "谢谢")),
                    Exercise("zh_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: '很好吃' (Hěn hào chī)", "很好吃", "很好吃", explanation = "Çok lezzetli dedin!"),
                    Exercise("zh_2_2", ExerciseType.MATCH_PAIRS, "Yemek ve içecek:", correctAnswer = "4_pairs", pairMap = mapOf("茶 (Chá)" to "Çay", "水 (Shuǐ)" to "Su", "咖啡 (Kāfēi)" to "Kahve", "好吃 (Hàochī)" to "Lezzetli"))
                )
            ),
            Lesson(
                id = "zh_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Şehir ve Metro",
                title = "Dìtiě zài nǎlǐ?", subtitle = "地铁站在哪里", languageCode = "zh", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("zh_3_1", ExerciseType.MATCH_PAIRS, "Kelimeleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("地铁 (Dìtiě)" to "Metro", "饭店 (Fàndiàn)" to "Otel / Restoran", "机器人 (Jīqìrén)" to "Robot", "朋友 (Péngyǒu)" to "Arkadaş")),
                    Exercise("zh_3_2", ExerciseType.SENTENCE_ORDER, "'地铁站在哪里？' (Metro nerede?) kur:", "地铁站在哪里", "地铁站在哪里", listOf("地铁站", "在哪里", "饭店", "谢谢"))
                )
            ),
            Lesson(
                id = "zh_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "Çince Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "zh", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("zh_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk sözcükleri:", correctAnswer = "4_pairs", pairMap = mapOf("胜利 (Shènglì)" to "Zafer", "冠军 (Guànjūn)" to "Şampiyon", "星星 (Xīngxing)" to "Yıldız", "未来 (Wèilái)" to "Gelecek")),
                    Exercise("zh_5_2", ExerciseType.SENTENCE_ORDER, "'我会说中文' (Çince konuşabiliyorum) kur:", "我会说中文", "我会说中文", listOf("我会说", "中文", "朋友", "谢谢"))
                )
            )
        )
    }

    fun getArabicLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Ərəb Dili Əsasları" else if (uiLang == "en") "Unit 1: Arabic Basics" else "Ünite 1: Arapça Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Qəhvə" else if (uiLang == "en") "Unit 2: Café & Coffee" else "Ünite 2: Kafe & Arap Kahvesi"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Səyahət" else if (uiLang == "en") "Unit 3: City & Travel" else "Ünite 3: Şehir ve Seyahat"
        val u5 = if (uiLang == "az") "Vahid 5: Ərəb Dili Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Arapça Büyük Şampiyonluk Sandığı"

        return listOf(
            Lesson(
                id = "ar_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Temel selamlar",
                title = "Marhaban & Shukran", subtitle = "مرحباً", languageCode = "ar", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("ar_1_1", ExerciseType.MULTIPLE_CHOICE, "'مرحباً' (Marhaban) ne demektir?", "مرحباً", "Merhaba", listOf("Merhaba", "Teşekkürler", "Hoşça kal", "Lütfen")),
                    Exercise("ar_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'شكراً جزيلاً' (Shukran jazilan)", "شكراً جزيلاً", "شكراً جزيلاً", explanation = "Çok teşekkürler dedin!"),
                    Exercise("ar_1_2", ExerciseType.MATCH_PAIRS, "Arapça selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("مرحباً" to "Merhaba", "شكراً" to "Teşekkürler", "صباح الخير" to "Günaydın", "مع السلامة" to "Güle güle")),
                    Exercise("ar_1_3", ExerciseType.SENTENCE_ORDER, "'أهلاً وسهلاً' (Hoş geldiniz) kur:", "أهلاً وسهلاً", "أهلاً وسهلاً", listOf("أهلاً", "وسهلاً", "شكراً", "مرحباً"))
                )
            ),
            Lesson(
                id = "ar_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kahve ve lezzetler",
                title = "Qahwa min fadlik", subtitle = "قهوة من فضلك", languageCode = "ar", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("ar_2_1", ExerciseType.SENTENCE_ORDER, "'قهوة من فضلك' (Kahve lütfen) de:", "قهوة من فضلك", "قهوة من فضلك", listOf("قهوة", "من", "فضلك", "ماء", "شكراً")),
                    Exercise("ar_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'ماء بارد' (Maa barid)", "ماء بارد", "ماء بارد", explanation = "Soğuk su dedin!"),
                    Exercise("ar_2_2", ExerciseType.MATCH_PAIRS, "Yiyecek ve içecek:", correctAnswer = "4_pairs", pairMap = mapOf("قهوة (Qahwa)" to "Kahve", "ماء (Maa)" to "Su", "شاي (Shay)" to "Çay", "لذيذ (Ladhidh)" to "Lezzetli"))
                )
            ),
            Lesson(
                id = "ar_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Şehir ve Otel",
                title = "Ayna al-funduq?", subtitle = "أين الفندق؟", languageCode = "ar", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("ar_3_1", ExerciseType.MATCH_PAIRS, "Kelimeleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("فندق (Funduq)" to "Otel", "مطار (Matar)" to "Havalimanı", "روبوت (Robot)" to "Robot", "صديق (Sadiq)" to "Arkadaş")),
                    Exercise("ar_3_2", ExerciseType.SENTENCE_ORDER, "'أين الفندق من فضلك؟' (Otel nerede?) kur:", "أين الفندق من فضلك", "أين الفندق من فضلك", listOf("أين", "الفندق", "من", "فضلك", "مطار"))
                )
            ),
            Lesson(
                id = "ar_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "Arapça Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "ar", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("ar_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk sözcükleri:", correctAnswer = "4_pairs", pairMap = mapOf("النصر (Al-nasr)" to "Zafer", "البطل (Al-batal)" to "Şampiyon", "النجم (Al-najm)" to "Yıldız", "المستقبل" to "Gelecek")),
                    Exercise("ar_5_2", ExerciseType.SENTENCE_ORDER, "'أتكلم العربية بطلاقة' (Akıcı Arapça konuşuyorum) kur:", "أتكلم العربية بطلاقة", "أتكلم العربية بطلاقة", listOf("أتكلم", "العربية", "بطلاقة", "صديق", "شكراً"))
                )
            )
        )
    }

    /**
     * Massive 5-Unit 12-Lesson curriculum generator for all 100+ languages
     */
    fun generateGenericCurriculum(lang: Language, uiLang: String): List<Lesson> {
        val greeting = lang.greeting
        val nat = lang.nativeName
        val disp = lang.getDisplayName(uiLang)
        val flag = lang.flagEmoji

        val u1 = if (uiLang == "az") "Vahid 1: $flag $disp Əsasları & Salamlar" else if (uiLang == "en") "Unit 1: $flag $disp Basics & Greetings" else "Ünite 1: $flag $disp Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: $flag $disp Kafe & Dadlı Qidalar" else if (uiLang == "en") "Unit 2: $flag $disp Café & Food" else "Ünite 2: $flag $disp Kafe & Yiyecekler"
        val u3 = if (uiLang == "az") "Vahid 3: $flag $disp Şəhər & Səyahət" else if (uiLang == "en") "Unit 3: $flag $disp City & Travel" else "Ünite 3: $flag $disp Şehir & Seyahat"
        val u4 = if (uiLang == "az") "Vahid 4: $flag $disp Ailə & Gündəlik Həyat" else if (uiLang == "en") "Unit 4: $flag $disp Family & Daily Life" else "Ünite 4: $flag $disp Aile & Günlük Yaşam"
        val u5 = if (uiLang == "az") "Vahid 5: $flag $disp Böyük Çempionluq Sandığı" else if (uiLang == "en") "Unit 5: $flag $disp Grand Championship" else "Ünite 5: $flag $disp Büyük Şampiyonluk Sandığı"

        return listOf(
            // Unit 1
            Lesson(
                id = "${lang.code}_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "$disp diline ilk adımlar",
                title = "Temel Selamlaşma", subtitle = "$greeting ($nat)", languageCode = lang.code, iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("${lang.code}_1_1", ExerciseType.MULTIPLE_CHOICE, "$disp dilinde '${greeting}' ne anlama gelir?", greeting, "Merhaba / Selam", listOf("Merhaba / Selam", "Hoşça kal", "Teşekkürler", "Lütfen"), explanation = "'$greeting', $disp dilinde geleneksel selamlaşmadır."),
                    Exercise("${lang.code}_1_speak", ExerciseType.SPEAK_PRONOUNCE, "$disp telaffuz pratiği: '$greeting' söyle", greeting, greeting, explanation = "Harika telaffuz! $disp dilinde '$greeting' demeyi başardın!"),
                    Exercise("${lang.code}_1_2", ExerciseType.MATCH_PAIRS, "Temel ifadeleri eşleştirin:", correctAnswer = "4_pairs", pairMap = mapOf(greeting to "Merhaba", "1" to "Bir", "2" to "İki", "OK" to "Tamam")),
                    Exercise("${lang.code}_1_3", ExerciseType.SENTENCE_ORDER, "Selamlaşma cümlesini kur:", "$greeting robot", "$greeting robot", listOf(greeting, "robot", "amigo", "super"))
                )
            ),
            Lesson(
                id = "${lang.code}_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "$disp tanışma",
                title = "Tanışma & Nezaket", subtitle = "İlk Cümleler", languageCode = lang.code, iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("${lang.code}_1_4", ExerciseType.MATCH_PAIRS, "Nezaket sözcükleri:", correctAnswer = "4_pairs", pairMap = mapOf("Friend" to "Arkadaş", "Good" to "İyi", "Yes" to "Evet", "No" to "Hayır")),
                    Exercise("${lang.code}_1_4speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: '$greeting $disp'", "$greeting $disp", "$greeting $disp"),
                    Exercise("${lang.code}_1_5", ExerciseType.MULTIPLE_CHOICE, "$disp dilini öğrenmeye hazır mısın?", "Evet, hazırım!", "Evet, hazırım!", listOf("Evet, hazırım!", "Daha sonra", "Zor görünüyor", "Belki"))
                )
            ),
            Lesson(
                id = "${lang.code}_u1_l3", unitNumber = 1, unitTitle = u1, unitDescription = "1. Bölüm Sandığı",
                title = "1. Bölüm Ustalık Sandığı", subtitle = "Temel Kupa", languageCode = lang.code, iconType = "crown", xpReward = 35, gemReward = 20, isMilestone = true,
                exercises = listOf(
                    Exercise("${lang.code}_1_6", ExerciseType.SENTENCE_ORDER, "Cümleyi birleştir:", "$greeting friend", "$greeting friend", listOf(greeting, "friend", "yes", "good")),
                    Exercise("${lang.code}_1_7", ExerciseType.MATCH_PAIRS, "Hızlı tekrar:", correctAnswer = "4_pairs", pairMap = mapOf(greeting to "Selam", "Lingua" to "Dil", "Star" to "Yıldız", "Bot" to "Robot"))
                )
            ),

            // Unit 2
            Lesson(
                id = "${lang.code}_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "$disp kafe siparişleri",
                title = "Kafe & İçecekler", subtitle = "Kahve & Çay", languageCode = lang.code, iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("${lang.code}_2_1", ExerciseType.MATCH_PAIRS, "Kafe kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Café" to "Kahve", "Tea" to "Çay", "Water" to "Su", "Menu" to "Menü")),
                    Exercise("${lang.code}_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Café please'", "Café please", "Café please"),
                    Exercise("${lang.code}_2_2", ExerciseType.SENTENCE_ORDER, "'Café please' cümlesini kur:", "Café please", "Café please", listOf("Café", "please", "water", "tea"))
                )
            ),
            Lesson(
                id = "${lang.code}_u2_l2", unitNumber = 2, unitTitle = u2, unitDescription = "Lezzetli Yemekler",
                title = "Restoran & Hesap", subtitle = "Lezzetler", languageCode = lang.code, iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("${lang.code}_2_3", ExerciseType.MATCH_PAIRS, "Restoran eşleştirmesi:", correctAnswer = "4_pairs", pairMap = mapOf("Bread" to "Ekmek", "Cheese" to "Peynir", "Check" to "Hesap", "Delicious" to "Lezzetli")),
                    Exercise("${lang.code}_2_4", ExerciseType.FILL_BLANK, "Water and _____ please. (Su ve ekmek lütfen)", "Water and Bread please", "Bread", listOf("Bread", "Hotel", "Car", "Train"))
                )
            ),

            // Unit 3
            Lesson(
                id = "${lang.code}_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "$disp seyahat ve şehir",
                title = "Şehir & Otel", subtitle = "Yolculuk", languageCode = lang.code, iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("${lang.code}_3_1", ExerciseType.MATCH_PAIRS, "Seyahat kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Hotel" to "Otel", "Station" to "İstasyon", "Airport" to "Havalimanı", "Ticket" to "Bilet")),
                    Exercise("${lang.code}_3_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Where is the hotel?'", "Where is the hotel", "Where is the hotel"),
                    Exercise("${lang.code}_3_2", ExerciseType.SENTENCE_ORDER, "Cümleyi kur:", "Where is hotel", "Where is hotel", listOf("Where", "is", "hotel", "airport"))
                )
            ),

            // Unit 4
            Lesson(
                id = "${lang.code}_u4_l1", unitNumber = 4, unitTitle = u4, unitDescription = "$disp aile ve yaşam",
                title = "Ailem & Arkadaşlarım", subtitle = "Günlük Yaşam", languageCode = lang.code, iconType = "star", xpReward = 30,
                exercises = listOf(
                    Exercise("${lang.code}_4_1", ExerciseType.MATCH_PAIRS, "Aile kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Mother" to "Anne", "Father" to "Baba", "Sister" to "Kız kardeş", "Brother" to "Erkek kardeş")),
                    Exercise("${lang.code}_4_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'I love my family'", "I love my family", "I love my family")
                )
            ),

            // Unit 5: Grand Championship
            Lesson(
                id = "${lang.code}_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "$disp Şampiyonluk Sandığı", subtitle = "👑 Grand Master Championship", languageCode = lang.code, iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("${lang.code}_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk sözcükleri:", correctAnswer = "4_pairs", pairMap = mapOf(greeting to "Selam", "Victory" to "Zafer", "Champion" to "Şampiyon", "Future" to "Gelecek")),
                    Exercise("${lang.code}_5_2", ExerciseType.SENTENCE_ORDER, "Şampiyonluk cümlesini kur:", "$greeting $disp champion", "$greeting $disp champion", listOf(greeting, disp, "champion", "star", "victory")),
                    Exercise("${lang.code}_5_speak", ExerciseType.SPEAK_PRONOUNCE, "Final şampiyonluk telaffuzu: '$greeting $nat'", "$greeting $nat", "$greeting $nat", explanation = "Tebrikler! $disp dilinin şampiyonu oldun!")
                )
            )
        )
    }
}
