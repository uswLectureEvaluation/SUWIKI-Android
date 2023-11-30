package com.suwiki.domain.timetable.usecase

import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.domain.timetable.repository.OpenLectureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOpenLectureListUseCase @Inject constructor(
  private val openLectureRepository: OpenLectureRepository,
) {
  suspend operator fun invoke(): Flow<List<OpenLecture>> {
    return openLectureRepository.getOpenLectureList()
  }
}
