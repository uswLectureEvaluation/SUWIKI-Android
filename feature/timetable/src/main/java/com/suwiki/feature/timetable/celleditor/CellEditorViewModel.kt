package com.suwiki.feature.timetable.celleditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.exception.TimetableCellOverlapException
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.core.ui.extension.decodeFromUri
import com.suwiki.domain.timetable.usecase.InsertTimetableCellUseCase
import com.suwiki.feature.timetable.navigation.TimetableRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CellEditorViewModel @Inject constructor(
  private val insertTimetableCellUseCase: InsertTimetableCellUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<CellEditorState, CellEditorSideEffect> {
  private val openLectureArgument = savedStateHandle.get<String>(TimetableRoute.CELL_EDITOR_OPEN_LECTURE_ARGUMENT)!!
  private val openLecture = Json.decodeFromUri<OpenLecture>(openLectureArgument)

  override val container: Container<CellEditorState, CellEditorSideEffect> = container(
    CellEditorState(
      lectureName = openLecture.name,
      professorName = openLecture.professorName,
      cellStateList = openLecture.originalCellList.map { it.toState() }.toPersistentList(),
    ),
  )

  fun updateCellColor(color: TimetableCellColor) = intent {
    reduce { state.copy(selectedTimetableCellColor = color) }
  }

  fun addCell() = intent {
    reduce {
      state.copy(
        cellStateList = state.cellStateList.add(CellState()),
      )
    }
  }

  fun deleteCell(index: Int) = intent {
    reduce {
      state.copy(
        cellStateList = state.cellStateList.removeAt(index),
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateCellLocation(index: Int, location: String) = blockingIntent {
    val toUpdateCell = state.cellStateList[index].copy(location = location)
    reduceCell(index, toUpdateCell)
  }

  @OptIn(OrbitExperimental::class)
  fun updateCellEndPeriod(index: Int, endPeriod: String) = blockingIntent {
    val toUpdateCell = state.cellStateList[index].copy(endPeriod = endPeriod.toPeriod())
    reduceCell(index, toUpdateCell)
  }

  @OptIn(OrbitExperimental::class)
  fun updateCellStartPeriod(index: Int, startPeriod: String) = blockingIntent {
    val toUpdateCell = state.cellStateList[index].copy(startPeriod = startPeriod.toPeriod())
    reduceCell(index, toUpdateCell)
  }

  private fun String.toPeriod(): String {
    return toIntOrNull()?.let {
      when {
        it < 1 -> "1"
        it > 15 -> "15"
        else -> this
      }
    } ?: ""
  }

  fun updateCellDay(index: Int, day: TimetableDay) = intent {
    val toUpdateCell = state.cellStateList[index].copy(day = day)
    reduceCell(index, toUpdateCell)
  }

  private suspend fun SimpleSyntax<CellEditorState, CellEditorSideEffect>.reduceCell(
    index: Int,
    toUpdateCell: CellState,
  ) {
    val cellList = state.cellStateList.removeAt(index)
    reduce {
      state.copy(
        cellStateList = cellList.add(index, toUpdateCell),
      )
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateLectureName(lectureName: String) = blockingIntent {
    reduce {
      state.copy(lectureName = lectureName)
    }
  }

  @OptIn(OrbitExperimental::class)
  fun updateProfessorName(professorName: String) = blockingIntent {
    reduce {
      state.copy(professorName = professorName)
    }
  }

  fun insertTimetable() = intent {
    val sideEffect = when {
      state.lectureName.isEmpty() -> CellEditorSideEffect.ShowNeedLectureNameToast
      state.professorName.isEmpty() -> CellEditorSideEffect.ShowNeedProfessorNameToast
      state.cellStateList.any { it.location.isEmpty() } -> CellEditorSideEffect.ShowNeedLocationToast
      else -> null
    }

    sideEffect?.run {
      postSideEffect(this)
      return@intent
    }

    val timetableCellList = if (state.cellStateList.isEmpty()) {
      listOf(
        TimetableCell(
          name = state.lectureName,
          professor = state.professorName,
          location = "",
          day = TimetableDay.E_LEARNING,
          startPeriod = 0,
          endPeriod = 0,
          color = state.selectedTimetableCellColor,
        ),
      )
    } else {
      state.cellStateList.map { cell ->
        TimetableCell(
          name = state.lectureName,
          professor = state.professorName,
          location = cell.location,
          day = cell.day,
          startPeriod = cell.startPeriod.toIntOrNull() ?: 0,
          endPeriod = cell.endPeriod.toIntOrNull() ?: 0,
          color = state.selectedTimetableCellColor,
        )
      }
    }

    insertTimetableCellUseCase(timetableCellList)
      .onSuccess {
        postSideEffect(CellEditorSideEffect.ShowSuccessCellToastEditor)
        popBackStack()
      }
      .onFailure {
        if (it is TimetableCellOverlapException) {
          postSideEffect(CellEditorSideEffect.ShowOverlapCellToastEditor(it.message))
        } else {
          postSideEffect(CellEditorSideEffect.HandleException(it))
        }
      }
  }

  fun popBackStack() = intent { postSideEffect(CellEditorSideEffect.PopBackStack) }
}
