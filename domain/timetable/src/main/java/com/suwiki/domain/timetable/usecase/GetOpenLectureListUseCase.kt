package com.suwiki.domain.timetable.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.domain.timetable.repository.OpenLectureRepository
import javax.inject.Inject

class GetOpenLectureListUseCase @Inject constructor(
  private val openLectureRepository: OpenLectureRepository,
) {
  suspend operator fun invoke(): Result<List<OpenLecture>> = runCatchingIgnoreCancelled {
    openLectureRepository.getOpenLectureList()
  }
}
