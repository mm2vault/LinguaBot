package com.example.data.repository

import com.example.data.model.Exercise
import com.example.data.model.ExerciseType
import com.example.data.model.Language
import com.example.data.model.Lesson

object CurriculumEuropean {

    fun getSpanishLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: İspan Dili Əsasları" else if (uiLang == "en") "Unit 1: Spanish Basics" else "Ünite 1: İspanyolca Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Dadlı Yeməklər" else if (uiLang == "en") "Unit 2: Café & Food" else "Ünite 2: Kafe & Lezzetli Yemekler"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Səyahət" else if (uiLang == "en") "Unit 3: City & Travel" else "Ünite 3: Şehir ve Seyahat"
        val u4 = if (uiLang == "az") "Vahid 4: Ailə & Gündəlik Həyat" else if (uiLang == "en") "Unit 4: Family & Daily Life" else "Ünite 4: Aile & Günlük Yaşam"
        val u5 = if (uiLang == "az") "Vahid 5: Süni İntellekt & Çempionluq" else if (uiLang == "en") "Unit 5: AI & Championship" else "Ünite 5: Yapay Zeka & Büyük Şampiyonluk"

        return listOf(
            // Unit 1
            Lesson(
                id = "es_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "İlk adımlar ve tanışma",
                title = "Merhaba & Teşekkür", subtitle = "¡Hola! & Gracias", languageCode = "es", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("es_1_1", ExerciseType.MULTIPLE_CHOICE, "İspanyolca 'Merhaba' nasıl denir?", "Hola", "Hola", listOf("Hola", "Adiós", "Por favor", "Gracias")),
                    Exercise("es_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Hola amigo'", "Hola amigo", "Hola amigo", explanation = "H harfi okunmaz, 'ola amigo' denir."),
                    Exercise("es_1_2", ExerciseType.MATCH_PAIRS, "Eşleştirin:", correctAnswer = "4_pairs", pairMap = mapOf("Hola" to "Merhaba", "Gracias" to "Teşekkürler", "Adiós" to "Hoşça kal", "Por favor" to "Lütfen")),
                    Exercise("es_1_3", ExerciseType.SENTENCE_ORDER, "'Merhaba, çok teşekkürler' de:", "Hola muchas gracias", "Hola muchas gracias", listOf("Hola", "muchas", "gracias", "de", "nada")),
                    Exercise("es_1_4", ExerciseType.LISTEN_TRANSLATE, "Dinle ve seç:", "Buenos días", "Buenos días", listOf("Buenos días", "Buenas noches", "Hasta luego", "Mucho gusto"))
                )
            ),
            Lesson(
                id = "es_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "Kendini tanıtma",
                title = "Benim Adım...", subtitle = "¿Cómo te llamas?", languageCode = "es", iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("es_1_5", ExerciseType.FILL_BLANK, "Me _____ Carlos. (Adım Carlos.)", "Me llamo Carlos", "llamo", listOf("llamo", "es", "soy", "tengo")),
                    Exercise("es_1_5speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Mucho gusto'", "Mucho gusto", "Mucho gusto", explanation = "Tanıştığıma memnun oldum dedin!"),
                    Exercise("es_1_6", ExerciseType.MULTIPLE_CHOICE, "¿Cómo estás? ne demektir?", "¿Cómo estás?", "Nasılsın?", listOf("Nasılsın?", "Nerelisin?", "Saat kaç?", "Kimsin?")),
                    Exercise("es_1_7", ExerciseType.MATCH_PAIRS, "İfadeleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("Mucho gusto" to "Memnun oldum", "¿Cómo estás?" to "Nasılsın?", "Muy bien" to "Çok iyi", "Yo también" to "Ben de"))
                )
            ),
            Lesson(
                id = "es_u1_l3", unitNumber = 1, unitTitle = u1, unitDescription = "Sayılar ve Renkler",
                title = "Sayılar (Uno, Dos, Tres)", subtitle = "1'den 10'a Sayılar", languageCode = "es", iconType = "book", xpReward = 25,
                exercises = listOf(
                    Exercise("es_1_8", ExerciseType.MATCH_PAIRS, "Sayıları eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("Uno" to "1 (Bir)", "Dos" to "2 (İki)", "Tres" to "3 (Üç)", "Cuatro" to "4 (Dört)")),
                    Exercise("es_1_8speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Uno dos tres'", "Uno dos tres", "Uno dos tres"),
                    Exercise("es_1_9", ExerciseType.FILL_BLANK, "Cinco, seis, _____ (5, 6, 7)", "siete", "siete", listOf("siete", "ocho", "nueve", "diez"))
                )
            ),
            Lesson(
                id = "es_u1_l4", unitNumber = 1, unitTitle = u1, unitDescription = "Ünite 1 Sandığı",
                title = "1. Bölüm Ustalık Sandığı", subtitle = "1. Bölüm Zafer Kupası", languageCode = "es", iconType = "crown", xpReward = 35, gemReward = 20, isMilestone = true,
                exercises = listOf(
                    Exercise("es_1_10", ExerciseType.SENTENCE_ORDER, "'Hola, mucho gusto amigo' kur:", "Hola mucho gusto amigo", "Hola mucho gusto amigo", listOf("Hola", "mucho", "gusto", "amigo", "bien")),
                    Exercise("es_1_11", ExerciseType.MATCH_PAIRS, "Hepsini eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("Buenos días" to "Günaydın", "Buenas tardes" to "Tünaydın", "Buenas noches" to "İyi geceler", "Hasta pronto" to "Yakında görüşürüz"))
                )
            ),

            // Unit 2: Café & Food
            Lesson(
                id = "es_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede sipariş",
                title = "Kahve ve Su", subtitle = "Un café por favor", languageCode = "es", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("es_2_1", ExerciseType.MULTIPLE_CHOICE, "'Agua' kelimesinin anlamı nedir?", "Agua", "Su", listOf("Su", "Kahve", "Süt", "Ekmek")),
                    Exercise("es_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Quiero un café por favor'", "Quiero un café por favor", "Quiero un café por favor"),
                    Exercise("es_2_2", ExerciseType.MATCH_PAIRS, "Yiyecekleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("El café" to "Kahve", "El agua" to "Su", "El pan" to "Ekmek", "El queso" to "Peynir")),
                    Exercise("es_2_3", ExerciseType.SENTENCE_ORDER, "'Lütfen bir su istiyorum' de:", "Quiero un agua por favor", "Quiero un agua por favor", listOf("Quiero", "un", "agua", "por", "favor", "café"))
                )
            ),
            Lesson(
                id = "es_u2_l2", unitNumber = 2, unitTitle = u2, unitDescription = "Restoran ve Lezzetler",
                title = "Hesap ve Tatlar", subtitle = "La cuenta por favor", languageCode = "es", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("es_2_4", ExerciseType.FILL_BLANK, "La comida está muy _____! (Yemek çok lezzetli!)", "La comida está muy deliciosa", "deliciosa", listOf("deliciosa", "agua", "gracias", "adiós")),
                    Exercise("es_2_5", ExerciseType.MATCH_PAIRS, "Restoran kelimelerini eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("La cuenta" to "Hesap", "El menú" to "Menü", "Delicioso" to "Lezzetli", "El té" to "Çay"))
                )
            ),
            Lesson(
                id = "es_u2_l3", unitNumber = 2, unitTitle = u2, unitDescription = "Ünite 2 Sandığı",
                title = "Kafe Ustası Sandığı", subtitle = "Lezzet Kupası", languageCode = "es", iconType = "crown", xpReward = 35, gemReward = 25, isMilestone = true,
                exercises = listOf(
                    Exercise("es_2_6", ExerciseType.SENTENCE_ORDER, "'La cuenta por favor, gracias' de:", "La cuenta por favor gracias", "La cuenta por favor gracias", listOf("La", "cuenta", "por", "favor", "gracias", "pan")),
                    Exercise("es_2_7", ExerciseType.MATCH_PAIRS, "Kafe kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("La mesa" to "Masa", "El camarero" to "Garson", "El jugo" to "Meyve suyu", "La taza" to "Fincan"))
                )
            ),

            // Unit 3: City & Travel
            Lesson(
                id = "es_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Otel ve Ulaşım",
                title = "Otel Nerede?", subtitle = "¿Dónde está el hotel?", languageCode = "es", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("es_3_1", ExerciseType.MULTIPLE_CHOICE, "'¿Dónde está...?' ne demektir?", "¿Dónde está?", "Nerede?", listOf("Nerede?", "Ne zaman?", "Nasıl?", "Kaç para?")),
                    Exercise("es_3_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: '¿Dónde está la estación?'", "¿Dónde está la estación?", "¿Dónde está la estación?"),
                    Exercise("es_3_2", ExerciseType.MATCH_PAIRS, "Seyahat kelimelerini eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("El hotel" to "Otel", "La estación" to "İstasyon", "El tren" to "Tren", "La calle" to "Cadde"))
                )
            ),
            Lesson(
                id = "es_u3_l2", unitNumber = 3, unitTitle = u3, unitDescription = "Havalimanı ve Bilet",
                title = "Havalimanı & Bilet", subtitle = "El aeropuerto", languageCode = "es", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("es_3_3", ExerciseType.SENTENCE_ORDER, "'Mi pasaporte y mi boleto' kur:", "Mi pasaporte y mi boleto", "Mi pasaporte y mi boleto", listOf("Mi", "pasaporte", "y", "mi", "boleto", "tren")),
                    Exercise("es_3_4", ExerciseType.MATCH_PAIRS, "Havalimanı kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("El pasaporte" to "Pasaport", "El boleto" to "Bilet", "El avión" to "Uçak", "El taxi" to "Taksi"))
                )
            ),

            // Unit 4: Family & Life
            Lesson(
                id = "es_u4_l1", unitNumber = 4, unitTitle = u4, unitDescription = "Aile bireyleri",
                title = "Ailem & Evim", subtitle = "Mi familia", languageCode = "es", iconType = "star", xpReward = 30,
                exercises = listOf(
                    Exercise("es_4_1", ExerciseType.MATCH_PAIRS, "Aile üyeleri:", correctAnswer = "4_pairs", pairMap = mapOf("La madre" to "Anne", "El padre" to "Baba", "El hermano" to "Erkek kardeş", "La hermana" to "Kız kardeş")),
                    Exercise("es_4_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Amo a mi familia'", "Amo a mi familia", "Amo a mi familia", explanation = "Ailemi seviyorum dedin!")
                )
            ),

            // Unit 5: Grand Championship
            Lesson(
                id = "es_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "İspanyolca Şampiyonluk Kupası", subtitle = "👑 Büyük Kupa Sandığı", languageCode = "es", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("es_5_1", ExerciseType.SENTENCE_ORDER, "'Hablo español con fluidez' de:", "Hablo español con fluidez", "Hablo español con fluidez", listOf("Hablo", "español", "con", "fluidez", "amigo")),
                    Exercise("es_5_2", ExerciseType.MATCH_PAIRS, "Şampiyonluk eşleştirmesi:", correctAnswer = "4_pairs", pairMap = mapOf("El futuro" to "Gelecek", "La victoria" to "Zafer", "El campeón" to "Şampiyon", "La estrella" to "Yıldız"))
                )
            )
        )
    }

    fun getEnglishLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: İngilis Dili Əsasları" else if (uiLang == "en") "Unit 1: English Basics" else "Ünite 1: İngilizce Temelleri"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Gündəlik Həyat" else if (uiLang == "en") "Unit 2: Café & Food" else "Ünite 2: Kafe & Lezzetler"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Səyahət" else if (uiLang == "en") "Unit 3: City & Travel" else "Ünite 3: Şehir ve Seyahat"
        val u4 = if (uiLang == "az") "Vahid 4: Ailə & Hobbilər" else if (uiLang == "en") "Unit 4: Family & Hobbies" else "Ünite 4: Aile & Hobiler"
        val u5 = if (uiLang == "az") "Vahid 5: Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: İngilizce Büyük Şampiyonluk"

        return listOf(
            Lesson(
                id = "en_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Temel selamlar",
                title = "Hello & Welcome", subtitle = "İlk Adımlar", languageCode = "en", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("en_1_1", ExerciseType.MULTIPLE_CHOICE, "'How are you?' ne demektir?", "How are you?", "Nasılsın?", listOf("Nasılsın?", "Kimsin?", "Nerelisin?", "Saat kaç?")),
                    Exercise("en_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Hello, nice to meet you!'", "Hello, nice to meet you!", "Hello, nice to meet you!"),
                    Exercise("en_1_2", ExerciseType.MATCH_PAIRS, "Kelimeleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("Good morning" to "Günaydın", "Thank you" to "Teşekkürler", "Please" to "Lütfen", "Goodbye" to "Hoşça kal")),
                    Exercise("en_1_3", ExerciseType.SENTENCE_ORDER, "'I am learning English' kur:", "I am learning English", "I am learning English", listOf("I", "am", "learning", "English", "you"))
                )
            ),
            Lesson(
                id = "en_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "Kendini tanıtma",
                title = "My Name is...", subtitle = "Tanışma", languageCode = "en", iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("en_1_4", ExerciseType.FILL_BLANK, "My name _____ Alex. (Benim adım Alex.)", "My name is Alex", "is", listOf("is", "am", "are", "be")),
                    Exercise("en_1_5", ExerciseType.MATCH_PAIRS, "Kelimeler:", correctAnswer = "4_pairs", pairMap = mapOf("Friend" to "Arkadaş", "Student" to "Öğrenci", "Welcome" to "Hoş geldiniz", "See you" to "Görüşürüz"))
                )
            ),
            Lesson(
                id = "en_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede sipariş",
                title = "Coffee & Bakery", subtitle = "Kafede", languageCode = "en", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("en_2_1", ExerciseType.MULTIPLE_CHOICE, "'Water' kelimesinin anlamı nedir?", "Water", "Su", listOf("Su", "Çay", "Kahve", "Meyve suyu")),
                    Exercise("en_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Could I have a coffee, please?'", "Could I have a coffee please", "Could I have a coffee please"),
                    Exercise("en_2_2", ExerciseType.MATCH_PAIRS, "Kafe kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Coffee" to "Kahve", "Tea" to "Çay", "Bread" to "Ekmek", "Bill" to "Hesap"))
                )
            ),
            Lesson(
                id = "en_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Havalimanı ve metro",
                title = "Airport & Travel", subtitle = "Seyahat", languageCode = "en", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("en_3_1", ExerciseType.SENTENCE_ORDER, "'Where is the train station?' kur:", "Where is the train station", "Where is the train station", listOf("Where", "is", "the", "train", "station", "hotel")),
                    Exercise("en_3_2", ExerciseType.MATCH_PAIRS, "Seyahat kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Airport" to "Havalimanı", "Ticket" to "Bilet", "Hotel" to "Otel", "Train" to "Tren"))
                )
            ),
            Lesson(
                id = "en_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "İngilizce Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "en", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("en_5_1", ExerciseType.SENTENCE_ORDER, "'I speak English fluently' de:", "I speak English fluently", "I speak English fluently", listOf("I", "speak", "English", "fluently", "and", "well")),
                    Exercise("en_5_2", ExerciseType.MATCH_PAIRS, "Ustalık eşleştirmesi:", correctAnswer = "4_pairs", pairMap = mapOf("Champion" to "Şampiyon", "Victory" to "Zafer", "Future" to "Gelecek", "Success" to "Başarı"))
                )
            )
        )
    }

    fun getGermanLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Alman Dili Əsasları" else if (uiLang == "en") "Unit 1: German Basics" else "Ünite 1: Almanca Temelleri"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Yemək" else if (uiLang == "en") "Unit 2: Café & Food" else "Ünite 2: Kafe & Lezzetler"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Səyahət" else if (uiLang == "en") "Unit 3: City & Travel" else "Ünite 3: Şehir ve Seyahat"
        val u5 = if (uiLang == "az") "Vahid 5: Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Almanca Büyük Şampiyonluk"

        return listOf(
            Lesson(
                id = "de_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Almanca tanışma",
                title = "Hallo & Guten Tag", subtitle = "Selamlar", languageCode = "de", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("de_1_1", ExerciseType.MULTIPLE_CHOICE, "'Guten Morgen' ne demektir?", "Guten Morgen", "Günaydın", listOf("Günaydın", "İyi geceler", "Merhaba", "Hoşça kal")),
                    Exercise("de_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Hallo, wie geht es dir?'", "Hallo wie geht es dir", "Hallo wie geht es dir"),
                    Exercise("de_1_2", ExerciseType.MATCH_PAIRS, "Almanca selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("Hallo" to "Merhaba", "Danke" to "Teşekkürler", "Bitte" to "Lütfen", "Tschüss" to "Hoşça kal"))
                )
            ),
            Lesson(
                id = "de_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede sipariş",
                title = "Kaffee & Brot", subtitle = "Kafede", languageCode = "de", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("de_2_1", ExerciseType.SENTENCE_ORDER, "'Einen Kaffee bitte' de:", "Einen Kaffee bitte", "Einen Kaffee bitte", listOf("Einen", "Kaffee", "bitte", "Wasser", "danke")),
                    Exercise("de_2_2", ExerciseType.MATCH_PAIRS, "Yiyecek ve içecekler:", correctAnswer = "4_pairs", pairMap = mapOf("Der Kaffee" to "Kahve", "Das Wasser" to "Su", "Das Brot" to "Ekmek", "Die Rechnung" to "Hesap"))
                )
            ),
            Lesson(
                id = "de_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Ulaşım ve Otel",
                title = "Bahnhof & Hotel", subtitle = "Seyahat", languageCode = "de", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("de_3_1", ExerciseType.SENTENCE_ORDER, "'Wo ist der Bahnhof bitte?' kur:", "Wo ist der Bahnhof bitte", "Wo ist der Bahnhof bitte", listOf("Wo", "ist", "der", "Bahnhof", "bitte")),
                    Exercise("de_3_2", ExerciseType.MATCH_PAIRS, "Şehir kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Der Bahnhof" to "Tren Garı", "Das Hotel" to "Otel", "Die Straße" to "Cadde", "Das Ticket" to "Bilet"))
                )
            ),
            Lesson(
                id = "de_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "Almanca Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "de", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("de_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Der Meister" to "Usta / Şampiyon", "Die Zukunft" to "Gelecek", "Der Erfolg" to "Başarı", "Der Stern" to "Yıldız"))
                )
            )
        )
    }

    fun getFrenchLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Fransız Dili Əsasları" else if (uiLang == "en") "Unit 1: French Basics" else "Ünite 1: Fransızca Temelleri"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Kruvasan" else if (uiLang == "en") "Unit 2: Café & Croissant" else "Ünite 2: Kafe & Kruvasan"
        val u5 = if (uiLang == "az") "Vahid 5: Paris & Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Paris & Büyük Şampiyonluk"

        return listOf(
            Lesson(
                id = "fr_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Fransızca tanışma",
                title = "Bonjour & Bienvenue", subtitle = "Merhaba", languageCode = "fr", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("fr_1_1", ExerciseType.MULTIPLE_CHOICE, "'Bonjour' ne demektir?", "Bonjour", "İyi günler / Merhaba", listOf("İyi günler / Merhaba", "Hoşça kal", "Teşekkürler", "Lütfen")),
                    Exercise("fr_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Merci beaucoup!'", "Merci beaucoup", "Merci beaucoup"),
                    Exercise("fr_1_2", ExerciseType.MATCH_PAIRS, "Fransızca selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("Bonjour" to "Merhaba", "Merci" to "Teşekkürler", "S'il vous plaît" to "Lütfen", "Au revoir" to "Hoşça kal"))
                )
            ),
            Lesson(
                id = "fr_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede sipariş",
                title = "Café & Croissant", subtitle = "Sipariş", languageCode = "fr", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("fr_2_1", ExerciseType.SENTENCE_ORDER, "'Je voudrais un croissant s'il vous plaît' de:", "Je voudrais un croissant sil vous plait", "Je voudrais un croissant sil vous plait", listOf("Je", "voudrais", "un", "croissant", "sil", "vous", "plait")),
                    Exercise("fr_2_2", ExerciseType.MATCH_PAIRS, "Lezzetler:", correctAnswer = "4_pairs", pairMap = mapOf("Le café" to "Kahve", "L'eau" to "Su", "Le pain" to "Ekmek", "L'addition" to "Hesap"))
                )
            ),
            Lesson(
                id = "fr_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Paris ve Şampiyonluk",
                title = "Fransızca Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "fr", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("fr_5_1", ExerciseType.MATCH_PAIRS, "Ustalık eşleştirmesi:", correctAnswer = "4_pairs", pairMap = mapOf("La victoire" to "Zafer", "Le champion" to "Şampiyon", "L'étoile" to "Yıldız", "L'avenir" to "Gelecek"))
                )
            )
        )
    }

    fun getItalianLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: İtalyan Dili Əsasları" else if (uiLang == "en") "Unit 1: Italian Basics" else "Ünite 1: İtalyanca Temelleri"
        val u2 = if (uiLang == "az") "Vahid 2: Bar & Pizza" else if (uiLang == "en") "Unit 2: Bar & Pizza" else "Ünite 2: Bar & Pizza"
        val u5 = if (uiLang == "az") "Vahid 5: Roma & Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: İtalyanca Büyük Şampiyonluk"

        return listOf(
            Lesson(
                id = "it_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "İtalyanca başlangıç",
                title = "Ciao & Buongiorno", subtitle = "Merhaba & Günaydın", languageCode = "it", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("it_1_1", ExerciseType.MULTIPLE_CHOICE, "'Grazie' ne demektir?", "Grazie", "Teşekkürler", listOf("Teşekkürler", "Lütfen", "Merhaba", "Görüşürüz")),
                    Exercise("it_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Ciao, come stai?'", "Ciao come stai", "Ciao come stai"),
                    Exercise("it_1_2", ExerciseType.MATCH_PAIRS, "İtalyanca selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("Ciao" to "Merhaba", "Grazie" to "Teşekkürler", "Per favore" to "Lütfen", "Arrivederci" to "Görüşmek üzere"))
                )
            ),
            Lesson(
                id = "it_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Espresso ve Pizza",
                title = "Un Espresso per favore", subtitle = "İtalyan Mutfağı", languageCode = "it", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("it_2_1", ExerciseType.SENTENCE_ORDER, "'Un espresso per favore' de:", "Un espresso per favore", "Un espresso per favore", listOf("Un", "espresso", "per", "favore", "grazie")),
                    Exercise("it_2_2", ExerciseType.MATCH_PAIRS, "Yemek kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Il caffè" to "Kahve", "L'acqua" to "Su", "La pizza" to "Pizza", "Il conto" to "Hesap"))
                )
            ),
            Lesson(
                id = "it_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Roma ve Şampiyonluk",
                title = "İtalyanca Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "it", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("it_5_1", ExerciseType.MATCH_PAIRS, "Ustalık eşleştirmesi:", correctAnswer = "4_pairs", pairMap = mapOf("Il campione" to "Şampiyon", "La vittoria" to "Zafer", "La stella" to "Yıldız", "Il futuro" to "Gelecek"))
                )
            )
        )
    }

    fun getPortugueseLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Portuqal Dili Əsasları" else if (uiLang == "en") "Unit 1: Portuguese Basics" else "Ünite 1: Portekizce Temelleri"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Səyahət" else if (uiLang == "en") "Unit 2: Café & Travel" else "Ünite 2: Kafe ve Seyahat"
        val u5 = if (uiLang == "az") "Vahid 5: Böyük Çempionluq" else if (uiLang == "en") "Unit 5: Grand Championship" else "Ünite 5: Portekizce Büyük Şampiyonluk"

        return listOf(
            Lesson(
                id = "pt_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Portekizce selamlar",
                title = "Olá & Bom dia", subtitle = "Merhaba & Günaydın", languageCode = "pt", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("pt_1_1", ExerciseType.MULTIPLE_CHOICE, "'Muito obrigado' ne demektir?", "Muito obrigado", "Çok teşekkürler", listOf("Çok teşekkürler", "Rica ederim", "Günaydın", "Hoşça kal")),
                    Exercise("pt_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Doğal sesle söyle: 'Olá, tudo bem?'", "Olá tudo bem", "Olá tudo bem"),
                    Exercise("pt_1_2", ExerciseType.MATCH_PAIRS, "Portekizce selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("Olá" to "Merhaba", "Obrigado" to "Teşekkürler", "Por favor" to "Lütfen", "Tchau" to "Hoşça kal"))
                )
            ),
            Lesson(
                id = "pt_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafe ve Lezzetler",
                title = "Um café por favor", subtitle = "Kafe", languageCode = "pt", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("pt_2_1", ExerciseType.SENTENCE_ORDER, "'Um café por favor' kur:", "Um café por favor", "Um café por favor", listOf("Um", "café", "por", "favor", "água")),
                    Exercise("pt_2_2", ExerciseType.MATCH_PAIRS, "Kelimeleri eşleştir:", correctAnswer = "4_pairs", pairMap = mapOf("O café" to "Kahve", "A água" to "Su", "O pão" to "Ekmek", "A conta" to "Hesap"))
                )
            ),
            Lesson(
                id = "pt_u5_l1", unitNumber = 5, unitTitle = u5, unitDescription = "Büyük Şampiyonluk",
                title = "Portekizce Şampiyonluk Kupası", subtitle = "👑 Grand Master Cup", languageCode = "pt", iconType = "crown", xpReward = 50, gemReward = 50, isMilestone = true,
                exercises = listOf(
                    Exercise("pt_5_1", ExerciseType.MATCH_PAIRS, "Şampiyonluk kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("O campeão" to "Şampiyon", "A vitória" to "Zafer", "A estrela" to "Yıldız", "O futuro" to "Gelecek"))
                )
            )
        )
    }
}
