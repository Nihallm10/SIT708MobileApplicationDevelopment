package com.example.tak_81_c_chatbot_app

import com.google.gson.annotations.SerializedName

data class ChatReply(
    @SerializedName("message") val message: String
)