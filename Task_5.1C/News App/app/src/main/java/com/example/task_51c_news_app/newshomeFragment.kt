package com.example.task_51c_news_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task_51c_news_app.databinding.FragmentNewshomeBinding

class newshomeFragment : Fragment() {
    private lateinit var fragmentBinding: FragmentNewshomeBinding

    private lateinit var adaptertopnews: AdapterTopNews
    private lateinit var adapterrelatednews: AdapterRelatedNews
    private val customTopNewsList = listOf(
        TopNewsItem("Top News", "News Description", R.drawable.img1),
        TopNewsItem("Top News", "News Description", R.drawable.img1),
        TopNewsItem("Top News", "News Description", R.drawable.img1),
        TopNewsItem("Top News", "News Description", R.drawable.img1),

        TopNewsItem("Top News", "News Description", R.drawable.img1),
        TopNewsItem("Top News", "News Description", R.drawable.img1),
        TopNewsItem("Top News", "News Description", R.drawable.img1),
        TopNewsItem("Top News", "News Description", R.drawable.img1)
        // Add more top news items as needed
    )
    private val customRelatedNewsList = listOf(
        CustomRelatedNewsItem("ABC News", "Scientists discover a new species of deep-sea fish with bioluminescent features, shedding light on marine biodiversity and evolutionary adaptations.", R.drawable.img1),
        CustomRelatedNewsItem("7 News", "Global tech company unveils revolutionary AI-driven platform for sustainable agriculture, promising increased crop yields and environmental conservation.", R.drawable.img1),
        CustomRelatedNewsItem("9 News", "SpaceX successfully launches new crewed mission to the International Space Station, marking another milestone in space exploration.", R.drawable.img1),
        CustomRelatedNewsItem("BBC News", "World leaders convene for climate summit, pledging ambitious targets to combat greenhouse gas emissions and address climate change.", R.drawable.img1),
        CustomRelatedNewsItem("Sky News", "Renowned artist's masterpiece sets auction record, selling for a staggering price at an art auction house.", R.drawable.img1),
        CustomRelatedNewsItem("CBS News", "Tech giant announces plans for next-generation smartphone with cutting-edge features, aiming to redefine the mobile industry.", R.drawable.img1)
        // Add more related news items as needed
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentNewshomeBinding.inflate(inflater,container,false)
        val newMainPage:View = fragmentBinding.root
        fragmentBinding.apply {
            // Set up the Top News RecyclerView
            adaptertopnews = AdapterTopNews(requireContext(), customTopNewsList)
            topNewsRecycler.adapter = adaptertopnews
            topNewsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            // Set up the Related News RecyclerView
            adapterrelatednews = AdapterRelatedNews(requireContext(), customRelatedNewsList)
            relatedNewsRecycler.adapter = adapterrelatednews
            relatedNewsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        return newMainPage
    }
}
