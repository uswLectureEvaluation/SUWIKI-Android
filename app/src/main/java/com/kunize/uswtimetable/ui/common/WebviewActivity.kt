package com.kunize.uswtimetable.ui.common

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.Constants.KEY_URL

class WebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val startUrl = intent.getStringExtra(KEY_URL) ?: getString(R.string.SUWIKI_WEB)

        val webView: WebView = findViewById(R.id.webView)
        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.supportZoom()
            settings.builtInZoomControls = true
            settings.javaScriptEnabled = true
        }
        webView.loadUrl(startUrl)

        val closeButton: ImageView = findViewById(R.id.btn_close_webview)
        closeButton.setOnClickListener { finish() }
    }
}