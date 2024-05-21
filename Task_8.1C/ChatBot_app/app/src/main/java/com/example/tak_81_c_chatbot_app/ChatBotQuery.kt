package com.example.tak_81_c_chatbot_app

import com.google.gson.annotations.SerializedName

data class ChatBotQuery(
    @SerializedName("userMessage") val userMessage: String,
    @SerializedName("chatHistory") val chatHistory: List<ChatHistoryList>
)
