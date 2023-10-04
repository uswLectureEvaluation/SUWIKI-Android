package com.suwiki.lectureevaluation.viewer.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamProviderDataSource
import com.suwiki.lectureevaluation.viewer.api.ExamViewerApi
import com.suwiki.lectureevaluation.viewer.response.exam.toModel
import com.suwiki.model.LectureDetailExamData
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteExamProviderDataSourceImpl @Inject constructor(
    private val examApi: ExamViewerApi,
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