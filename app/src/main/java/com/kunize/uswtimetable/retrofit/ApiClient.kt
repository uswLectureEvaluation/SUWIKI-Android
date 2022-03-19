package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.util.API.BASE_URL
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.isJsonArray
import com.kunize.uswtimetable.util.isJsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofitClient: Retrofit? = null
    private var retrofitClientWithNoToken: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(AppAuthenticator()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient!!
    }

    fun getClientWithNoToken(): Retrofit {
        if (retrofitClientWithNoToken == null) {
            retrofitClientWithNoToken = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(null))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClientWithNoToken!!
    }

    private fun getOkHttpClient(
        authenticator: AppAuthenticator?
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d(TAG, JSONObject(message).toString(4))
                message.isJsonArray() ->
                    Log.d(TAG, JSONArray(message).toString(4))
                else ->
                    try {
                        Log.d(TAG, JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d(TAG, "CONNECTION INFO -> $message")
                    }
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val client = if (authenticator == null) {
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder()
                .authenticator(authenticator)
                .addInterceptor(loggingInterceptor)
                .build()
        }
        return client
    }

    class AppAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            return when (response.code) {
                400 -> {
                    // TODO 로그인 에러
                    response.request
                }
                401 -> {
                    // TODO Refresh Token 얻어서 서버에 전송
                    val refreshToken = TimeTableSelPref.prefs.getRefreshToken()
                    refreshToken?.let {
                        response.request.newBuilder().header("Authorization", it).build()
                    }
                }
                403 -> {
                    Log.e(TAG, "403에러: ${response.message}")
                    response.request
                }
                else -> response.request
            }
        }
    }
}

