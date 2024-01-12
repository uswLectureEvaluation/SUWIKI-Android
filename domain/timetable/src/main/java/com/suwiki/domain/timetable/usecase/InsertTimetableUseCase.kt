package com.suwiki.domain.timetable.usecase

import com.suwiki.core.common.runCatchingIgnoreCancelled
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.domain.timetable.repository.TimetableRepository
import java.sql.Time
import javax.inject.Inject

class InsertTimetableUseCase @Inject constructor(
  private val timetableRepository: TimetableRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    val createTime = System.currentTimeMillis()
    with(timetableRepository) {
      insertTimetable(
        Timetable(
          createTime = createTime,
          year = param.year,
          semester = param.semester,
          name = param.name,
          cellList = emptyList(),
        ),
      )
      setMainTimetableCreateTime(createTime)
    }
  }

  data class Param(
    val name: String,
    val year: String,
    val semester: String,
  )
}
