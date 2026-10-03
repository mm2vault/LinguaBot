package com.example.data.model

data class ChatMessage(
    val id: String,
    val sender: String, // "user" or "byte"
    val text: String,
    val imageBase64: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
