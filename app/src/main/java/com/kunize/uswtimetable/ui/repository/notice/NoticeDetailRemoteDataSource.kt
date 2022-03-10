package com.kunize.uswtimetable.ui.repository.notice

import android.os.Build
import android.util.Log
import com.kunize.uswtimetable.dataclass.NoticeDetailDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.Constants.TAG
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class NoticeDetailRemoteDataSource(private val iRetrofit: IRetrofit): NoticeDetailDataSource {
    override suspend fun getNotice(id: Long): NoticeDetailDto {
        Log.d(TAG, "NoticeDetailRemoteDataSource - getNotice() called")
        return try {
            Log.d(
                TAG,
                "NoticeDetailRemoteDataSource - getNotice(${iRetrofit.getNotice(id)}) called"
            )
            iRetrofit.getNotice(id)
        } catch (e: IllegalStateException) {
            Log.d(TAG, "NoticeDetailRemoteDataSource - getNotice() call Failed!!")
            NoticeDetailDto(id, "공지를 받아오지 못함", "2022.03.10", "서버가 준비되지 않았습니다.")
        } catch (e: Exception) {
            Log.d(TAG, "NoticeDetailRemoteDataSource - getNotice() called Failed!! / error: $e")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NoticeDetailDto(id, "공지를 받아오지 못함", "${LocalDateTime.now()}", "공지를 받아오는데 실패했습니다.")
            } else {
                val now = System.currentTimeMillis()
                val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(now)
                NoticeDetailDto(id, "공지를 받아오지 못함", simpleDateFormat, "공지를 받아오는데 실패했습니다.")
            }
        }
    }
}