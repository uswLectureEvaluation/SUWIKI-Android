package com.suwiki.domain.timetable.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.domain.timetable.repository.TimetableRepository
import javax.inject.Inject

class UpdateTimetableUseCase @Inject constructor(
  private val timetableRepository: TimetableRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    with(param) {
      timetableRepository.updateTimetable(
        createTime = createTime,
        year = year,
        semester = semester,
        name = name,
      )
    }
  }

  data class Param(
    val createTime: Long,
    val year: String,
    val semester: String,
    val name: String,
  )
}
