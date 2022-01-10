package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.isJsonArray
import com.kunize.uswtimetable.util.isJsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        /* 로깅 인터셉터 추가 */
        /*val client = OkHttpClient.Builder() // OKHttp 클라이언트
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(TAG, "RetrofitClient - log() called / message: $message")
            when {
                message.isJsonObject() ->
                    Log.d(TAG, JSONObject(message).toString(4))
                message.isJsonArray() ->
                    Log.d(TAG, JSONObject(message).toString(4))
                else ->
                    try {
                        Log.d(TAG, JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d(TAG, message)
                    }
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor) // OKHttp 클라이언트에 로깅 인터셉트 추가*/

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy/MM/dd a hh시 mm분") // 2021/10/23 오후 11시 37분
            .create()

        if (retrofitClient == null) {
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.client(client.build()) // 위에서 설정한 OKHttp 클라이언트
                .build()
        }
        return retrofitClient
    }
}