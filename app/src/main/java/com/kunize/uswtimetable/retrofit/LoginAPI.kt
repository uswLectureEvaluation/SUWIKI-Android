package com.kunize.uswtimetable.retrofit

import android.content.Context
import com.kunize.uswtimetable.TimeTableSelPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.*

interface LoginAPI {

    fun login()
    fun logout()

    companion object {
        fun create() {

            val client = OkHttpClient.Builder()
                .authenticator(Authenticator { route, response ->
                    return@Authenticator when (response.code) {
                        400 -> {
                            response.request
                        }
                        else -> response.request
                    }
                })
        }
    }
}

class TokenAuthenticator(private val context: Context, private val refreshToken: String) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val refreshToken = TimeTableSelPref.prefs.getRefreshToken()
            GlobalScope.async(Dispatchers.Default){

            }
        }
        return response.request // TODO 지울 것
    }


}