package com.suwiki.feature.timetable.timetablelist

import androidx.lifecycle.ViewModel
import com.suwiki.feature.timetable.timetablelist.TimetableListSideEffect
import com.suwiki.feature.timetable.timetablelist.TimetableListState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TimetableListListViewModel @Inject constructor(
) : ViewModel(), ContainerHost<TimetableListState, TimetableListSideEffect> {
  override val container: Container<TimetableListState, TimetableListSideEffect> = container(TimetableListState())


}
