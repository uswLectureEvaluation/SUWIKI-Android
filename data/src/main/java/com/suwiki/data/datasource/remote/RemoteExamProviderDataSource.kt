package com.suwiki.data.datasource.remote

import com.suwiki.core.model.LectureDetailExamData

interface RemoteExamProviderDataSource {
    suspend fun buyExam(lectureId: Long)

    suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): LectureDetailExamData
}
