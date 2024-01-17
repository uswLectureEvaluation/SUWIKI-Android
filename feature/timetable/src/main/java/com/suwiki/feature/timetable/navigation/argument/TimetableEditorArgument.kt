package com.suwiki.feature.timetable.navigation.argument

import com.suwiki.core.model.timetable.Cell
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import kotlinx.serialization.Serializable

@Serializable
data class TimetableEditorArgument(
  val createTime: Long = 0L,
  val name: String = "",
  val year: String = "",
  val semester: String = "",
) {
  val isEditMode = createTime != 0L
}

internal fun Timetable.toTimetableEditorArgument() = TimetableEditorArgument(
  createTime = createTime,
  name = name,
  year = year,
  semester = semester,
)
