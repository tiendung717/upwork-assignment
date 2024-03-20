package com.dungnt.webapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(), "Android")
        webView.loadUrl("file:///android_asset/index.html")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                sendLifecycleStateToWebView("onCreate")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sendLifecycleStateToWebView("onStart")
    }

    override fun onResume() {
        super.onResume()
        sendLifecycleStateToWebView("onResume")
    }

    override fun onPause() {
        super.onPause()
        sendLifecycleStateToWebView("onPause")
    }

    override fun onStop() {
        super.onStop()
        sendLifecycleStateToWebView("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        sendLifecycleStateToWebView("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        sendLifecycleStateToWebView("onDestroy")
    }

    private fun sendLifecycleStateToWebView(state: String) {
        webView.evaluateJavascript("window._freed_wv_msg('$state', 'Activity lifecycle state changed to $state')", null)
    }


    private inner class WebAppInterface {
        @JavascriptInterface
        fun getOSInfo(): String {
            val osName = System.getProperty("os.name")
            val osVersion = System.getProperty("os.version")
            val osArch = System.getProperty("os.arch")
            return "OS Name: $osName, OS Version: $osVersion, OS Architecture: $osArch"
        }
    }
}