package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.kunize.uswtimetable.util.API
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.ResponseState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    // Retrofit 인터페이스 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun getMain(completion: (ResponseState, String) -> Unit) {
        val call: Call<JsonElement> = iRetrofit?.getMain() ?: return
        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body().toString()}")
                completion(ResponseState.OK, response.body().toString())
            }

            // 응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(ResponseState.FAIL, t.toString())
            }
        })
    }

    fun getNoticeList(completion: (ResponseState, String) -> Unit) {
        val call: Call<JsonElement> = iRetrofit?.getNoticeList() ?: return
        call.enqueue(object : Callback<JsonElement> {
            // 응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body().toString()}")
                completion(ResponseState.OK, response.body().toString())
            }

            // 응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(ResponseState.FAIL, t.toString())
            }
        })
    }

    // TODO 나머지 인터페이스도 추가
}