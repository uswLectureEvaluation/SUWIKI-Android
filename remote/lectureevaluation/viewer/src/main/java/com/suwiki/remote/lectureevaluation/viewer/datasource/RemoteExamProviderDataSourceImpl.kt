package com.suwiki.remote.lectureevaluation.viewer.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteExamProviderDataSource
import com.suwiki.remote.lectureevaluation.viewer.api.ExamViewerApi
import com.suwiki.remote.lectureevaluation.viewer.response.exam.toModel
import com.suwiki.core.model.LectureDetailExamData
import com.suwiki.core.model.Result
import javax.inject.Inject

class RemoteExamProviderDataSourceImpl @Inject constructor(
    private val examApi: ExamViewerApi,
) : RemoteExamProviderDataSource {

    override suspend fun getLectureDetailExam(
        lectureId: Long,
        page: Int,
    ): com.suwiki.core.model.Result<com.suwiki.core.model.LectureDetailExamData> {
        return examApi.getLectureDetailExam(lectureId = lectureId, page = page)
            .toResult().map { it.toModel() }
    }

    override suspend fun buyExam(lectureId: Long): com.suwiki.core.model.Result<Unit> {
        return examApi.buyExam(lectureId).toResult()
    }
}