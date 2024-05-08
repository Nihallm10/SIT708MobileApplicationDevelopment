package com.example.task_71p_lost_found_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ItemViewsPage : AppCompatActivity() {
    private lateinit var itemname: TextView
    private lateinit var itemdate: TextView
    private lateinit var itemloc: TextView
    private lateinit var itemtype: TextView
    private lateinit var delete_buton: Button
    private lateinit var dtBase: DtBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_views_page)
        dtBase = DtBase(this)

        val intent = intent
        val item_id = intent.getStringExtra("item_id")
        val item_name = intent.getStringExtra("item_name")
        val item_desc = intent.getStringExtra("item_desc")
        val item_date = intent.getStringExtra("item_date")
        val item_loc = intent.getStringExtra("item_loc")
        val item_type = intent.getStringExtra("item_type")
        val phone = intent.getStringExtra("phone")

        itemname = findViewById(R.id.itemname)
        itemdate = findViewById(R.id.date)
        itemloc = findViewById(R.id.loc)
        itemtype = findViewById(R.id.itemtype)

        itemname.text = item_name
        itemtype.text = "$item_type : "
        itemloc.text = item_loc
        itemdate.text = item_date

        delete_buton = findViewById(R.id.delete_btn)
        delete_buton.setOnClickListener {
            val deleteSuccessful = dtBase.deleteItem(item_id)
            if (deleteSuccessful) {
                Toast.makeText(this@ItemViewsPage, "Advert Deleted", Toast.LENGTH_SHORT).show()
                val i = Intent(this@ItemViewsPage, ShowAllPage::class.java)
                startActivity(i)
            }
        }
    }
}