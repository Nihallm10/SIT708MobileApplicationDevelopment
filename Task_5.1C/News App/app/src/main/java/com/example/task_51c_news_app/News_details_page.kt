package com.example.task_51c_news_app


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task_51c_news_app.databinding.FragmentNewsDetailsPageBinding

class News_details_page : Fragment() {
    private lateinit var binding: FragmentNewsDetailsPageBinding
    private lateinit var adapterrelatednews: AdapterRelatedNewsDP

    private val customRelatedNewsList = listOf(
        CustomRelatedNewsItem("Related News", "Robotics company unveils humanoid assistant capable of performing complex tasks autonomously.", R.drawable.img1),
        CustomRelatedNewsItem("Related News", "Global economy sees robust growth as major markets rebound from pandemic impact.", R.drawable.img1),
        CustomRelatedNewsItem("Related News", "Medical breakthrough: gene-editing therapy shows promising results in treating genetic disorders.", R.drawable.img1),
        CustomRelatedNewsItem("Related News", "Cultural heritage preserved: ancient archaeological site unearthed in ongoing excavation.", R.drawable.img1),
        CustomRelatedNewsItem("Related News", "Futuristic transportation: first commercial hyperloop system launches, promising rapid travel.", R.drawable.img1),
        CustomRelatedNewsItem("Related News", "AI-powered healthcare system revolutionizes patient care and diagnosis accuracy.", R.drawable.img1)
        // Add more related news items as needed
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailsPageBinding.inflate(inflater, container, false)
        val newsdetailsPageBinding: View = binding.root

        // Retrieve data passed from the adapter
        val newsTitle = arguments?.getString("NewsTitle")
        val newsDescription = arguments?.getString("NewsDescription")
        val newsImage = arguments?.getInt("NewsImage")

        binding.apply {
            detailTitle.text = newsTitle
            detailDescription.text = newsDescription
            imageDetail.setImageResource(newsImage ?: R.drawable.img1)
            // Set news image using newsImage, if null use default_image
            adapterrelatednews = AdapterRelatedNewsDP(requireContext(), customRelatedNewsList)
            verticalRecyclerView.adapter = adapterrelatednews
            verticalRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        }



        return newsdetailsPageBinding
    }
}


