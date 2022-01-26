package com.kunize.uswtimetable.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectionManager {
    companion object {
        private fun getConnectivityManager(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        fun isConnected(context: Context): Boolean {
            val cm = getConnectivityManager(context)
            val networkInfo = cm.activeNetworkInfo
            return networkInfo?.isConnected == true
        }
    }
}