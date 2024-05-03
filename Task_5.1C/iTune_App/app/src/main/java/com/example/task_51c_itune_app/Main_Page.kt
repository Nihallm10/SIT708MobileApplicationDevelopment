package com.example.task_51c_itune_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Main_Page : AppCompatActivity() {
    private lateinit var youtubeUrlEditText: EditText
    private lateinit var playButton: Button
    private lateinit var addUrlButton: Button
    private lateinit var myPlaylistButton: Button
    private var username: String? = null
    private lateinit var dbase: DatabaseStr

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        dbase = DatabaseStr(this)
        youtubeUrlEditText = findViewById(R.id.youtubeurl)
        playButton = findViewById(R.id.Playbutton)
        addUrlButton = findViewById(R.id.Addtoplaylist)
        myPlaylistButton = findViewById(R.id.myplaylist)

        username = intent.getStringExtra("username")

        playButton.setOnClickListener {
            val youtubeUrl = youtubeUrlEditText.text.toString().trim()
            if (!youtubeUrl.isEmpty()) {
                val intent = Intent(this, VideoDisplay::class.java)
                intent.putExtra("youtubeUrl", youtubeUrl)
                startActivity(intent)
            }
        }

        addUrlButton.setOnClickListener {
            val url = youtubeUrlEditText.text.toString().trim()
            if (!TextUtils.isEmpty(url)) {
                val inserted = dbase.insertPlaylistUrl(username ?: "", url)
                val message = if (inserted != -1L) {
                    "URL added to playlist"
                } else {
                    "Failed to add URL to playlist"
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show()
            }
        }

        myPlaylistButton.setOnClickListener {
            val intent = Intent(this, MyPlaylist_PG::class.java)
            intent.putExtra("username", username) // Pass the username
            startActivity(intent)
        }
    }
}
