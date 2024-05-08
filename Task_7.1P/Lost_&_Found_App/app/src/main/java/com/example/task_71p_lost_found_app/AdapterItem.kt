package com.example.task_71p_lost_found_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterItem(private val applicationContext: Context, private val list: List<ItemList>) :
    RecyclerView.Adapter<AdapterItem.MyViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    private val dtbase: DtBase = DtBase(applicationContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.item_views, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.type.text = currentItem.itemType
        holder.type.text = "${position + 1}. ${currentItem.itemType}"
        holder.name.text = currentItem.name
        holder.itemView.setOnClickListener {
            val intent = Intent(applicationContext, ItemViewsPage::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("item_id", currentItem.id)
            intent.putExtra("item_name", currentItem.name)
            intent.putExtra("item_desc", currentItem.description)
            intent.putExtra("item_date", currentItem.date)
            intent.putExtra("item_loc", currentItem.location)
            intent.putExtra("item_type", currentItem.itemType)
            intent.putExtra("phone", currentItem.phone)
            applicationContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView = itemView.findViewById(R.id.textView6)
        val name: TextView = itemView.findViewById(R.id.textView7)
    }
}

