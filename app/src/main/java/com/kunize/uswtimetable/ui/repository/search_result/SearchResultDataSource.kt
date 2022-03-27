package com.kunize.uswtimetable.ui.repository.search_result

import com.kunize.uswtimetable.dataclass.LectureMainDto
import retrofit2.Response

interface SearchResultDataSource {
    suspend fun getSearchResultDataSource(name: String, option: String, page: Int): Response<LectureMainDto>
}