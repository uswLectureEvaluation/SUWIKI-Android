package com.kunize.uswtimetable.ui.repository.lecture_info

import com.kunize.uswtimetable.dataclass.LectureDetailInfoDto
import retrofit2.Response

interface LectureInfoDataSource {
    suspend fun getLectureDetailInfoDataSource(lectureId: Long): Response<LectureDetailInfoDto>
}