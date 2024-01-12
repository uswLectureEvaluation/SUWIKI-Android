package com.suwiki.feature.timetable.createtimetable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.suwiki.feature.timetable.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDateTime

data class CreateTimetableState(
  val name: String = "",
  val isSheetOpenSemester: Boolean = false,
  val selectedSemesterPosition: Int? = null,
) {
  val semester = selectedSemesterPosition?.let { semesterList[it] }
  val buttonEnabled = name.isNotEmpty()
}

val semesterList: PersistentList<Semester>
  get() {
    val currentYear = LocalDateTime.now().year
    val semesterList = mutableListOf<Semester>()
    for (year in currentYear + 1 downTo currentYear - 2) {
      semesterList
        .run {
          add(Semester(year.toString(), "1"))
          add(Semester(year.toString(), "2"))
        }
    }

    return semesterList.toPersistentList()
  }

data class Semester(
  val year: String,
  val semester: String,
) {
  fun toText(context: Context) = context.getString(R.string.create_timetable_screen_semester, year, semester)

  @Composable
  fun toText() = stringResource(id = R.string.create_timetable_screen_semester, year, semester)
}

sealed interface CreateTimetableSideEffect {
  data object PopBackStack : CreateTimetableSideEffect
  data object NeedSelectSemesterToast : CreateTimetableSideEffect
  data class HandleException(val throwable: Throwable) : CreateTimetableSideEffect
}
