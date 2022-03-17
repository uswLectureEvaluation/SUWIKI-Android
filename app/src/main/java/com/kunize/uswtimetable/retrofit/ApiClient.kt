package com.kunize.uswtimetable.retrofit

import com.kunize.uswtimetable.ui.login.User
import com.kunize.uswtimetable.util.API.BASE_URL
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object ApiClient {

    fun getClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient(AppInterceptor(), AppAuthenticator()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getClientWithNoToken(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(interceptor: AppInterceptor, authenticator: AppAuthenticator): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .authenticator(authenticator)
            .build()

    class AppInterceptor : Interceptor {
        private val token = User.user?.token ?: ""

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("token", token)
                .build()
            proceed(newRequest)
        }
    }

    class AppAuthenticator: Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            if (response.code == 401) {
                // TODO Refresh Token 얻어서 서버에 전송
            }
            return response.request
        }
    }
}