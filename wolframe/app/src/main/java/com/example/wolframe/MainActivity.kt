package com.example.wolframe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings.*
import android.webkit.WebSettings.RenderPriority.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.View as View

class MainActivity : AppCompatActivity() {
    var mWebView : WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWebView = findViewById<View>(R.id.webView) as WebView
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url)
                return true
            }
        }
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.setRenderPriority(HIGH)
        webSettings.cacheMode = LOAD_CACHE_ELSE_NETWORK
        webSettings.domStorageEnabled = true
        webSettings.layoutAlgorithm = LayoutAlgorithm.NORMAL
        webSettings.useWideViewPort = true
        webSettings.saveFormData = true
        webSettings.savePassword = true
        webSettings.setEnableSmoothTransition(true)
        mWebView!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        mWebView!!.loadUrl( "https://www.wolframalpha.com/")
    }

    override fun onBackPressed() {
        if(mWebView!!.canGoBack()){
            mWebView!!.goBack()
        }
        else {
            super.onBackPressed()
        }

    }
}




