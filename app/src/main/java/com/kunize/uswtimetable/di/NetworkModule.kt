package com.kunize.uswtimetable.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.kunize.uswtimetable.data.remote.AuthenticationInterceptor
import com.kunize.uswtimetable.data.remote.TokenAuthenticator
import com.kunize.uswtimetable.domain.repository.AuthRepository
import com.kunize.uswtimetable.domain.repository.LogoutRepository
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.suwiki.data.network.ApiService
import com.suwiki.data.network.ApiService.Companion.API.BASE_URL
import com.suwiki.data.network.ResultCallAdapterFactory
import com.suwiki.domain.di.AuthApiService
import com.suwiki.domain.di.AuthOkHttpClient
import com.suwiki.domain.di.AuthenticatorInterceptor
import com.suwiki.domain.di.LoggingInterceptor
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.di.OtherOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @AuthApiService
    @Provides
    fun provideAuthApiService(
        @AuthOkHttpClient client: OkHttpClient,
        apiResponseCallAdapterFactory: ApiResponseCallAdapterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory,
    ): ApiService {
        return provideBaseApiService(
            client,
            apiResponseCallAdapterFactory,
            scalarsConverterFactory,
            gsonConverterFactory,
        )
    }

    @OtherApiService
    @Provides
    fun provideApiService(
        @OtherOkHttpClient client: OkHttpClient,
        apiResponseCallAdapterFactory: ApiResponseCallAdapterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory,
    ): ApiService {
        return provideBaseApiService(
            client,
            apiResponseCallAdapterFactory,
            scalarsConverterFactory,
            gsonConverterFactory,
        )
    }

    private fun provideBaseApiService(
        client: OkHttpClient,
        apiResponseCallAdapterFactory: ApiResponseCallAdapterFactory,
        scalarsConverterFactory: ScalarsConverterFactory,
        gsonConverterFactory: GsonConverterFactory,
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(apiResponseCallAdapterFactory)
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .addConverterFactory(scalarsConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(ApiService::class.java)
    }

    @AuthOkHttpClient
    @Provides
    fun provideAuthOkHttpClient(
        authenticator: TokenAuthenticator,
        @AuthenticatorInterceptor authenticatorInterceptor: Interceptor,
        @LoggingInterceptor loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authenticatorInterceptor)
            .authenticator(authenticator)
            .build()
    }

    @OtherOkHttpClient
    @Provides
    fun provideOkHttpClient(
        @LoggingInterceptor loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @LoggingInterceptor
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            when {
                message.isJsonObject() ->
                    Log.d("Network", JSONObject(message).toString(4))

                message.isJsonArray() ->
                    Log.d("Network", JSONArray(message).toString(4))

                else ->
                    Log.d("Network", "CONNECTION INFO -> $message")
            }
        }.apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @AuthenticatorInterceptor
    @Provides
    fun provideAuthenticatorInterceptor(): Interceptor {
        return AuthenticationInterceptor()
    }

    @Provides
    fun provideApiResponseCallAdapter(): ApiResponseCallAdapterFactory {
        return ApiResponseCallAdapterFactory.create()
    }

    @Provides
    fun provideScalarsConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Provides
    fun provideTokenAuthenticator(
        authRepository: AuthRepository,
        logoutRepository: LogoutRepository,
    ): TokenAuthenticator {
        return TokenAuthenticator(
            authRepository,
            logoutRepository,
        )
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime::class.java,
                object : JsonDeserializer<LocalDateTime> {
                    override fun deserialize(
                        json: JsonElement?,
                        typeOfT: Type?,
                        context: JsonDeserializationContext?,
                    ): LocalDateTime {
                        return LocalDateTime.parse(
                            json?.asString,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                        )
                    }
                },
            )
            .registerTypeAdapter(
                LocalDate::class.java,
                object : JsonDeserializer<LocalDate> {
                    override fun deserialize(
                        json: JsonElement?,
                        typeOfT: Type?,
                        context: JsonDeserializationContext?,
                    ): LocalDate {
                        return LocalDate.parse(
                            json?.asString,
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                        )
                    }
                },
            )
            .registerTypeAdapter(
                LocalTime::class.java,
                object : JsonDeserializer<LocalTime> {
                    override fun deserialize(
                        json: JsonElement?,
                        typeOfT: Type?,
                        context: JsonDeserializationContext?,
                    ): LocalTime {
                        return LocalTime.parse(
                            json?.asString,
                            DateTimeFormatter.ofPattern("HH:mm:ss"),
                        )
                    }
                },
            )
            .create()
        return GsonConverterFactory.create(gson)
    }
}

private fun String?.isJsonObject(): Boolean = this?.startsWith("{") == true && this.endsWith("}")
private fun String?.isJsonArray(): Boolean = this?.startsWith("[") == true && this.endsWith("]")
