package com.suwiki.domain.timetable.usecase

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.domain.timetable.repository.TimetableRepository
import javax.inject.Inject

class GetMainTimetableUseCase @Inject constructor(
  private val timetableRepository: TimetableRepository,
) {
  suspend operator fun invoke(): Result<Timetable?> = runCatchingIgnoreCancelled {
    with(timetableRepository) {
      val createTime = getMainTimetableCreateTime() ?: return@with null
      getTimetable(createTime)
    }
  }
}
