package com.suwiki.data.lectureevaluation.viewer.datasource

import com.suwiki.core.model.lectureevaluation.LectureDetailExamData

interface RemoteExamProviderDataSource {
    suspend fun buyExam(lectureId: Long)

    suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): LectureDetailExamData
}
