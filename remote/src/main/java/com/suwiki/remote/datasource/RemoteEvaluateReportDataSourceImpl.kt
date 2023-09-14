package com.suwiki.remote.datasource

import com.suwiki.data.datasource.remote.RemoteEvaluateReportDataSource
import com.suwiki.model.Result
import com.suwiki.remote.api.EvaluateApi
import com.suwiki.remote.request.evaluation.ReportLectureRequest
import com.suwiki.remote.toResult
import javax.inject.Inject

class RemoteEvaluateReportDataSourceImpl @Inject constructor(
    private val evaluateApi: EvaluateApi,
) : RemoteEvaluateReportDataSource {

    override suspend fun reportLecture(evaluateIdx: Long, content: String): Result<Unit> {
        return evaluateApi.reportLecture(
            ReportLectureRequest(
                evaluateIdx = evaluateIdx,
                content = content,
            ),
        ).toResult()
    }
}
