package com.suwiki.domain.repository

import com.suwiki.domain.model.LectureMain
import com.suwiki.domain.model.Result

interface EvaluationRepository {
    suspend fun getLectureMainList(
        option: String,
        majorType: String,
    ): Result<List<LectureMain?>>
}
