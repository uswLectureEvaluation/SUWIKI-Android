package com.kunize.uswtimetable.ui.repository.lecture_info

import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import com.kunize.uswtimetable.retrofit.IRetrofit
import retrofit2.Response

class LectureInfoRemoteDataSource(private val apiService: IRetrofit): LectureInfoDataSource {

    override suspend fun getLectureDetailInfoDataSource(lectureId: Long): Response<LectureDetailInfoDto> {
        return apiService.getLectureDetailInfo(lectureId)
    }
}