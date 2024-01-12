package com.suwiki.feature.timetable.createtimetable

import androidx.lifecycle.ViewModel
import com.suwiki.domain.timetable.usecase.InsertTimetableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CreateTimetableViewModel @Inject constructor(
  private val insertTimetableUseCase: InsertTimetableUseCase,
) : ViewModel(), ContainerHost<CreateTimetableState, CreateTimetableSideEffect> {
  override val container: Container<CreateTimetableState, CreateTimetableSideEffect> = container(CreateTimetableState())

  fun showSemesterBottomSheet() = intent { reduce { state.copy(isSheetOpenSemester = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(isSheetOpenSemester = false) } }
  fun updateSemesterPosition(position: Int) = intent { reduce { state.copy(selectedSemesterPosition = position) } }

  @OptIn(OrbitExperimental::class)
  fun updateName(name: String) = blockingIntent { reduce { state.copy(name = name) } }

  fun insertTimetable() = intent {
    if (state.semester == null) {
      postSideEffect(CreateTimetableSideEffect.NeedSelectSemesterToast)
      return@intent
    }

    insertTimetableUseCase(
      param = InsertTimetableUseCase.Param(
        name = state.name,
        year = state.semester!!.year,
        semester = state.semester!!.semester,
      ),
    ).onSuccess {
      postSideEffect(CreateTimetableSideEffect.PopBackStack)
    }.onFailure {
      postSideEffect(CreateTimetableSideEffect.HandleException(it))
    }
  }

  fun popBackStack() = intent { postSideEffect(CreateTimetableSideEffect.PopBackStack) }
}
