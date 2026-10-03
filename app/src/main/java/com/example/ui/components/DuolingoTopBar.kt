package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LanguageCatalog
import com.example.ui.theme.GemTeal
import com.example.ui.theme.HeartColor
import com.example.ui.theme.StreakFlameColor

@Composable
fun DuolingoTopBar(
    languageCode: String,
    streakDays: Int,
    gems: Int,
    hearts: Int,
    onLanguageClick: () -> Unit,
    onStreakClick: () -> Unit,
    onHeartsClick: () -> Unit,
    uiLang: String = "tr",
    modifier: Modifier = Modifier
) {
    val currentLang = LanguageCatalog.findByCode(languageCode)

    Surface(
        color = Color(0xFF140D2B),
        tonalElevation = 6.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flag / Language Picker Pill with Dropdown
            Surface(
                color = Color(0xFF28184C),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF4A3282)),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onLanguageClick
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = currentLang.flagEmoji, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = currentLang.getDisplayName(uiLang),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Color.White
                    )
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Switch Language",
                        tint = Color(0xFFB0A4D6),
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }

            // Streak Pill (Clickable -> Opens Streak Hub)
            Surface(
                color = Color(0xFF28184C),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4A3282)),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onStreakClick
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    FlameIcon(size = 18.dp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = streakDays.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = StreakFlameColor
                    )
                }
            }

            // Gems Pill
            Surface(
                color = Color(0xFF28184C),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4A3282)),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onHeartsClick
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    GemIcon(size = 17.dp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = gems.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = GemTeal
                    )
                }
            }

            // Hearts Pill (Clickable)
            Surface(
                color = Color(0xFF28184C),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4A3282)),
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onHeartsClick
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    HeartIcon(size = 17.dp, isFilled = hearts > 0)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = hearts.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = if (hearts > 0) HeartColor else Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}
