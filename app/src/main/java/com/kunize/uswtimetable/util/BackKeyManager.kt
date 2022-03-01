package com.kunize.uswtimetable.util

import android.app.Activity
import android.widget.Toast

class BackKeyManager(private val activity: Activity) {
    private var toast : Toast? = null
    private var backKeyPressedTime = 0L

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
        } else {
            activity.finish()
            toast?.cancel()
        }
    }

    fun onBackPressed(msg: String) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide(msg)
        } else {
            activity.finish()
            toast?.cancel()
        }
    }

    fun onBackPressed(second: Double) {
        if (System.currentTimeMillis() > backKeyPressedTime + (second * 1000)) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
        } else {
            activity.finish()
            toast?.cancel()
        }
    }

    fun onBackPressed(msg: String, second: Double) {
        if (System.currentTimeMillis() > backKeyPressedTime + (second * 1000)) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide(msg)
        } else {
            activity.finish()
            toast?.cancel()
        }
    }

    private fun showGuide() {
        toast = Toast.makeText(activity, "뒤로 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun showGuide(msg: String) {
        toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }
}