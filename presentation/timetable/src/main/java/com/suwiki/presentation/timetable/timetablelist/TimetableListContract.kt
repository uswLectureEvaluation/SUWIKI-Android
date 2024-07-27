package com.suwiki.presentation.timetable.timetablelist

import com.suwiki.common.model.timetable.Timetable
import com.suwiki.presentation.timetable.navigation.argument.TimetableEditorArgument
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class TimetableListState(
  val timetableList: PersistentList<Timetable> = persistentListOf(),
  val showDeleteDialog: Boolean = false,
)

sealed interface TimetableListSideEffect {
  data class HandleException(val throwable: Throwable) : TimetableListSideEffect
  data class NavigateTimetableEditor(val argument: TimetableEditorArgument) : TimetableListSideEffect
  data object PopBackStack : TimetableListSideEffect
}
