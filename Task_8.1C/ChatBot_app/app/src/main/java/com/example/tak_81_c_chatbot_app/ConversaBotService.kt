package com.example.tak_81_c_chatbot_app

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ConversaBotService {
    @POST("chat")
    fun getChatBotResponse(@Body request: ChatBotQuery): Call<ChatReply>

    // Add logging statement to method definition
    @POST("chat")
    fun getChatBotResponseWithLogging(@Body request: ChatBotQuery): Call<ChatReply>
}