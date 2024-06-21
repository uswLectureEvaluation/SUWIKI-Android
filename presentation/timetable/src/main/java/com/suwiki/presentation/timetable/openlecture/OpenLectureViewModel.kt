package com.suwiki.presentation.timetable.openlecture

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.exception.TimetableCellOverlapException
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.OpenLectureData
import com.suwiki.core.model.timetable.TimetableCell
import com.suwiki.core.model.timetable.TimetableCellColor
import com.suwiki.core.model.timetable.TimetableDay
import com.suwiki.domain.timetable.usecase.GetOpenLectureListUseCase
import com.suwiki.domain.timetable.usecase.InsertTimetableCellUseCase
import com.suwiki.presentation.timetable.navigation.argument.toCellEditorArgument
import com.suwiki.presentation.timetable.openlecture.model.SchoolLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
class OpenLectureViewModel @Inject constructor(
  private val getOpenLectureListUseCase: GetOpenLectureListUseCase,
  private val insertTimetableCellUseCase: InsertTimetableCellUseCase,
) : ViewModel(), ContainerHost<OpenLectureState, OpenLectureSideEffect> {

  private val mutex: Mutex = Mutex()

  override val container: Container<OpenLectureState, OpenLectureSideEffect> = container(
    OpenLectureState(),
  )

  private val currentState
    get() = container.stateFlow.value

  private var cursorId: Long = 0
  private var isLast: Boolean = false
  private var searchQuery: String = ""
  private var isFirstVisit: Boolean = true

  private var selectedOpenLecture: OpenLecture? = null

  fun navigateCellEditor(openLecture: OpenLecture = OpenLecture()) = intent {
    postSideEffect(
      OpenLectureSideEffect.NavigateCellEditor(openLecture.toCellEditorArgument()),
    )
  }

  fun navigateAddCustomCell() = intent { postSideEffect(OpenLectureSideEffect.NavigateAddCustomTimetableCell) }

  fun insertTimetable() = intent {
    if (selectedOpenLecture == null) return@intent

    val timetableCellList = if (selectedOpenLecture!!.originalCellList.isEmpty()) {
      listOf(
        TimetableCell(
          name = selectedOpenLecture!!.name,
          professor = selectedOpenLecture!!.professorName,
          location = "",
          day = TimetableDay.E_LEARNING,
          startPeriod = 0,
          endPeriod = 0,
          color = state.selectedTimetableCellColor,
        ),
      )
    } else {
      selectedOpenLecture!!.originalCellList.map { cell ->
        TimetableCell(
          name = selectedOpenLecture!!.name,
          professor = selectedOpenLecture!!.professorName,
          location = cell.location,
          day = cell.day,
          startPeriod = cell.startPeriod,
          endPeriod = cell.endPeriod,
          color = state.selectedTimetableCellColor,
        )
      }
    }

    insertTimetableCellUseCase(timetableCellList)
      .onSuccess {
        postSideEffect(OpenLectureSideEffect.ShowSuccessAddCellToast)
      }
      .onFailure {
        if (it is TimetableCellOverlapException) {
          postSideEffect(OpenLectureSideEffect.ShowOverlapCellToast(it.message))
        } else {
          postSideEffect(OpenLectureSideEffect.HandleException(it))
        }
      }
  }

  fun updateSelectedCellColor(color: TimetableCellColor) = intent { reduce { state.copy(selectedTimetableCellColor = color) } }

  fun showSelectColorBottomSheet(openLecture: OpenLecture) = intent {
    reduce {
      selectedOpenLecture = openLecture
      state.copy(
        selectedTimetableCellColor = TimetableCellColor.entries.shuffled().first(),
        showSelectCellColorBottomSheet = true,
      )
    }
  }

  fun hideSelectColorBottomSheet() = intent { reduce { state.copy(showSelectCellColorBottomSheet = false) } }

  fun initData() = intent {
    if (isFirstVisit) {
      getOpenLectureList(needClear = false)
      isFirstVisit = false
    }
  }

  fun searchOpenLecture(search: String) {
    searchQuery = search
    getOpenLectureList(search = search, needClear = true)
  }

  @OptIn(OrbitExperimental::class)
  fun updateSearchValue(searchValue: String) = blockingIntent {
    reduce { state.copy(searchValue = searchValue) }
  }

  fun updateSchoolLevelPosition(schoolLevel: SchoolLevel) = intent {
    reduce {
      state.copy(
        schoolLevel = schoolLevel,
      )
    }

    getOpenLectureList(
      needClear = true,
    )
  }

  fun updateSelectedOpenMajor(openMajor: String) = intent {
    if (openMajor == state.selectedOpenMajor) return@intent

    reduce {
      state.copy(
        selectedOpenMajor = openMajor,
      )
    }

    getOpenLectureList(
      needClear = true,
    )
  }

  fun getOpenLectureList(
    search: String = searchQuery,
    needClear: Boolean,
  ) = intent {
    mutex.withLock {
      val currentList = when {
        needClear -> {
          reduce { state.copy(isLoading = true) }
          cursorId = 0
          isLast = false
          emptyList()
        }

        isLast -> return@intent
        else -> state.openLectureList
      }

      getOpenLectureListUseCase(
        GetOpenLectureListUseCase.Param(
          cursorId = cursorId,
          keyword = search,
          major = if (currentState.selectedOpenMajor == "전체") null else currentState.selectedOpenMajor,
          grade = currentState.schoolLevel.query,
        ),
      ).onSuccess { newData ->
        handleGetOpenLectureListSuccess(
          currentList = currentList,
          newData = newData,
        )
      }.onFailure {
        postSideEffect(OpenLectureSideEffect.HandleException(it))
      }

      if (needClear) {
        postSideEffect(OpenLectureSideEffect.ScrollToTop)
        reduce { state.copy(isLoading = false) }
      }
    }
  }

  private suspend fun SimpleSyntax<OpenLectureState, OpenLectureSideEffect>.handleGetOpenLectureListSuccess(
    currentList: List<OpenLecture>,
    newData: OpenLectureData,
  ) = reduce {
    isLast = newData.isLast
    cursorId = newData.content.lastOrNull()?.id ?: 0L
    state.copy(
      openLectureList = currentList
        .plus(newData.content)
        .distinctBy { it.id }
        .toPersistentList(),
    )
  }

  fun showGradeBottomSheet() = intent { reduce { state.copy(showSchoolLevelBottomSheet = true) } }
  fun hideGradeBottomSheet() = intent { reduce { state.copy(showSchoolLevelBottomSheet = false) } }
  fun popBackStack() = intent { postSideEffect(OpenLectureSideEffect.PopBackStack) }
}
