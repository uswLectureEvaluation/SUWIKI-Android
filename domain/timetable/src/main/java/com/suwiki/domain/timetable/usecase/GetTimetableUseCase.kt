package com.suwiki.domain.timetable.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.domain.timetable.repository.TimetableRepository
import javax.inject.Inject

class GetTimetableUseCase @Inject constructor(
  private val timetableRepository: TimetableRepository,
) {
  suspend operator fun invoke(createTime: Long): Result<Timetable> = runCatchingIgnoreCancelled {
    timetableRepository.getTimetable(createTime)
  }
}
