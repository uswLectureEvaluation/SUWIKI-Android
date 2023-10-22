package com.suwiki.domain.repository

import com.suwiki.domain.model.LectureMain
import com.suwiki.domain.model.Result

interface SearchResultRepository {
  suspend fun getSearchResultList(
    name: String,
    option: String,
    page: Int,
    majorType: String,
  ): Result<List<LectureMain?>>

  suspend fun getLectureMainList(
    option: String,
    page: Int,
    majorType: String = "",
  ): Result<List<LectureMain?>>
}
