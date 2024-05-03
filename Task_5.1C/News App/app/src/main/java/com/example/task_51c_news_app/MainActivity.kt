package com.example.task_51c_news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.task_51c_news_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private var currentDisplayedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.apply {
            displayFragment(newshomeFragment())
        }
    }

    fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrameLayout, fragment)
            .addToBackStack(null)
            .commit()
        currentDisplayedFragment = fragment
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            currentDisplayedFragment = null
        } else {
            super.onBackPressed()
        }
    }
}
