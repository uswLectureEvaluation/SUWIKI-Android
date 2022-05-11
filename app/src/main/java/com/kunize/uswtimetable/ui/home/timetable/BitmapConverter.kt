package com.kunize.uswtimetable.ui.home.timetable

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

object BitmapConverter {
    fun bitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val byte = baos.toByteArray()
        return Base64.encodeToString(byte, Base64.DEFAULT)
    }

    fun stringToBitmap(encodedString: String): Bitmap? {
        return try {
            val byte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(byte, 0, byte.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}