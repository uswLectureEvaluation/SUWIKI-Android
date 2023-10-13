package com.suwiki.lectureevaluation.report.datasource

import com.suwiki.core.network.retrofit.toResult
import com.suwiki.data.datasource.remote.RemoteLectureReportDataSource
import com.suwiki.lectureevaluation.report.api.LectureReportApi
import com.suwiki.lectureevaluation.report.request.ReportLectureRequest
import com.suwiki.model.Result
import javax.inject.Inject

class RemoteLectureReportDataSourceImpl @Inject constructor(
    private val lectureApi: LectureReportApi,
) : RemoteLectureReportDataSource {

    override suspend fun reportLecture(evaluateIdx: Long, content: String): Result<Unit> {
        return lectureApi.reportLecture(
            ReportLectureRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}