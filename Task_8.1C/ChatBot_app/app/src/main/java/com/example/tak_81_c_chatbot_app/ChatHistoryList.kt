package com.example.tak_81_c_chatbot_app

import com.google.gson.annotations.SerializedName

data class ChatHistoryList(
    @SerializedName("User") val user: String,
    @SerializedName("Llama") val llama: String
)