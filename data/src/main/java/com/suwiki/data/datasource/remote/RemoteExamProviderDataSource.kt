package com.suwiki.data.datasource.remote

import com.suwiki.core.model.LectureDetailExamData
import com.suwiki.core.model.Result

interface RemoteExamProviderDataSource {
    suspend fun buyExam(lectureId: Long): com.suwiki.core.model.Result<Unit>

    suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): com.suwiki.core.model.Result<com.suwiki.core.model.LectureDetailExamData>
}
