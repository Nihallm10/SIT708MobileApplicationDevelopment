package com.example.task_51c_itune_app

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class VideoDisplay : AppCompatActivity() {

    private lateinit var mWebView: WebView
    private lateinit var mYoutubeUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_display)

        mYoutubeUrl = intent.getStringExtra("youtubeUrl") ?: ""

        mWebView = findViewById(R.id.webview)
        // Configure WebView settings
        val webSettings: WebSettings = mWebView.settings
        webSettings.javaScriptEnabled = true // Enable JavaScript
        webSettings.domStorageEnabled = true // Enable local storage

        // Set a WebViewClient to handle navigation within the WebView
        mWebView.webViewClient = WebViewClient()

        // Load the YouTube URL in the WebView
        mWebView.loadUrl(mYoutubeUrl)
    }
}
