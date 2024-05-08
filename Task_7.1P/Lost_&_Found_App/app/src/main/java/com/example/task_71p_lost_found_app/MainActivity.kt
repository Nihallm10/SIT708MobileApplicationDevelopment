package com.example.task_71p_lost_found_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onNewAdvertClicked(view: View) {
        val intent = Intent(this@MainActivity, NewAdvertPage::class.java)
        startActivity(intent)
    }

    fun onShowAllClicked(view: View) {
        val intent = Intent(this@MainActivity, ShowAllPage::class.java)
        startActivity(intent)
    }
}
