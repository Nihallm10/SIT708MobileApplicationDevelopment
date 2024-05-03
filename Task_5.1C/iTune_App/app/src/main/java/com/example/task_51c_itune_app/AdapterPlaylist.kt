package com.example.task_51c_itune_app


import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class AdapterPlaylist(context: Context, c: Cursor?) : CursorAdapter(context, c, 0) {
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        // Inflate the layout for each list item
        return LayoutInflater.from(context).inflate(R.layout.items_playilist, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        // Bind data to the views in each list item
        val urlTextView: TextView = view.findViewById(R.id.urlTextView)

        // Get the URL from the cursor and set it to the TextView
        val urlIndex = cursor.getColumnIndexOrThrow(DatabaseStr.COLUMN_URL)
        val url = cursor.getString(urlIndex)
        urlTextView.text = url
    }
}

