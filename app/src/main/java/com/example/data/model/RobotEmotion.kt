package com.example.data.model

enum class RobotEmotion {
    IDLE,
    HAPPY,
    THINKING,
    SASSY_ANGRY,
    CELEBRATING
}

object RobotDialogue {
    fun getDialogue(emotion: RobotEmotion, uiLang: String, mistakeCount: Int = 0): String {
        return when (uiLang) {
            "az" -> when (emotion) {
                RobotEmotion.HAPPY -> "Əla getdin! Mikrosxemlərim sevinclə parıldayır!"
                RobotEmotion.CELEBRATING -> "Möhtəşəm! Bu dərsi uğurla tamamladın!"
                RobotEmotion.THINKING -> "Bip-bup... Düşünürəm və analiz edirəm..."
                RobotEmotion.SASSY_ANGRY -> if (mistakeCount > 2) {
                    "Bip-bop! Devrələrim qızır! Bu qaydanı artıq 3-cü dəfədir səhv edirsən! Diqqətli ol!"
                } else {
                    "Bip! Prosessorum bu xətanı qəbul etmir! Bir daha diqqətlə bax!"
                }
                RobotEmotion.IDLE -> "Salam dostum! Bu gün hansı dildə rekord vururuq?"
            }
            "en" -> when (emotion) {
                RobotEmotion.HAPPY -> "Beep boop! Great job! My circuits are buzzing with joy!"
                RobotEmotion.CELEBRATING -> "Incredible! Unit mastered with flying colors!"
                RobotEmotion.THINKING -> "Processing neural pathways... Let's analyze this."
                RobotEmotion.SASSY_ANGRY -> if (mistakeCount > 2) {
                    "Beep-bop! System temperature critical! My circuits are overheating! Concentrate!"
                } else {
                    "Beep! Even my low-power standby mode could solve that! Focus up!"
                }
                RobotEmotion.IDLE -> "Ready to boost your language processor today?"
            }
            else -> when (emotion) { // "tr" default
                RobotEmotion.HAPPY -> "Bip-bup! Harikasın! Çiplerim sevinçten parıldıyor!"
                RobotEmotion.CELEBRATING -> "İnanılmaz! Bu bölümü tam puanla geçtik!"
                RobotEmotion.THINKING -> "Bip... Analiz yapıyorum, en doğru cevabı bulalım."
                RobotEmotion.SASSY_ANGRY -> if (mistakeCount > 2) {
                    "Bip-bop! Devrelerim aşırı ısınıyor! Sıcaklık 98°C oldu! Lütfen odaklan!"
                } else {
                    "Bip! İşlemcim bu hatayı sindiremedi! Dikkatini topla bakalım!"
                }
                RobotEmotion.IDLE -> "Merhaba! Bugün hangi dili fethedeceğiz?"
            }
        }
    }
}
