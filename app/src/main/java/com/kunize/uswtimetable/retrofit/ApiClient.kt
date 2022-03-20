package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.kunize.uswtimetable.ui.login.User
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
                    Log.d(TAG, "CONNECTION INFO -> $message")
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
        authenticator?.apply { client.authenticator(this) }

        return client.build()
    }

    class AuthenticationInterceptor : Interceptor {

        private val accessToken = User.user?.token

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .header("AccessToken", accessToken?:"").build()
            return chain.proceed(request)
        }
    }

    class AppAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {

            return when (response.code) {
                400 -> {
                    // TODO 로그인 에러
                    response.request
                }
                402 -> {
                    Log.e(TAG, "402에러: ${response.message}")
                    response.request
                }
                else -> {
                    Log.e(TAG, "${response.code}에러: ${response.message}")
                    response.request
                }
            }
        }
    }
}

