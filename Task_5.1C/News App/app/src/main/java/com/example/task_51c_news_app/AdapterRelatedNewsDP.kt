package com.example.task_51c_news_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.task_51c_news_app.databinding.LayoutCardRelnewsNewsdetpgBinding

class AdapterRelatedNewsDP(
    private val context: Context,
    private val newsList: List<CustomRelatedNewsItem>
) : RecyclerView.Adapter<AdapterRelatedNewsDP.CustomRelatedNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRelatedNewsViewHolder {
        val binding = LayoutCardRelnewsNewsdetpgBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomRelatedNewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomRelatedNewsViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.binding.apply {
            image.setImageResource(currentItem.imageResource)
            topnewstitle.text = currentItem.newsTitle
            description.text = currentItem.newsDescription
            root.setOnClickListener {

                val fragment = News_details_page().apply {

                    arguments = Bundle().apply {
                        putString("NewsTitle", currentItem.newsTitle)
                        putString("NewsDescription", currentItem.newsDescription)
                        putInt("NewsImage", currentItem.imageResource)
                    }
                }


                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.mainFrameLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    inner class CustomRelatedNewsViewHolder(val binding: LayoutCardRelnewsNewsdetpgBinding) : RecyclerView.ViewHolder(binding.root)
}

