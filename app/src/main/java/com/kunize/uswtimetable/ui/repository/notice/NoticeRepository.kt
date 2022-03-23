package com.kunize.uswtimetable.ui.repository.notice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.NoticeDto
import com.kunize.uswtimetable.ui.common.NetworkResult
import com.kunize.uswtimetable.ui.common.NetworkStatus
import com.kunize.uswtimetable.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRepository(private val noticeRemoteDataSource: NoticeRemoteDataSource) {
    private val result = MutableLiveData<NetworkResult<List<NoticeDto>>>()

    suspend fun getNotices() {
        val remoteData = noticeRemoteDataSource.getNotices()
        remoteData.enqueue(object : Callback<List<NoticeDto>> {
            override fun onResponse(
                call: Call<List<NoticeDto>>,
                response: Response<List<NoticeDto>>
            ) {
                result.value = if (response.isSuccessful) {
                    NetworkResult(
                        NetworkStatus.SUCCESS,
                        response.code(),
                        response.message(),
                        response.body() ?: listOf()
                    )
                } else {
                    if (response.body() == null) {
                        NetworkResult(
                            NetworkStatus.EMPTY,
                            response.code(),
                            response.message(),
                            listOf()
                        )
                    } else {
                        NetworkResult(
                            NetworkStatus.FAIL,
                            response.code(),
                            response.message(),
                            response.body()!!
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<NoticeDto>>, t: Throwable) {
                Log.e(Constants.TAG, "NoticeRepository - onFailure() called / ${t.message}")
                result.value = NetworkResult(
                    NetworkStatus.FAIL,
                    0, "error", listOf()
                )
            }
        })
    }

    fun getResult() = result
}