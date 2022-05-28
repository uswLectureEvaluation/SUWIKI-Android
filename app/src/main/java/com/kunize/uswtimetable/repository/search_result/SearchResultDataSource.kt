package com.kunize.uswtimetable.repository.search_result

import com.kunize.uswtimetable.data.remote.LectureMainDto
import com.kunize.uswtimetable.data.remote.MajorType
import retrofit2.Response

interface SearchResultDataSource {
    suspend fun getSearchResultDataSource(name: String, option: String, page: Int, majorType: String): Response<LectureMainDto>
}