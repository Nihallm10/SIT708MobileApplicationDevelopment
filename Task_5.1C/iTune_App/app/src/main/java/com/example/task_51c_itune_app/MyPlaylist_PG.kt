package com.example.task_51c_itune_app


import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MyPlaylist_PG : AppCompatActivity() {
    private lateinit var mListView: ListView
    private lateinit var mAdapter: AdapterPlaylist
    private lateinit var textView: TextView
    private lateinit var dbase: DatabaseStr
    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_playlist_pg)
        textView = findViewById(R.id.Myplaylist)
        mListView = findViewById(R.id.mylist)
        dbase = DatabaseStr(this)
        username = intent.getStringExtra("username") ?: ""

        populatePlaylist()
    }

    private fun populatePlaylist() {
        val cursor: Cursor? = dbase.getPlaylistItems(username)
        if (cursor != null) {
            if (cursor.count == 0) {
                textView.visibility = View.VISIBLE // Show the TextView
                mListView.visibility = View.GONE // Hide the ListView
                textView.text = "No playlist add some playlist items"
            } else {
                mAdapter = AdapterPlaylist(this, cursor)
                mListView.adapter = mAdapter
                textView.visibility = View.VISIBLE// Hide the TextView
                mListView.visibility = View.VISIBLE // Show the ListView
            }
        }
    }
}
