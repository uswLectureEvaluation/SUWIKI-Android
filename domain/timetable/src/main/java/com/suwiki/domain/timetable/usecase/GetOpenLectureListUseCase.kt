package com.suwiki.domain.timetable.usecase

import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.domain.timetable.repository.OpenLectureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOpenLectureListUseCase @Inject constructor(
  private val openLectureRepository: OpenLectureRepository,
) {
  suspend operator fun invoke(param: Param): List<OpenLecture> = with(param) {
    openLectureRepository.getOpenLectureList(
      cursorId = cursorId,
      size = size,
      keyword = keyword,
      major = major,
      grade = grade,
    )
  }

  data class Param(
    val cursorId: Long,
    val size: Long,
    val keyword: String?,
    val major: String?,
    val grade: Int?,
  )
}
