package com.kunize.uswtimetable.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import com.kunize.uswtimetable.R
import com.kunize.uswtimetable.util.BackKeyManager
import com.kunize.uswtimetable.util.Constants.KEY_URL
import java.net.URISyntaxException

class WebviewActivity : AppCompatActivity() {
    private val webView: WebView by lazy { findViewById(R.id.webView) }
    private val backKeyManager: BackKeyManager by lazy { BackKeyManager(this) }
    private val progressBar: ContentLoadingProgressBar by lazy { findViewById(R.id.progress_bar) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val startUrl = if (intent.action == Intent.ACTION_VIEW) {
            intent.data?.getQueryParameter(KEY_URL)
        } else {
            intent.getStringExtra(KEY_URL)
        } ?: getString(R.string.SUWIKI_WEB)

        webView.apply {
            webViewClient = WebViewClient(this@WebviewActivity)
            webChromeClient = WebChromeClient()
            settings.supportZoom()
            settings.displayZoomControls = false
            settings.builtInZoomControls = true
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true;
        }
        webView.loadUrl(startUrl)

        val closeButton: ImageView = findViewById(R.id.btn_close_webview)
        closeButton.setOnClickListener { finish() }
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            progressBar.progress = newProgress
        }
    }

    inner class WebViewClient(private val context: Context) : android.webkit.WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.hide()
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            val intent = parse(url)
            return if (isIntent(url)) {
                if (isExistInfo(intent) or isExistPackage(intent))
                    start(intent)
                else
                    gotoMarket(intent)
            } else if (isMarket(intent.toString()))
                start(intent)
            else {
                webView.loadUrl(url)
                false
            }
        }

        private fun isIntent(url: String?) = url?.matches(Regex("^intent:?\\w*://\\S+$")) ?: false

        private fun isMarket(url: String?) = url?.matches(Regex("^market://\\S+$")) ?: false

        private fun isExistInfo(intent: Intent?): Boolean {
            return try {
                intent != null && context.packageManager.getPackageInfo(
                    intent.`package`!!,
                    PackageManager.GET_ACTIVITIES
                ) != null
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }

        }

        private fun isExistPackage(intent: Intent?): Boolean =
            intent != null && context.packageManager.getLaunchIntentForPackage(intent.`package`!!) != null

        private fun parse(url: String): Intent? {
            return try {
                Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            } catch (e: URISyntaxException) {
                null
            }
        }

        private fun start(intent: Intent?): Boolean {
            intent?.let { context.startActivity(it) }
            return true
        }

        private fun gotoMarket(intent: Intent?): Boolean {
            intent?.let {
                start(Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("market://details?id=${it.`package`}")
                })
            }
            return true
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else backKeyManager.onBackPressed()
    }
}