package com.suwiki.data.datasource

import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.domain.model.Result

interface EvaluationDataSource {
  suspend fun getEvaluationDataSource(
    option: String,
    page: Int = 1,
    majorType: String,
  ): Result<MutableList<LectureMainDto?>>
}
