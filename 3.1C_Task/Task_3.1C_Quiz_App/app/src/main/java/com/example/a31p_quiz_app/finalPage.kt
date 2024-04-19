package com.example.a31p_quiz_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class finalPage : AppCompatActivity() {
    private lateinit var dUsername: TextView
    private lateinit var dscore: TextView
    private lateinit var newquizbutton: Button
    private lateinit var finishbutton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_page)

        val intent = intent
        if (intent != null) {
            val dusername = intent.getStringExtra("username")
            val dfinalscore = intent.getIntExtra("finalscore", 0)
            dUsername = findViewById(R.id.apusername)
            dscore = findViewById(R.id.score)
            dUsername.text = "Congratulations, $dusername"
            dscore.text = "$dfinalscore / 10"
        }

        newquizbutton = findViewById(R.id.newquizbutton)
        finishbutton = findViewById(R.id.finishbutton)

        newquizbutton.setOnClickListener {
            val intent = Intent(this@finalPage, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        finishbutton.setOnClickListener {
            finishAffinity()
        }
    }
}
