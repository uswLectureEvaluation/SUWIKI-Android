package com.kunize.uswtimetable.ui.common

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.BackKeyManager
import com.kunize.uswtimetable.util.Constants.KEY_URL

class WebviewActivity : AppCompatActivity() {
    private val webView: WebView by lazy { findViewById(R.id.webView) }
    private val backKeyManager: BackKeyManager by lazy { BackKeyManager(this) }
    private val progressBar: ContentLoadingProgressBar by lazy { findViewById(R.id.progress_bar) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val startUrl = intent.getStringExtra(KEY_URL) ?: getString(R.string.SUWIKI_WEB)

        webView.apply {
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            settings.supportZoom()
            settings.displayZoomControls = false
            settings.builtInZoomControls = true
            settings.javaScriptEnabled = true
        }
        webView.loadUrl(startUrl)

        val closeButton: ImageView = findViewById(R.id.btn_close_webview)
        closeButton.setOnClickListener { finish() }
    }

    inner class WebChromeClient: android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
        }
    }

    inner class WebViewClient: android.webkit.WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.hide()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else backKeyManager.onBackPressed()
    }
}