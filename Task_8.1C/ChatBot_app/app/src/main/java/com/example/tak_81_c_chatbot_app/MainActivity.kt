package com.example.tak_81_c_chatbot_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var uNameEditText: EditText
    private lateinit var gBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uNameEditText = findViewById(R.id.usernameEditText)
        gBtn = findViewById(R.id.goButton)

        gBtn.setOnClickListener {

            val userName = uNameEditText.text.toString()


            val intent = Intent(this@MainActivity, ChatsPg::class.java)
            intent.putExtra("Enter Username", userName)
            startActivity(intent)
        }
    }
}