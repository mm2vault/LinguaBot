package com.example.data.model

data class Language(
    val code: String,
    val nameEn: String,
    val nameTr: String,
    val nameAz: String,
    val nativeName: String,
    val greeting: String,
    val difficulty: Int, // 1 to 3
    val category: String,
    val speakers: String,
    val badgeColorHex: Long = 0xFF7C3AED,
    val flagEmoji: String = getFlagForCode(code)
) {
    fun getDisplayName(appUiLang: String): String {
        return when (appUiLang) {
            "tr" -> nameTr
            "az" -> nameAz
            else -> nameEn
        }
    }

    companion object {
        fun getFlagForCode(code: String): String {
            return when (code.lowercase().trim()) {
                "es" -> "🇪🇸"
                "en", "gb", "uk" -> "🇬🇧"
                "tr" -> "🇹🇷"
                "az" -> "🇦🇿"
                "de" -> "🇩🇪"
                "fr" -> "🇫🇷"
                "it" -> "🇮🇹"
                "ja" -> "🇯🇵"
                "ko" -> "🇰🇷"
                "zh" -> "🇨🇳"
                "ru" -> "🇷🇺"
                "ar" -> "🇸🇦"
                "pt" -> "🇵🇹"
                "nl" -> "🇳🇱"
                "sv" -> "🇸🇪"
                "no" -> "🇳🇴"
                "da" -> "🇩🇰"
                "fi" -> "🇫🇮"
                "pl" -> "🇵🇱"
                "el" -> "🇬🇷"
                "hi" -> "🇮🇳"
                "vi" -> "🇻🇳"
                "id" -> "🇮🇩"
                "cs" -> "🇨🇿"
                "uk" -> "🇺🇦"
                "ro" -> "🇷🇴"
                "hu" -> "🇭🇺"
                "he" -> "🇮🇱"
                "th" -> "🇹🇭"
                "fa" -> "🇮🇷"
                "bn" -> "🇧🇩"
                "tl" -> "🇵🇭"
                "sw" -> "🇰🇪"
                "ga" -> "🇮🇪"
                "is" -> "🇮🇸"
                "bg" -> "🇧🇬"
                "hr" -> "🇭🇷"
                "sr" -> "🇷🇸"
                "sk" -> "🇸🇰"
                "sl" -> "🇸🇮"
                "lt" -> "🇱🇹"
                "lv" -> "🇱🇻"
                "et" -> "🇪🇪"
                "ca" -> "🇪🇸"
                "gl" -> "🇪🇸"
                "eu" -> "🇪🇸"
                "ms" -> "🇲🇾"
                "ur" -> "🇵🇰"
                "ta" -> "🇮🇳"
                "te" -> "🇮🇳"
                "mr" -> "🇮🇳"
                "gu" -> "🇮🇳"
                "kn" -> "🇮🇳"
                "ml" -> "🇮🇳"
                "pa" -> "🇮🇳"
                "ne" -> "🇳🇵"
                "si" -> "🇱🇰"
                "my" -> "🇲🇲"
                "km" -> "🇰🇭"
                "lo" -> "🇱🇦"
                "ka" -> "🇬🇪"
                "hy" -> "🇦🇲"
                "kk" -> "🇰🇿"
                "uz" -> "🇺🇿"
                "ky" -> "🇰🇬"
                "tk" -> "🇹🇲"
                "tt" -> "🇷🇺"
                "mn" -> "🇲🇳"
                "am" -> "🇪🇹"
                "yo" -> "🇳🇬"
                "ig" -> "🇳🇬"
                "ha" -> "🇳🇬"
                "zu" -> "🇿🇦"
                "xh" -> "🇿🇦"
                "af" -> "🇿🇦"
                "so" -> "🇸🇴"
                "mg" -> "🇲🇬"
                "rw" -> "🇷🇼"
                "sq" -> "🇦🇱"
                "mk" -> "🇲🇰"
                "bs" -> "🇧🇦"
                "mt" -> "🇲🇹"
                "cy" -> "🏴󠁧󠁢󠁷󠁬󠁳󠁿"
                "gd" -> "🏴󠁧󠁢󠁳󠁣󠁴󠁿"
                "lb" -> "🇱🇺"
                "eo" -> "🌍"
                "la" -> "🏛️"
                "sa" -> "🕉️"
                "be" -> "🇧🇾"
                "ps" -> "🇦🇫"
                "ku" -> "☀️"
                "tg" -> "🇹🇯"
                "ug" -> "☪️"
                "ba" -> "🇷🇺"
                "cv" -> "🇷🇺"
                "ceb" -> "🇵🇭"
                "jw" -> "🇮🇩"
                "su" -> "🇮🇩"
                "haw" -> "🌺"
                "mi" -> "🇳🇿"
                "sm" -> "🇼🇸"
                "to" -> "🇹🇴"
                "fj" -> "🇫🇯"
                "kl" -> "🇬🇱"
                else -> "🌐"
            }
        }
    }
}

object LanguageCatalog {
    val languages: List<Language> = listOf(
        // Popular / Core
        Language("es", "Spanish", "İspanyolca", "İspan dili", "Español", "¡Hola!", 1, "Popular", "550M", 0xFFE11D48, "🇪🇸"),
        Language("en", "English", "İngilizce", "İngilis dili", "English", "Hello!", 1, "Popular", "1.5B", 0xFF2563EB, "🇬🇧"),
        Language("tr", "Turkish", "Türkçe", "Türk dili", "Türkçe", "Merhaba!", 1, "Popular", "85M", 0xFFDC2626, "🇹🇷"),
        Language("az", "Azerbaijani", "Azerbaycanca", "Azərbaycan dili", "Azərbaycan", "Salam!", 1, "Popular", "35M", 0xFF0284C7, "🇦🇿"),
        Language("de", "German", "Almanca", "Alman dili", "Deutsch", "Hallo!", 2, "Popular", "130M", 0xFFD97706, "🇩🇪"),
        Language("fr", "French", "Fransızca", "Fransız dili", "Français", "Bonjour!", 2, "Popular", "300M", 0xFF4F46E5, "🇫🇷"),
        Language("it", "Italian", "İtalyanca", "İtalyan dili", "Italiano", "Ciao!", 1, "Popular", "68M", 0xFF059669, "🇮🇹"),
        Language("ja", "Japanese", "Japonca", "Yapon dili", "日本語", "こんにちは", 3, "Popular", "125M", 0xFFBE123C, "🇯🇵"),
        Language("ko", "Korean", "Korece", "Koreya dili", "한국어", "안녕하세요", 3, "Popular", "80M", 0xFF4338CA, "🇰🇷"),
        Language("zh", "Chinese", "Çince", "Çin dili", "中文", "你好", 3, "Popular", "1.3B", 0xFFB91C1C, "🇨🇳"),
        Language("ru", "Russian", "Rusça", "Rus dili", "Русский", "Привет!", 2, "Popular", "260M", 0xFF1D4ED8, "🇷🇺"),
        Language("ar", "Arabic", "Arapça", "Ərəb dili", "العربية", "مرحباً", 3, "Popular", "400M", 0xFF047857, "🇸🇦"),
        Language("pt", "Portuguese", "Portekizce", "Portuqal dili", "Português", "Olá!", 1, "Popular", "260M", 0xFF15803D, "🇵🇹"),
        Language("nl", "Dutch", "Felemenkçe", "Holland dili", "Nederlands", "Hallo!", 1, "European", "25M", 0xFFEA580C, "🇳🇱"),
        Language("sv", "Swedish", "İsveççe", "İsveç dili", "Svenska", "Hej!", 1, "Nordic", "10M", 0xFF0284C7, "🇸🇪"),
        Language("no", "Norwegian", "Norveççe", "Norveç dili", "Norsk", "Hei!", 1, "Nordic", "5M", 0xFFB91C1C, "🇳🇴"),
        Language("da", "Danish", "Danca", "Danimarka dili", "Dansk", "Hej!", 1, "Nordic", "6M", 0xFFBE123C, "🇩🇰"),
        Language("fi", "Finnish", "Fince", "Fin dili", "Suomi", "Hei!", 3, "Nordic", "5.5M", 0xFF1E40AF, "🇫🇮"),
        Language("pl", "Polish", "Lehçe", "Polyak dili", "Polski", "Cześć!", 2, "European", "45M", 0xFFDC2626, "🇵🇱"),
        Language("el", "Greek", "Yunanca", "Yunan dili", "Ελληνικά", "Γεια σας", 2, "European", "13M", 0xFF0284C7, "🇬🇷"),
        Language("hi", "Hindi", "Hintçe", "Hind dili", "हिन्दी", "नमस्ते", 2, "Asian", "600M", 0xFFD97706, "🇮🇳"),
        Language("vi", "Vietnamese", "Vietnamca", "Vyetnam dili", "Tiếng Việt", "Xin chào", 2, "Asian", "85M", 0xFFDC2626, "🇻🇳"),
        Language("id", "Indonesian", "Endonezce", "İndoneziya dili", "Bahasa Indonesia", "Halo!", 1, "Asian", "200M", 0xFFDC2626, "🇮🇩"),
        Language("cs", "Czech", "Çekçe", "Çex dili", "Čeština", "Ahoj!", 2, "European", "11M", 0xFF2563EB, "🇨🇿"),
        Language("uk", "Ukrainian", "Ukraynaca", "Ukrayna dili", "Українська", "Привіт!", 2, "European", "40M", 0xFFEAB308, "🇺🇦"),
        Language("ro", "Romanian", "Romence", "Rumın dili", "Română", "Salut!", 1, "European", "24M", 0xFF1D4ED8, "🇷🇴"),
        Language("hu", "Hungarian", "Macarca", "Macar dili", "Magyar", "Szia!", 3, "European", "13M", 0xFF15803D, "🇭🇺"),
        Language("he", "Hebrew", "İbranice", "İvrit dili", "עברית", "שלום", 3, "Middle Eastern", "9M", 0xFF0284C7, "🇮🇱"),
        Language("th", "Thai", "Tayca", "Tay dili", "ไทย", "สวัสดี", 3, "Asian", "70M", 0xFF9333EA, "🇹🇭"),
        Language("fa", "Persian", "Farsça", "Fars dili", "فارسی", "سلام", 2, "Middle Eastern", "110M", 0xFF047857, "🇮🇷"),
        Language("bn", "Bengali", "Bengalce", "Benqal dili", "বাংলা", "হ্যালো", 2, "Asian", "270M", 0xFF059669, "🇧🇩"),
        Language("tl", "Tagalog", "Tagalogca", "Taqaloq dili", "Tagalog", "Kamusta!", 1, "Asian", "80M", 0xFF2563EB, "🇵🇭"),
        Language("sw", "Swahili", "Svahili", "Suahili dili", "Kiswahili", "Jambo!", 1, "African", "150M", 0xFF16A34A, "🇰🇪"),
        Language("ga", "Irish", "İrlandaca", "İrlandiya dili", "Gaeilge", "Dia dhuit!", 2, "European", "2M", 0xFF16A34A, "🇮🇪"),
        Language("is", "Icelandic", "İzlandaca", "İslandiya dili", "Íslenska", "Halló!", 3, "Nordic", "350K", 0xFF1E3A8A, "🇮🇸"),
        Language("bg", "Bulgarian", "Bulgarca", "Bolqar dili", "Български", "Здравейте", 2, "European", "8M", 0xFF15803D, "🇧🇬"),
        Language("hr", "Croatian", "Hırvatça", "Xorvat dili", "Hrvatski", "Bok!", 2, "European", "5M", 0xFFDC2626, "🇭🇷"),
        Language("sr", "Serbian", "Sırpça", "Serb dili", "Српски", "Здраво!", 2, "European", "9M", 0xFF2563EB, "🇷🇸"),
        Language("sk", "Slovak", "Slovakça", "Slovak dili", "Slovenčina", "Ahoj!", 2, "European", "5.5M", 0xFF1E40AF, "🇸🇰"),
        Language("sl", "Slovenian", "Slovence", "Sloven dili", "Slovenščina", "Živjo!", 2, "European", "2.5M", 0xFF2563EB, "🇸🇮"),
        Language("lt", "Lithuanian", "Litvanca", "Litva dili", "Lietuvių", "Labas!", 2, "European", "3M", 0xFFCA8A04, "🇱🇹"),
        Language("lv", "Latvian", "Letonca", "Latış dili", "Latviešu", "Sveiki!", 2, "European", "2M", 0xFF991B1B, "🇱🇻"),
        Language("et", "Estonian", "Estonca", "Eston dili", "Eesti", "Tere!", 3, "Nordic", "1.2M", 0xFF2563EB, "🇪🇪"),
        Language("ca", "Catalan", "Katalanca", "Katalan dili", "Català", "Hola!", 1, "European", "10M", 0xFFEAB308, "🇪🇸"),
        Language("gl", "Galician", "Galiçyaca", "Qalisiya dili", "Galego", "Ola!", 1, "European", "3M", 0xFF0284C7, "🇪🇸"),
        Language("eu", "Basque", "Baskça", "Bask dili", "Euskara", "Kaixo!", 3, "European", "800K", 0xFF15803D, "🇪🇸"),
        Language("ms", "Malay", "Malayca", "Malay dili", "Bahasa Melayu", "Hai!", 1, "Asian", "30M", 0xFFDC2626, "🇲🇾"),
        Language("ur", "Urdu", "Urduca", "Urdu dili", "اردو", "سلام", 2, "Asian", "230M", 0xFF15803D, "🇵🇰"),
        Language("ta", "Tamil", "Tamilce", "Tamil dili", "தமிழ்", "வணக்கம்", 3, "Asian", "80M", 0xFFEA580C, "🇮🇳"),
        Language("te", "Telugu", "Teluguca", "Teluqu dili", "తెలుగు", "నమస్కారం", 3, "Asian", "90M", 0xFF7C3AED, "🇮🇳"),
        Language("mr", "Marathi", "Marathice", "Maratxi dili", "मराठी", "नमस्कार", 2, "Asian", "85M", 0xFFEA580C, "🇮🇳"),
        Language("gu", "Gujarati", "Guceratça", "Qucarat dili", "ગુજરાતી", "નમસ્તે", 2, "Asian", "60M", 0xFFF59E0B, "🇮🇳"),
        Language("kn", "Kannada", "Kannadaca", "Kannada dili", "ಕನ್ನಡ", "ನಮಸ್ಕಾರ", 3, "Asian", "50M", 0xFFEAB308, "🇮🇳"),
        Language("ml", "Malayalam", "Malayalamca", "Malayalam dili", "മലയാളം", "നമസ്കാരം", 3, "Asian", "38M", 0xFFDC2626, "🇮🇳"),
        Language("pa", "Punjabi", "Pencapça", "Pəncab dili", "ਪੰਜਾਬੀ", "ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ", 2, "Asian", "120M", 0xFFF97316, "🇮🇳"),
        Language("ne", "Nepali", "Nepalce", "Nepal dili", "नेपाली", "नमस्ते", 2, "Asian", "30M", 0xFFDC2626, "🇳🇵"),
        Language("si", "Sinhala", "Seylanca", "Sinhal dili", "සිංහල", "ආයුබෝවන්", 3, "Asian", "17M", 0xFFB45309, "🇱🇰"),
        Language("my", "Burmese", "Birmanca", "Birma dili", "မြန်မာစာ", "မင်္ဂလာပါ", 3, "Asian", "33M", 0xFFCA8A04, "🇲🇲"),
        Language("km", "Khmer", "Kmer dili", "Kxmer dili", "ខ្មែរ", "ជំរាបសួរ", 3, "Asian", "16M", 0xFF1D4ED8, "🇰🇭"),
        Language("lo", "Lao", "Laoca", "Laos dili", "ລາວ", "ສະບາຍດີ", 3, "Asian", "7M", 0xFFDC2626, "🇱🇦"),
        Language("ka", "Georgian", "Gürcüce", "Gürcü dili", "ქართული", "გამარჯობა", 3, "European", "4M", 0xFFDC2626, "🇬🇪"),
        Language("hy", "Armenian", "Ermenice", "Erməni dili", "Հայերեն", "Բարև", 2, "European", "7M", 0xFFEA580C, "🇦🇲"),
        Language("kk", "Kazakh", "Kazakça", "Qazax dili", "Қазақша", "Сәлем!", 2, "Asian", "15M", 0xFF0284C7, "🇰🇿"),
        Language("uz", "Uzbek", "Özbekçe", "Özbək dili", "O'zbekcha", "Salom!", 1, "Asian", "35M", 0xFF0284C7, "🇺🇿"),
        Language("ky", "Kyrgyz", "Kırgızca", "Qırğız dili", "Кыргызча", "Саламатсызбы!", 2, "Asian", "5M", 0xFFDC2626, "🇰🇬"),
        Language("tk", "Turkmen", "Türkmence", "Türkmən dili", "Türkmençe", "Salam!", 1, "Asian", "7M", 0xFF15803D, "🇹🇲"),
        Language("tt", "Tatar", "Tatarca", "Tatar dili", "Татарча", "Сәлам!", 1, "Asian", "6M", 0xFF15803D, "🇷🇺"),
        Language("mn", "Mongolian", "Moğolca", "Monqol dili", "Монгол", "Сайн байна уу", 3, "Asian", "6M", 0xFFDC2626, "🇲🇳"),
        Language("am", "Amharic", "Amharca", "Amhar dili", "አማርኛ", "ሰላም", 3, "African", "35M", 0xFF15803D, "🇪🇹"),
        Language("yo", "Yoruba", "Yorubaca", "Yoruba dili", "Yorùbá", "Bawo ni", 2, "African", "45M", 0xFF16A34A, "🇳🇬"),
        Language("ig", "Igbo", "İgboca", "İqbo dili", "Igbo", "Kedu", 2, "African", "30M", 0xFF16A34A, "🇳🇬"),
        Language("ha", "Hausa", "Hausaca", "Hausa dili", "Hausa", "Sannu", 2, "African", "75M", 0xFF15803D, "🇳🇬"),
        Language("zu", "Zulu", "Zuluca", "Zulu dili", "isiZulu", "Sawubona", 2, "African", "12M", 0xFFCA8A04, "🇿🇦"),
        Language("xh", "Xhosa", "Xhosaca", "Xosa dili", "isiXhosa", "Molo", 2, "African", "8M", 0xFFCA8A04, "🇿🇦"),
        Language("af", "Afrikaans", "Afrikanca", "Afrikaans dili", "Afrikaans", "Hallo!", 1, "African", "17M", 0xFFEA580C, "🇿🇦"),
        Language("so", "Somali", "Somalice", "Somali dili", "Soomaali", "Salaan", 2, "African", "22M", 0xFF0284C7, "🇸🇴"),
        Language("mg", "Malagasy", "Malgaşça", "Madaqaskar dili", "Malagasy", "Salama", 2, "African", "25M", 0xFFDC2626, "🇲🇬"),
        Language("rw", "Kinyarwanda", "Ruandaca", "Ruanda dili", "Kinyarwanda", "Muraho", 2, "African", "13M", 0xFF0284C7, "🇷🇼"),
        Language("sq", "Albanian", "Arnavutça", "Alban dili", "Shqip", "Përshëndetje", 2, "European", "6M", 0xFFDC2626, "🇦🇱"),
        Language("mk", "Macedonian", "Makedonca", "Makedon dili", "Македонски", "Здраво", 2, "European", "2M", 0xFFDC2626, "🇲🇰"),
        Language("bs", "Bosnian", "Boşnakça", "Bosniya dili", "Bosanski", "Zdravo!", 2, "European", "3M", 0xFF1D4ED8, "🇧🇦"),
        Language("mt", "Maltese", "Maltaca", "Malta dili", "Malti", "Merħba", 2, "European", "500K", 0xFFDC2626, "🇲🇹"),
        Language("cy", "Welsh", "Galce", "Uels dili", "Cymraeg", "Helo!", 2, "European", "900K", 0xFF15803D, "🏴󠁧󠁢󠁷󠁬󠁳󠁿"),
        Language("gd", "Scottish Gaelic", "İskoçça", "Şotland dili", "Gàidhlig", "Halò!", 2, "European", "60K", 0xFF1D4ED8, "🏴󠁧󠁢󠁳󠁣󠁴󠁿"),
        Language("lb", "Luxembourgish", "Lüksemburgca", "Lüksemburq dili", "Lëtzebuergesch", "Moien!", 1, "European", "400K", 0xFF0284C7, "🇱🇺"),
        Language("eo", "Esperanto", "Esperanto", "Esperanto", "Esperanto", "Saluton!", 1, "Popular", "2M", 0xFF15803D, "🌍"),
        Language("la", "Latin", "Latince", "Latın dili", "Latina", "Salve!", 2, "European", "Scholarly", 0xFF991B1B, "🏛️"),
        Language("sa", "Sanskrit", "Sanskritçe", "Sanskrit dili", "संस्कृतम्", "नमस्ते", 3, "Asian", "Classical", 0xFFEA580C, "🕉️"),
        Language("be", "Belarusian", "Belarusça", "Belarus dili", "Беларуская", "Прывітанне", 2, "European", "7M", 0xFFDC2626, "🇧🇾"),
        Language("ps", "Pashto", "Peştuca", "Puştu dili", "پښتو", "سلام", 3, "Middle Eastern", "50M", 0xFF15803D, "🇦🇫"),
        Language("ku", "Kurdish", "Kürtçe", "Kürd dili", "Kurdî", "Silav!", 2, "Middle Eastern", "30M", 0xFFCA8A04, "☀️"),
        Language("tg", "Tajik", "Tacikçe", "Tacik dili", "Тоҷикӣ", "Салом!", 2, "Asian", "10M", 0xFFDC2626, "🇹🇯"),
        Language("ug", "Uyghur", "Uygurca", "Uyğur dili", "ئۇيغۇرچە", "ياخشىمۇسىز", 2, "Asian", "12M", 0xFF0284C7, "☪️"),
        Language("ba", "Bashkir", "Başkurtça", "Başqırd dili", "Башҡортса", "Һаумыһығыҙ", 2, "Asian", "1.5M", 0xFF0284C7, "🇷🇺"),
        Language("cv", "Chuvash", "Çuvaşça", "Çuvaş dili", "Чӑвашла", "Ырӑ кун", 2, "European", "1M", 0xFFDC2626, "🇷🇺"),
        Language("ceb", "Cebuano", "Sebuanca", "Sebuano dili", "Bisaya", "Kumusta", 1, "Asian", "20M", 0xFF2563EB, "🇵🇭"),
        Language("jw", "Javanese", "Cavaca", "Yava dili", "Basa Jawa", "Sugeng", 2, "Asian", "80M", 0xFFDC2626, "🇮🇩"),
        Language("su", "Sundanese", "Sundaca", "Sunda dili", "Basa Sunda", "Sampurasun", 2, "Asian", "40M", 0xFF15803D, "🇮🇩"),
        Language("haw", "Hawaiian", "Havai dili", "Havay dili", "ʻŌlelo Hawaiʻi", "Aloha!", 1, "Americas", "25K", 0xFFEA580C, "🌺"),
        Language("mi", "Maori", "Maorice", "Maori dili", "Te Reo Māori", "Kia ora!", 2, "Asian", "150K", 0xFFDC2626, "🇳🇿"),
        Language("sm", "Samoan", "Samoaca", "Samoa dili", "Gagana Sāmoa", "Talofa!", 1, "Asian", "500K", 0xFF1D4ED8, "🇼🇸"),
        Language("to", "Tongan", "Tongaca", "Tonqa dili", "Lea Fakatonga", "Mālō e lelei", 2, "Asian", "180K", 0xFFDC2626, "🇹🇴"),
        Language("fj", "Fijian", "Ficice", "Fici dili", "Na Vosa Vakaviti", "Bula!", 1, "Asian", "600K", 0xFF0284C7, "🇫🇯"),
        Language("kl", "Greenlandic", "Grönlandca", "Qrenlandiya dili", "Kalaallisut", "Aluu!", 3, "Nordic", "56K", 0xFFDC2626, "🇬🇱")
    )

    fun findByCode(code: String): Language {
        return languages.find { it.code.equals(code, ignoreCase = true) } ?: languages.first()
    }
}
