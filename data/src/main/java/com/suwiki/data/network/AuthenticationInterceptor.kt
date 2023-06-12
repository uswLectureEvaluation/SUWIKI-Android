package com.suwiki.data.network

import android.util.Log
import com.kunize.uswtimetable.util.Constants
import com.kunize.uswtimetable.util.SuwikiApplication
import okhttp3.Interceptor

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val accessToken = SuwikiApplication.encryptedPrefs.getAccessToken() ?: ""
        val request = chain.request().newBuilder()
            .addHeader(TokenAuthenticator.AUTH_HEADER, accessToken).build()
        Log.d(
            Constants.TAG,
            "AuthenticationInterceptor - intercept() called / request header: ${request.headers}",
        )
        return chain.proceed(request)
    }
}
