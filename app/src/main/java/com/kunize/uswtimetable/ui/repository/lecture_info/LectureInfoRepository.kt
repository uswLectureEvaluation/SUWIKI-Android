package com.kunize.uswtimetable.ui.repository.lecture_info

import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import retrofit2.Response

class LectureInfoRepository(
    private val remoteDataSource: LectureInfoRemoteDataSource
) {
    suspend fun getLectureDetailInfo(lectureId: Long): Response<LectureDetailInfoDto> {
        return remoteDataSource.getLectureDetailInfoDataSource(lectureId)
    }
}