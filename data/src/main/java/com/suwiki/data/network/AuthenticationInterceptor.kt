package com.suwiki.data.network

import android.util.Log
import com.suwiki.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor

class AuthenticationInterceptor(
    private val authRepository: AuthRepository,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        return runBlocking {
            val accessToken = authRepository.accessToken.first()
            val request = chain.request().newBuilder()
                .addHeader(TokenAuthenticator.AUTH_HEADER, accessToken).build()
            Log.d(
                "Network",
                "AuthenticationInterceptor - intercept() called / request header: ${request.headers}",
            )
            return@runBlocking chain.proceed(request)
        }
    }
}
