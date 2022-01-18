package com.kunize.uswtimetable.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.kunize.uswtimetable.dataclass.EmailCheckDto
import com.kunize.uswtimetable.dataclass.EmailData
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.dataclass.NoticeDto
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

    fun getNoticeList(completion: (ResponseState, List<NoticeDto>?) -> Unit) {
        val call: Call<List<NoticeDto>> = iRetrofit?.getNoticeList() ?: return
        var notices: List<NoticeDto>?
        call.enqueue(object: Callback<List<NoticeDto>> {
            // 응답 성공
            override fun onResponse(
                call: Call<List<NoticeDto>>,
                response: Response<List<NoticeDto>>
            ) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body()}")
                notices = response.body()
                completion(ResponseState.OK, notices)
            }
            // 응답 실패
            override fun onFailure(call: Call<List<NoticeDto>>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(ResponseState.FAIL, null)
            }
        })
    }

    fun getNotice(id: Int, completion: (ResponseState, NoticeDetailDto?) -> Unit) {
        val call: Call<NoticeDetailDto> = iRetrofit?.getNotice(id) ?: return
        call.enqueue(object: Callback<NoticeDetailDto> {
            override fun onResponse(call: Call<NoticeDetailDto>, response: Response<NoticeDetailDto>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body().toString()}")
                completion(ResponseState.OK, response.body())
            }

            override fun onFailure(call: Call<NoticeDetailDto>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(ResponseState.FAIL, null)
            }
        })
    }

    fun emailCheck(email: String, completion: (ResponseState, EmailCheckDto?) -> Unit) {
        val emailData = EmailData(email)
        val call: Call<EmailCheckDto> = iRetrofit?.sendCertNumber(emailData) ?: return
        call.enqueue(object: Callback<EmailCheckDto> {
            override fun onResponse(call: Call<EmailCheckDto>, response: Response<EmailCheckDto>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body().toString()}")
                completion(ResponseState.OK, response.body())
            }

            override fun onFailure(call: Call<EmailCheckDto>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t")
                completion(ResponseState.FAIL, null)
            }
        })
    }

    // TODO 나머지 인터페이스도 추가
}