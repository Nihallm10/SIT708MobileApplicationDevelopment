package com.example.a31p_quiz_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var buttonstart: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.username)
        buttonstart = findViewById(R.id.submitButton)

        buttonstart.setOnClickListener {
            val text = editTextName.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(this@MainActivity, "Please enter a value", Toast.LENGTH_SHORT).show()
            } else {
                val userName = editTextName.text.toString()
                val intent = Intent(this@MainActivity, FIrstPage::class.java)
                intent.putExtra("UserName", userName)
                startActivity(intent)
            }
        }
    }
}
