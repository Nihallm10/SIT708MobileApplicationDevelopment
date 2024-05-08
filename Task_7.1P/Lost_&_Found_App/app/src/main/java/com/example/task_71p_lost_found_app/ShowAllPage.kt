package com.example.task_71p_lost_found_app

import android.database.Cursor
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowAllPage : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val itemList = ArrayList<ItemList>()
    private lateinit var itemAdapter: AdapterItem
    private lateinit var dtbase: DtBase
    private lateinit var noItemTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_page)

        recyclerView = findViewById(R.id.rv)
        dtbase = DtBase(this)
//        noItemTextView = findViewById(R.id.noitem)

        val layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        itemAdapter = AdapterItem(applicationContext, itemList)
        recyclerView.adapter = itemAdapter

        val cursor: Cursor = dtbase.getAllItems()
        if (cursor.count == 0) {
            Toast.makeText(applicationContext, "Empty", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                val item = ItemList(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
                itemList.add(item)
            }
            itemAdapter.notifyDataSetChanged()
        }
    }
}
