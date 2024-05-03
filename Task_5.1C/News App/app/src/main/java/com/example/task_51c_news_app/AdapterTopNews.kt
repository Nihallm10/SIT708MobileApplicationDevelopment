package com.example.task_51c_news_app


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.task_51c_news_app.databinding.LayoutCardBinding

class AdapterTopNews(private val context: Context, private val topNewsList: List<TopNewsItem>) : RecyclerView.Adapter<AdapterTopNews.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = LayoutCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = topNewsList[position]
        holder.binding.apply {
            image.setImageResource(currentItem.imageResource)
//            topnewstitle.text = currentItem.title
            root.setOnClickListener {
                // Create a new instance of the NewsDetailsFragment fragment
                val fragment = News_details_page().apply {
                    // Pass data to the fragment using arguments bundle
                    arguments = Bundle().apply {
                        putString("NewsTitle", currentItem.title)
                        putString("NewsDescription", currentItem.description)
                        putInt("NewsImage", currentItem.imageResource)
                    }
                }

                // Replace the current fragment with NewsDetailsFragment fragment
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.mainFrameLayout, fragment)
                transaction.addToBackStack(null) // Optional: Add fragment to back stack
                transaction.commit()
            }
        }
    }

    override fun getItemCount(): Int = topNewsList.size

    inner class CustomViewHolder(val binding: LayoutCardBinding) : RecyclerView.ViewHolder(binding.root)
}

