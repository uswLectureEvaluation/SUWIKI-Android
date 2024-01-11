package com.suwiki.feature.timetable

import androidx.lifecycle.ViewModel
import com.suwiki.domain.timetable.usecase.GetMainTimetableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
  private val getMainTimetableUseCase: GetMainTimetableUseCase,
) : ViewModel(), ContainerHost<TimetableState, TimetableSideEffect> {
  override val container: Container<TimetableState, TimetableSideEffect> = container(TimetableState())

  fun getMainTimetable() = intent {
    getMainTimetableUseCase()
      .onSuccess { timetable ->
        reduce { state.copy(timetable = timetable) }
      }
      .onFailure {
        postSideEffect(TimetableSideEffect.HandleException(it))
      }
  }
}
