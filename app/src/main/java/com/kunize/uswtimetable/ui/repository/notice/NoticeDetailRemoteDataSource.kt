package com.kunize.uswtimetable.ui.repository.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeDetailRemoteDataSource : NoticeDetailDataSource {

    private val noticeResponse = MutableLiveData<NoticeDetailDto>()
    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }

    override suspend fun getNotice(id: Long): LiveData<NoticeDetailDto> {

        retrofit.getNotice(id).enqueue(object : Callback<NoticeDetailDto> {
            override fun onResponse(
                call: Call<NoticeDetailDto>,
                response: Response<NoticeDetailDto>
            ) {
                if (response.isSuccessful.not()) {
                    Log.e(TAG, "NoticeDetailRemoteDataSource - onResponse() call failed: ${response.code()}")
                }
                noticeResponse.value = response.body()
            }
            override fun onFailure(call: Call<NoticeDetailDto>, t: Throwable) {
                Log.e(TAG, "NoticeDetailRemoteDataSource - onFailure() call failed: ${t.message}")
            }
        })

        return noticeResponse
    }
}