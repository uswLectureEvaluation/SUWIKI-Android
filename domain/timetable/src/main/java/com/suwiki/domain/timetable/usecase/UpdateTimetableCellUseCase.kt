package com.suwiki.domain.timetable.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.domain.timetable.repository.TimetableRepository
import javax.inject.Inject

class UpdateTimetableCellUseCase @Inject constructor(
  private val timetableRepository: TimetableRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    timetableRepository.updateTimetableCell(oldCell = param.oldCell ,cellList = param.cellList)
  }

  data class Param(
    val oldCell: TimetableCell,
    val cellList: List<TimetableCell>,
  )
}
