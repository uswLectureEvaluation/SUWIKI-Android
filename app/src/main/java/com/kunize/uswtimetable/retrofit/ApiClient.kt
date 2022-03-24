package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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
                .client(getOkHttpClient(TokenAuthenticator()))
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
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }
        return retrofitClientWithNoToken!!
    }

    private fun getOkHttpClient(
        authenticator: TokenAuthenticator?
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

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor)
        authenticator?.apply {
            client
                .addInterceptor(AuthenticationInterceptor())
                .authenticator(this)
        }

        return client.build()
    }

    class AuthenticationInterceptor : Interceptor {

        private val accessToken = try {
            TimeTableSelPref.prefs.getAccessToken()
        } catch (e: Exception) {
            Log.d(TAG, "AuthenticationInterceptor - getAccessToken() returns null")
            ""
        }

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("AccessToken", accessToken).build()
            return chain.proceed(request)
        }
    }

    class TokenAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request {
            val updatedToken = getUpdatedToken()
            return response.request.newBuilder().header("AccessToken", updatedToken).build()
        }

        private fun getUpdatedToken(): String {
            val requestParams = HashMap<String, String>()
            val authTokenResponse = getClientWithNoToken().create(IRetrofit::class.java).requestRefresh(requestParams).execute().body()!!
            Log.d(TAG, "TokenAuthenticator - getUpdatedToken() called / $authTokenResponse")
            TimeTableSelPref.prefs.saveRefreshToken(authTokenResponse.refreshToken)
            TimeTableSelPref.prefs.saveAccessToken(authTokenResponse.accessToken)
            return authTokenResponse.accessToken
        }
    }
}
