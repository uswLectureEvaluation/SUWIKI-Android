package com.suwiki.feature.timetable.addcell

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.exception.TimetableCellOverlapException
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.decodeFromUri
import com.suwiki.domain.timetable.usecase.InsertTimetableCellUseCase
import com.suwiki.domain.timetable.usecase.InsertTimetableUseCase
import com.suwiki.feature.timetable.navigation.TimetableRoute
import com.suwiki.feature.timetable.openlecture.OpenLectureSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddCellViewModel @Inject constructor(
  private val insertTimetableCellUseCase: InsertTimetableCellUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<AddCellState, AddCellSideEffect> {
  private val openLectureArgument = savedStateHandle.get<String>(TimetableRoute.ADD_CELL_ARGUMENT)!!
  private val openLecture = Json.decodeFromUri<OpenLecture>(openLectureArgument)

  override val container: Container<AddCellState, AddCellSideEffect> = container(
    AddCellState(
      lectureName = openLecture.name,
      professorName = openLecture.professorName,
      cellStateList = openLecture.originalCellList.map { it.toState() },
    )
  )

  fun insertTimetable() = intent {
    val timetableCellList = if(state.cellStateList.isEmpty()) {
      listOf(
        TimetableCell(
          name = state.lectureName,
          professor = state.professorName,
          location = "",
          day = TimetableDay.E_LEARNING,
          startPeriod = 0,
          endPeriod = 0,
          color = state.selectedTimetableCellColor,
        )
      )
    } else {
      state.cellStateList.map { cell ->
        TimetableCell(
          name = state.lectureName,
          professor = state.professorName,
          location = cell.location,
          day = cell.day,
          startPeriod = cell.startPeriod.toInt(),
          endPeriod = cell.endPeriod.toInt(),
          color = state.selectedTimetableCellColor,
        )
      }
    }

    insertTimetableCellUseCase(timetableCellList)
      .onSuccess {
        postSideEffect(AddCellSideEffect.ShowSuccessAddCellToast)
      }
      .onFailure {
        if (it is TimetableCellOverlapException) postSideEffect(AddCellSideEffect.ShowOverlapCellToast(it.message))
        else postSideEffect(AddCellSideEffect.HandleException(it))
      }
  }

  fun popBackStack() = intent { postSideEffect(AddCellSideEffect.PopBackStack) }
}
