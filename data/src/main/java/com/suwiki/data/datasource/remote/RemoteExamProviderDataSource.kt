package com.suwiki.data.datasource.remote

import com.suwiki.model.LectureDetailExamData
import com.suwiki.model.Result

interface RemoteExamProviderDataSource {
    suspend fun buyExam(lectureId: Long): Result<Unit>

    suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailExamData>
}
