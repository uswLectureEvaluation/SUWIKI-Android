package com.suwiki.data.datasource.remote

import com.suwiki.model.Evaluation
import com.suwiki.model.LectureDetailEvaluationData
import com.suwiki.model.Result

interface RemoteEvaluateProviderDataSource {

    suspend fun getEvaluatePosts(page: Int): Result<List<Evaluation>>

    suspend fun getLectureDetailEvaluation(
        lectureId: Long,
        page: Int,
    ): Result<LectureDetailEvaluationData>
}
