package com.example.tak_81_c_chatbot_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsPg : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var adapterChat: AdapterChat
    private val messagesList = ArrayList<Message>()
    private lateinit var conversaBotService: ConversaBotService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats_pg)

        val userName = intent.getStringExtra("USERNAME") ?: "User"
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)

        adapterChat = AdapterChat(messagesList)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = adapterChat

        conversaBotService = ChatBotAPIClient.getClientWithLogging().create(ConversaBotService::class.java)

        // Add greeting message from chatbot
        addMessage(Message("Hello, $userName!", false))

        sendButton.setOnClickListener {
            sendMessageToChatBot()
        }
    }

    private fun sendMessageToChatBot() {
        val messageText = messageEditText.text.toString().trim()
        if (messageText.isNotEmpty()) {
            // Add user message to RecyclerView
            addMessage(Message(messageText, true))
            // Clear message EditText
            messageEditText.setText("")
            // Send message to chatbot API
            val request = ChatBotQuery(messageText, emptyList()) // Pass an empty list for chatHistory
            val call = conversaBotService.getChatBotResponseWithLogging(request)
            call.enqueue(object : Callback<ChatReply> {
                override fun onResponse(call: Call<ChatReply>, response: Response<ChatReply>) {
                    if (response.isSuccessful && response.body() != null) {
                        val message = response.body()?.message
                        // Add chatbot response to RecyclerView
                        message?.let { addMessage(Message(it, false)) }
                    } else {
                        // Handle unsuccessful response
                        Toast.makeText(this@ChatsPg, "Failed to get response from chatbot", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ChatReply>, t: Throwable) {
                    // Handle network error
                    Toast.makeText(this@ChatsPg, "Network error. Please try again later.", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMessage(message: Message) {
        messagesList.add(message)
        adapterChat.notifyDataSetChanged()
        chatRecyclerView.smoothScrollToPosition(messagesList.size - 1)
    }
}
