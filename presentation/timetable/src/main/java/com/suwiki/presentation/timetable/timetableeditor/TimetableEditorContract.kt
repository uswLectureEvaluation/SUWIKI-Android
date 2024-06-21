package com.suwiki.presentation.timetable.timetableeditor

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.suwiki.presentation.timetable.R
import com.suwiki.presentation.timetable.navigation.argument.TimetableEditorArgument
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDateTime

data class TimetableEditorState(
  val name: String = "",
  val isSheetOpenSemester: Boolean = false,
  val selectedSemesterPosition: Int? = null,
) {
  val semester = selectedSemesterPosition?.let { semesterList.getOrNull(it) }
  val buttonEnabled = name.isNotEmpty()
}

internal fun TimetableEditorArgument.toState() = TimetableEditorState(
  name = name,
  selectedSemesterPosition = semesterList.indexOf(Semester(year, semester)),
)

val semesterList: PersistentList<Semester> = run {
  val currentYear = LocalDateTime.now().year
  val semesterList = mutableListOf<Semester>()
  for (year in currentYear downTo currentYear - 3) {
    semesterList
      .run {
        add(Semester(year.toString(), "1"))
        add(Semester(year.toString(), "2"))
      }
  }

  semesterList.toPersistentList()
}

data class Semester(
  val year: String,
  val semester: String,
) {
  fun toText(context: Context) = context.getString(R.string.create_timetable_screen_semester, year, semester)

  @Composable
  fun toText() = stringResource(id = R.string.create_timetable_screen_semester, year, semester)
}

sealed interface TimetableEditorSideEffect {
  data object PopBackStack : TimetableEditorSideEffect
  data object NeedSelectSemesterToast : TimetableEditorSideEffect
  data class HandleException(val throwable: Throwable) : TimetableEditorSideEffect
}
