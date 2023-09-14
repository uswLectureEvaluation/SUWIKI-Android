package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteExamProviderDataSource
import com.suwiki.model.LectureDetailExamData
import com.suwiki.model.Result
import com.suwiki.remote.api.ExamApi
import com.suwiki.remote.response.exam.toModel
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteExamProviderDataSourceImpl @Inject constructor(
    private val examApi: ExamApi,
) : RemoteExamProviderDataSource {

    override suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailExamData> {
        return examApi.getLectureDetailExam(lectureId = lectureId, page = page)
            .toResult().map { it.toModel() }
    }

    override suspend fun buyExam(lectureId: Long): Result<Unit> {
        return examApi.buyExam(lectureId).toResult()
    }
}
