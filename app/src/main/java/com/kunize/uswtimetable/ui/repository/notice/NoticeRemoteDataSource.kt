package com.kunize.uswtimetable.ui.repository.notice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRemoteDataSource: NoticeDataSource {

    private val noticeResponse = MutableLiveData<List<NoticeDto>>()
    private val retrofit: IRetrofit by lazy { ApiClient.getClient().create(IRetrofit::class.java) }

    override fun getNotices(page: Int?): LiveData<List<NoticeDto>> {
        retrofit.getNoticeList(page).enqueue(object: Callback<List<NoticeDto>> {
            override fun onResponse(
                call: Call<List<NoticeDto>>,
                response: Response<List<NoticeDto>>
            ) {
                if (response.isSuccessful.not()) {
                    Log.e(TAG, "[${response.code()}] getNoticeList failed")
                    when (response.code()) {
                        502 -> {
                            Log.d(TAG, "NoticeRemoteDataSource - 502 에러 발생")
                        }
                    }
                }
                noticeResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<NoticeDto>>, t: Throwable) {
                Log.e(TAG, "getNoticeList failed: ${t.message}")
            }
        })
        return noticeResponse
    }
}