package com.example.data.repository

import com.example.data.model.Exercise
import com.example.data.model.ExerciseType
import com.example.data.model.Language
import com.example.data.model.Lesson

object CurriculumTurkicSlavic {

    fun getTurkishLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Türk Dili Əsasları" else if (uiLang == "en") "Unit 1: Turkish Basics" else "Ünite 1: Türkçe Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Çay, Simit & Kafe" else if (uiLang == "en") "Unit 2: Tea & Turkish Café" else "Ünite 2: Çay, Simit & Türk Mutfağı"
        val u3 = if (uiLang == "az") "Vahid 3: İstanbul & Səyahət" else if (uiLang == "en") "Unit 3: Istanbul & Travel" else "Ünite 3: İstanbul & Gezi"
        val u4 = if (uiLang == "az") "Vahid 4: Robot Byte & Süni İntellekt" else if (uiLang == "en") "Unit 4: Robot Byte & AI" else "Ünite 4: Akıllı Robot Byte & Yapay Zeka"

        return listOf(
            Lesson(
                id = "tr_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Temel selamlaşma ve tanışma",
                title = "Merhaba & Günaydın", subtitle = "İlk Adımlar", languageCode = "tr", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("tr_1_1", ExerciseType.MULTIPLE_CHOICE, "Sabahları nasıl selam verilir?", "Günaydın", "Günaydın", listOf("Günaydın", "İyi geceler", "Tünaydın", "Hoşça kal"), explanation = "Sabah saatlerinde 'Günaydın' kullanılır."),
                    Exercise("tr_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Merhaba, nasılsınız?'", "Merhaba nasılsınız", "Merhaba nasılsınız", explanation = "Harika akıcı bir Türkçe telaffuz!"),
                    Exercise("tr_1_2", ExerciseType.MATCH_PAIRS, "Türkçe nezaket ifadeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Merhaba" to "Hello / Salam", "Teşekkür ederim" to "Thank you / Sağ olun", "Lütfen" to "Please / Zəhmət olmasa", "Görüşürüz" to "See you / Görüşərik")),
                    Exercise("tr_1_3", ExerciseType.SENTENCE_ORDER, "'Ben Türkçe öğreniyorum' cümlesini kur:", "Ben Türkçe öğreniyorum", "Ben Türkçe öğreniyorum", listOf("Ben", "Türkçe", "öğreniyorum", "sen", "robot"))
                )
            ),
            Lesson(
                id = "tr_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "Kendini tanıtma",
                title = "Benim Adım...", subtitle = "Memnun Oldum", languageCode = "tr", iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("tr_1_4", ExerciseType.FILL_BLANK, "Benim _____ Deniz. Tanıştığıma memnun oldum.", "Benim adım Deniz", "adım", listOf("adım", "evim", "arabam", "okulum")),
                    Exercise("tr_1_5", ExerciseType.LISTEN_TRANSLATE, "Robotu dinle ve seç:", "Tanıştığımıza çok sevindim", "Tanıştığımıza çok sevindim", listOf("Tanıştığımıza çok sevindim", "İyi akşamlar dilerim", "Hoşça kalın efendim", "Güle güle gidin")),
                    Exercise("tr_1_6", ExerciseType.MATCH_PAIRS, "Tanışma kalıpları:", correctAnswer = "4_pairs", pairMap = mapOf("Adınız ne?" to "What is your name?", "Memnun oldum" to "Nice to meet you", "Nasılsınız?" to "How are you?", "İyiyim" to "I am good"))
                )
            ),
            Lesson(
                id = "tr_u1_l3", unitNumber = 1, unitTitle = u1, unitDescription = "Ünite 1 Sonu",
                title = "Türkçe Başlangıç Sandığı", subtitle = "1. Bölüm Kupası", languageCode = "tr", iconType = "crown", xpReward = 35, gemReward = 20, isMilestone = true,
                exercises = listOf(
                    Exercise("tr_1_7", ExerciseType.SENTENCE_ORDER, "'Merhaba arkadaşım, hoş geldin' de:", "Merhaba arkadaşım hoş geldin", "Merhaba arkadaşım hoş geldin", listOf("Merhaba", "arkadaşım", "hoş", "geldin", "ben", "sen")),
                    Exercise("tr_1_8", ExerciseType.MATCH_PAIRS, "Selamlaşma sözcükleri:", correctAnswer = "4_pairs", pairMap = mapOf("Hoş geldiniz" to "Welcome", "Hoş bulduk" to "Glad to be here", "Afiyet olsun" to "Enjoy your meal", "Kolay gelsin" to "May it be easy"))
                )
            ),
            Lesson(
                id = "tr_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Çay ve simit siparişi",
                title = "Bir Bardak Çay Lütfen", subtitle = "Türk Çayı & Kafe", languageCode = "tr", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("tr_2_1", ExerciseType.MULTIPLE_CHOICE, "'Su' kelimesinin anlamı nedir?", "Su", "Water / Su", listOf("Water / Su", "Tea / Çay", "Coffee / Qəhvə", "Bread / Çörək")),
                    Exercise("tr_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Bir çay ve taze simit lütfen'", "Bir çay ve taze simit lütfen", "Bir çay ve taze simit lütfen", explanation = "Mükemmel bir Türk kahvaltısı siparişi!"),
                    Exercise("tr_2_2", ExerciseType.MATCH_PAIRS, "Kafe ve lezzetler:", correctAnswer = "4_pairs", pairMap = mapOf("Çay" to "Tea", "Kahve" to "Coffee", "Ekmek" to "Bread", "Hesap" to "Bill / Check")),
                    Exercise("tr_2_3", ExerciseType.SENTENCE_ORDER, "'Hesabı alabilir miyim lütfen?' de:", "Hesabı alabilir miyim lütfen", "Hesabı alabilir miyim lütfen", listOf("Hesabı", "alabilir", "miyim", "lütfen", "çay", "su"))
                )
            ),
            Lesson(
                id = "tr_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "İstanbul ve Ulaşım",
                title = "Vapur & Metro", subtitle = "Şehir Ulaşımı", languageCode = "tr", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("tr_3_1", ExerciseType.MULTIPLE_CHOICE, "'Metro istasyonu nerede?' ne sorar?", "Metro istasyonu nerede?", "Metro nerede?", listOf("Metro nerede?", "Bilet ne kadar?", "Otel açık mı?", "Saat kaç?")),
                    Exercise("tr_3_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Kadıköy vapuruna nasıl giderim?'", "Kadıköy vapuruna nasıl giderim", "Kadıköy vapuruna nasıl giderim", explanation = "Harika bir İstanbul sorusu!"),
                    Exercise("tr_3_2", ExerciseType.MATCH_PAIRS, "Ulaşım kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Vapur" to "Ferry", "Bilet" to "Ticket", "İstasyon" to "Station", "Otel" to "Hotel"))
                )
            ),
            Lesson(
                id = "tr_u4_l1", unitNumber = 4, unitTitle = u4, unitDescription = "Robot Byte & Yapay Zeka",
                title = "Mor Robot Byte ile Sohbet", subtitle = "Ustalık Sandığı", languageCode = "tr", iconType = "chip", xpReward = 35, gemReward = 30, isMilestone = true,
                exercises = listOf(
                    Exercise("tr_4_1", ExerciseType.FILL_BLANK, "Byte çok akıllı ve cana yakın bir _____ robottur.", "Byte çok akıllı ve cana yakın bir mor robottur", "mor", listOf("mor", "kırmızı", "yeşil", "mavi")),
                    Exercise("tr_4_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Yapay zeka ile dil öğrenmek çok eğlenceli'", "Yapay zeka ile dil öğrenmek çok eğlenceli", "Yapay zeka ile dil öğrenmek çok eğlenceli", explanation = "Tebrikler! Türkçe ustalığına ulaştın!"),
                    Exercise("tr_4_2", ExerciseType.MATCH_PAIRS, "Teknoloji terimleri:", correctAnswer = "4_pairs", pairMap = mapOf("Robot" to "Robot", "Gelecek" to "Future", "Öğrenmek" to "To learn", "Yıldız" to "Star"))
                )
            )
        )
    }

    fun getAzerbaijaniLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "en") "Unit 1: Azerbaijani Basics" else if (uiLang == "tr") "Ünite 1: Azerbaycanca Temelleri" else "Vahid 1: Azərbaycan Dili Əsasları & Salamlar"
        val u2 = if (uiLang == "en") "Unit 2: Tea House & Tasty Food" else if (uiLang == "tr") "Ünite 2: Çayxana ve Lezzetler" else "Vahid 2: Çayxana & Dadlı Qidalar"
        val u3 = if (uiLang == "en") "Unit 3: Baku & Travel" else if (uiLang == "tr") "Ünite 3: Bakü ve Gezi" else "Vahid 3: Bakı Bulvarı və Səyahət"
        val u4 = if (uiLang == "en") "Unit 4: Robot Byte & Mastery" else if (uiLang == "tr") "Ünite 4: Robot Byte ve Ustalık" else "Vahid 4: Robot Byte & Ustalığ Sandığı"

        return listOf(
            Lesson(
                id = "az_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "İlk addımlar və salamlaşma",
                title = "Salam & Xoş Gəlmisiniz", subtitle = "İlk Addımlar", languageCode = "az", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("az_1_1", ExerciseType.MULTIPLE_CHOICE, "Azərbaycan dilində 'Salam' nə deməkdir?", "Salam", "Hello / Merhaba", listOf("Hello / Merhaba", "Goodbye", "Thank you", "Please"), explanation = "'Salam' ən geniş yayılmış salamlaşmadır."),
                    Exercise("az_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona de: 'Salam, necəsən dostum?'", "Salam necəsən dostum", "Salam necəsən dostum", explanation = "Əla və təmiz Azərbaycan tələffüzü!"),
                    Exercise("az_1_2", ExerciseType.MATCH_PAIRS, "Nəzakət sözlərini uyğunlaşdırın:", correctAnswer = "4_pairs", pairMap = mapOf("Salam" to "Hello", "Çox sağ olun" to "Thank you", "Zəhmət olmasa" to "Please", "Hələlik" to "Goodbye")),
                    Exercise("az_1_3", ExerciseType.SENTENCE_ORDER, "'Mən Azərbaycan dilini öyrənirəm' cümləsini qur:", "Mən Azərbaycan dilini öyrənirəm", "Mən Azərbaycan dilini öyrənirəm", listOf("Mən", "Azərbaycan", "dilini", "öyrənirəm", "sən", "robot"))
                )
            ),
            Lesson(
                id = "az_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "Özünü təqdim etmə",
                title = "Mənim Adım...", subtitle = "Şad Oldum", languageCode = "az", iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("az_1_4", ExerciseType.FILL_BLANK, "Mənim _____ Əlidir. Sizinlə tanış olmağıma şad oldum.", "Mənim adım Əlidir", "adım", listOf("adım", "evim", "kitabım", "qələmim")),
                    Exercise("az_1_5", ExerciseType.LISTEN_TRANSLATE, "Robotu dinlə və seç:", "Sabahınız xeyir olsun", "Sabahınız xeyir olsun", listOf("Sabahınız xeyir olsun", "Gecəniz xeyirə qalsın", "Xoş gördük dostum", "Görüşənədək")),
                    Exercise("az_1_6", ExerciseType.MATCH_PAIRS, "Tanışlıq ifadələri:", correctAnswer = "4_pairs", pairMap = mapOf("Adınız nədir?" to "What is your name?", "Şad oldum" to "Nice to meet you", "Necəsiniz?" to "How are you?", "Yaxşıyam" to "I am fine"))
                )
            ),
            Lesson(
                id = "az_u1_l3", unitNumber = 1, unitTitle = u1, unitDescription = "1. Vahid Sonu",
                title = "Azərbaycan Dili Sandığı", subtitle = "1. Bölmə Kuboku", languageCode = "az", iconType = "crown", xpReward = 35, gemReward = 20, isMilestone = true,
                exercises = listOf(
                    Exercise("az_1_7", ExerciseType.SENTENCE_ORDER, "'Salam dostum, xoş gəlmisən' de:", "Salam dostum xoş gəlmisən", "Salam dostum xoş gəlmisən", listOf("Salam", "dostum", "xoş", "gəlmisən", "mən", "sən")),
                    Exercise("az_1_8", ExerciseType.MATCH_PAIRS, "Sözləri uyğunlaşdır:", correctAnswer = "4_pairs", pairMap = mapOf("Xoş gəlmisiniz" to "Welcome", "Nuş olsun" to "Bon appetit", "Bağışlayın" to "Excuse me", "Dəyməz" to "You are welcome"))
                )
            ),
            Lesson(
                id = "az_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Çayxana və dadlı şirniyyatlar",
                title = "Bir Armudu Stəkan Çay", subtitle = "Çayxana Söhbəti", languageCode = "az", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("az_2_1", ExerciseType.MULTIPLE_CHOICE, "'Su' sözünün mənası nədir?", "Su", "Water / Su", listOf("Water / Su", "Tea / Çay", "Coffee / Qəhvə", "Bread / Çörək")),
                    Exercise("az_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona de: 'Zəhmət olmasa bir çay və paxlava gətirin'", "Zəhmət olmasa bir çay və paxlava gətirin", "Zəhmət olmasa bir çay və paxlava gətirin", explanation = "Ləzzətli Azərbaycan çay sifarişi!"),
                    Exercise("az_2_2", ExerciseType.MATCH_PAIRS, "Qidalar və çayxana:", correctAnswer = "4_pairs", pairMap = mapOf("Çay" to "Tea", "Qəhvə" to "Coffee", "Çörək" to "Bread", "Hesab" to "Bill")),
                    Exercise("az_2_3", ExerciseType.SENTENCE_ORDER, "'Hesabı zəhmət olmasa gətirin' de:", "Hesabı zəhmət olmasa gətirin", "Hesabı zəhmət olmasa gətirin", listOf("Hesabı", "zəhmət", "olmasa", "gətirin", "çay", "su"))
                )
            ),
            Lesson(
                id = "az_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Bakı və Nəqliyyat",
                title = "Bakı Bulvarı & Metro", subtitle = "Şəhər Səyahəti", languageCode = "az", iconType = "airplane", xpReward = 30,
                exercises = listOf(
                    Exercise("az_3_1", ExerciseType.MULTIPLE_CHOICE, "'Metro stansiyası haradadır?' nə soruşur?", "Metro stansiyası haradadır?", "Metro haradadır?", listOf("Metro haradadır?", "Bilet nə qədərdir?", "Otel açıqdır?", "Saat neçədir?")),
                    Exercise("az_3_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona de: 'Qız Qalasına necə gedə bilərəm?'", "Qız Qalasına necə gedə bilərəm", "Qız Qalasına necə gedə bilərəm", explanation = "Əla Bakı səyahət sualı!"),
                    Exercise("az_3_2", ExerciseType.MATCH_PAIRS, "Nəqliyyat və şəhər:", correctAnswer = "4_pairs", pairMap = mapOf("Qatar" to "Train", "Bilet" to "Ticket", "Stansiya" to "Station", "Otel" to "Hotel"))
                )
            ),
            Lesson(
                id = "az_u4_l1", unitNumber = 4, unitTitle = u4, unitDescription = "Bənövşəyi Robot Byte",
                title = "Robot Byte ilə Ustalığ Sandığı", subtitle = "Qələbə Kuboku", languageCode = "az", iconType = "chip", xpReward = 35, gemReward = 30, isMilestone = true,
                exercises = listOf(
                    Exercise("az_4_1", ExerciseType.FILL_BLANK, "Byte çox ağıllı və mehriban _____ robotdur.", "Byte çox ağıllı və mehriban bənövşəyi robotdur", "bənövşəyi", listOf("bənövşəyi", "qırmızı", "yaşıl", "sarı")),
                    Exercise("az_4_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona de: 'Mən yeni dilləri sevirəm'", "Mən yeni dilləri sevirəm", "Mən yeni dilləri sevirəm", explanation = "Təbriklər! Azərbaycan dilində ustalığa çatdın!"),
                    Exercise("az_4_2", ExerciseType.MATCH_PAIRS, "Texnologiya sözləri:", correctAnswer = "4_pairs", pairMap = mapOf("Robot" to "Robot", "Gələcək" to "Future", "Öyrənmək" to "To learn", "Ulduz" to "Star"))
                )
            )
        )
    }

    fun getRussianLessons(lang: Language, uiLang: String): List<Lesson> {
        val u1 = if (uiLang == "az") "Vahid 1: Rus Dili Əsasları" else if (uiLang == "en") "Unit 1: Russian Basics" else "Ünite 1: Rusça Temelleri & Selamlar"
        val u2 = if (uiLang == "az") "Vahid 2: Kafe & Yemək" else if (uiLang == "en") "Unit 2: Café & Food" else "Ünite 2: Kafe & Yiyecekler"
        val u3 = if (uiLang == "az") "Vahid 3: Şəhər & Ustalığ Sandığı" else if (uiLang == "en") "Unit 3: City & Mastery Chest" else "Ünite 3: Şehir & Ustalık Sandığı"

        return listOf(
            Lesson(
                id = "ru_u1_l1", unitNumber = 1, unitTitle = u1, unitDescription = "Rusça tanışma ve selamlar",
                title = "Привет & Здравствуйте", subtitle = "Selamlaşma", languageCode = "ru", iconType = "speech", xpReward = 20,
                exercises = listOf(
                    Exercise("ru_1_1", ExerciseType.MULTIPLE_CHOICE, "Rusça 'Спасибо' ne demektir?", "Спасибо", "Teşekkür ederim", listOf("Teşekkür ederim", "Lütfen", "Merhaba", "Hoşça kal")),
                    Exercise("ru_1_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Привет, как дела?'", "Привет как дела", "Привет как дела", explanation = "Nasılsın diye sordun!"),
                    Exercise("ru_1_2", ExerciseType.MATCH_PAIRS, "Rusça selamlar:", correctAnswer = "4_pairs", pairMap = mapOf("Привет" to "Merhaba", "Спасибо" to "Teşekkürler", "Пожалуйста" to "Lütfen / Rica ederim", "Пока" to "Hoşça kal")),
                    Exercise("ru_1_3", ExerciseType.SENTENCE_ORDER, "'Я учу русский язык' cümlesini kur:", "Я учу русский язык", "Я учу русский язык", listOf("Я", "учу", "русский", "язык", "ты", "робот"))
                )
            ),
            Lesson(
                id = "ru_u1_l2", unitNumber = 1, unitTitle = u1, unitDescription = "Kendini tanıtma",
                title = "Меня зовут...", subtitle = "Benim Adım", languageCode = "ru", iconType = "star", xpReward = 25,
                exercises = listOf(
                    Exercise("ru_1_4", ExerciseType.FILL_BLANK, "Меня _____ Иван. Очень приятно.", "Меня зовут Иван", "зовут", listOf("зовут", "есть", "был", "дом")),
                    Exercise("ru_1_5", ExerciseType.MATCH_PAIRS, "Tanışma kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Очень приятно" to "Memnun oldum", "Доброе утро" to "Günaydın", "Добрый вечер" to "İyi akşamlar", "Друг" to "Arkadaş"))
                )
            ),
            Lesson(
                id = "ru_u2_l1", unitNumber = 2, unitTitle = u2, unitDescription = "Kafede sipariş",
                title = "Кофе и Чай", subtitle = "Kafede", languageCode = "ru", iconType = "coffee", xpReward = 25,
                exercises = listOf(
                    Exercise("ru_2_1", ExerciseType.SENTENCE_ORDER, "'Один кофе, пожалуйста' de:", "Один кофе пожалуйста", "Один кофе пожалуйста", listOf("Один", "кофе", "пожалуйста", "чай", "вода")),
                    Exercise("ru_2_speak", ExerciseType.SPEAK_PRONOUNCE, "Mikrofona söyle: 'Счёт, пожалуйста'", "Счёт пожалуйста", "Счёт пожалуйста", explanation = "Hesap lütfen dedin!"),
                    Exercise("ru_2_2", ExerciseType.MATCH_PAIRS, "Yiyecek ve içecekler:", correctAnswer = "4_pairs", pairMap = mapOf("Кофе" to "Kahve", "Чай" to "Çay", "Вода" to "Su", "Хлеб" to "Ekmek"))
                )
            ),
            Lesson(
                id = "ru_u3_l1", unitNumber = 3, unitTitle = u3, unitDescription = "Şehir ve Ustalık",
                title = "Rusça Ustalık Sandığı", subtitle = "Bölüm Kupası", languageCode = "ru", iconType = "crown", xpReward = 35, gemReward = 25, isMilestone = true,
                exercises = listOf(
                    Exercise("ru_3_1", ExerciseType.SENTENCE_ORDER, "'Где находится метро?' (Metro nerede?) cümlesini kur:", "Где находится метро", "Где находится метро", listOf("Где", "находится", "метро", "отель", "спасибо")),
                    Exercise("ru_3_2", ExerciseType.MATCH_PAIRS, "Şehir kelimeleri:", correctAnswer = "4_pairs", pairMap = mapOf("Метро" to "Metro", "Отель" to "Otel", "Вокзал" to "Tren Garı", "Робот" to "Robot"))
                )
            )
        )
    }
}
