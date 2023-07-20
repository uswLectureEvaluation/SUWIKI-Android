package com.suwiki.data.datasource

import com.suwiki.data.network.dto.LectureMainDto
import com.suwiki.domain.model.Result

interface SearchResultDataSource : EvaluationDataSource {
    suspend fun getSearchResultDataSource(
        name: String,
        option: String,
        page: Int,
        majorType: String,
    ): Result<MutableList<LectureMainDto?>>
}
