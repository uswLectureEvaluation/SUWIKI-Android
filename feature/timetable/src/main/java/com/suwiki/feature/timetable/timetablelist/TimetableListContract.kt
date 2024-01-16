package com.suwiki.feature.timetable.timetablelist

import com.suwiki.core.model.timetable.Timetable
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.feature.timetable.navigation.CellEditorArgument
import com.suwiki.feature.timetable.timetable.component.timetable.cell.TimetableCellType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class TimetableListState(
  val timetableList: PersistentList<Timetable> = persistentListOf(),
)

sealed interface TimetableListSideEffect {
  data class HandleException(val throwable: Throwable) : TimetableListSideEffect
}
