package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PurpleBright

@Composable
fun DuolingoBottomBar(
    currentTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    uiLang: String = "tr",
    modifier: Modifier = Modifier
) {
    val labels = when (uiLang) {
        "az" -> listOf("Öyrən", "AI Söhbət", "AI Plan", "Robot Lab", "Profil")
        "en" -> listOf("Learn", "AI Chat", "AI Plan", "Robot Lab", "Profile")
        else -> listOf("Öğren", "AI Sohbet", "AI Plan", "Robot Lab", "Profil")
    }

    NavigationBar(
        containerColor = Color(0xFF1C1438),
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        // Tab 0: Learn
        NavigationBarItem(
            selected = currentTabIndex == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Learn") },
            label = { Text(labels[0], fontSize = 11.sp, fontWeight = if (currentTabIndex == 0) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurpleBright,
                selectedTextColor = PurpleBright,
                indicatorColor = Color(0xFF3B2A68),
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // Tab 1: AI Chat, Voice & Vision
        NavigationBarItem(
            selected = currentTabIndex == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Chat") },
            label = { Text(labels[1], fontSize = 11.sp, fontWeight = if (currentTabIndex == 1) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurpleBright,
                selectedTextColor = PurpleBright,
                indicatorColor = Color(0xFF3B2A68),
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // Tab 2: AI Study Plan & Diagnosis
        NavigationBarItem(
            selected = currentTabIndex == 2,
            onClick = { onTabSelected(2) },
            icon = { Icon(Icons.Default.Psychology, contentDescription = "AI Plan") },
            label = { Text(labels[2], fontSize = 11.sp, fontWeight = if (currentTabIndex == 2) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurpleBright,
                selectedTextColor = PurpleBright,
                indicatorColor = Color(0xFF3B2A68),
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // Tab 3: Robot Lab & Wardrobe
        NavigationBarItem(
            selected = currentTabIndex == 3,
            onClick = { onTabSelected(3) },
            icon = { Icon(Icons.Default.SmartToy, contentDescription = "Robot Lab") },
            label = { Text(labels[3], fontSize = 11.sp, fontWeight = if (currentTabIndex == 3) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurpleBright,
                selectedTextColor = PurpleBright,
                indicatorColor = Color(0xFF3B2A68),
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )

        // Tab 4: Profile & Login
        NavigationBarItem(
            selected = currentTabIndex == 4,
            onClick = { onTabSelected(4) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text(labels[4], fontSize = 11.sp, fontWeight = if (currentTabIndex == 4) FontWeight.Bold else FontWeight.Normal) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = PurpleBright,
                selectedTextColor = PurpleBright,
                indicatorColor = Color(0xFF3B2A68),
                unselectedIconColor = Color(0xFF94A3B8),
                unselectedTextColor = Color(0xFF94A3B8)
            )
        )
    }
}
