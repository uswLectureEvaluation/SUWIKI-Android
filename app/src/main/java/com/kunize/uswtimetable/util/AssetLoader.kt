package com.kunize.uswtimetable.util

import android.content.Context

class AssetLoader(private val context: Context) {

    fun getJsonString(fileName: String): String? {
        return kotlin.runCatching {
            loadAssets(fileName)
        }.getOrNull()
    }

    private fun loadAssets(fileName: String): String {
        return context.assets.open(fileName).use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes)
        }
    }
}