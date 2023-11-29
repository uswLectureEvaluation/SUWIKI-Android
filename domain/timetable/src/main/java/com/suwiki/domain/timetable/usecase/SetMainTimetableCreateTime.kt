package com.suwiki.domain.timetable.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.timetable.repository.TimetableRepository
import javax.inject.Inject

class SetMainTimetableCreateTime @Inject constructor(
  private val timetableRepository: TimetableRepository,
) {
  suspend operator fun invoke(createTime: Long): Result<Unit> = runCatchingIgnoreCancelled {
    timetableRepository.setMainTimetableCreateTime(createTime)
  }
}
