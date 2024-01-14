package com.suwiki.feature.timetable.addcell

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.OpenLectureData
import com.suwiki.domain.timetable.usecase.GetOpenLectureListUseCase
import com.suwiki.feature.timetable.addcell.model.SchoolLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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
class AddTimetableCellViewModel @Inject constructor(
  private val getOpenLectureListUseCase: GetOpenLectureListUseCase,
) : ViewModel(), ContainerHost<AddTimetableCellState, AddTimetableCellSideEffect> {

  override val container: Container<AddTimetableCellState, AddTimetableCellSideEffect> = container(
    AddTimetableCellState(),
  )

  private val currentState
    get() = container.stateFlow.value

  private var cursorId: Long = 0
  private var isLast: Boolean = false
  private var searchQuery: String = ""
  private var isFirstVisit: Boolean = true

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
    getOpenLectureList(
      schoolLevel = schoolLevel,
      needClear = true,
    )
  }

  fun updateSelectedOpenMajor(openMajor: String) = intent {
    if (openMajor == state.selectedOpenMajor) return@intent
    getOpenLectureList(
      majorType = openMajor,
      needClear = true,
    )
  }

  fun getOpenLectureList(
    search: String = searchQuery,
    schoolLevel: SchoolLevel = currentState.schoolLevel,
    majorType: String = currentState.selectedOpenMajor,
    needClear: Boolean,
  ) = intent {
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
        major = if (majorType == "전체") null else majorType,
        grade = schoolLevel.query,
      ),
    ).onSuccess { newData ->
      handleGetOpenLectureListSuccess(
        schoolLevel = schoolLevel,
        majorType = majorType,
        currentList = currentList,
        newData = newData,
      )
    }.onFailure {
      postSideEffect(AddTimetableCellSideEffect.HandleException(it))
    }

    if (needClear) {
      postSideEffect(AddTimetableCellSideEffect.ScrollToTop)
      reduce { state.copy(isLoading = false) }
    }
  }

  private fun handleGetOpenLectureListSuccess(
    schoolLevel: SchoolLevel,
    majorType: String,
    currentList: List<OpenLecture>,
    newData: OpenLectureData,
  ) = intent {
    reduce {
      isLast = newData.isLast
      cursorId = newData.content.lastOrNull()?.id ?: 0L
      state.copy(
        schoolLevel = schoolLevel,
        selectedOpenMajor = majorType,
        openLectureList = currentList
          .plus(newData.content)
          .distinctBy { it.id }
          .toPersistentList(),
      )
    }
  }

  fun showGradeBottomSheet() = intent { reduce { state.copy(showSchoolLevelBottomSheet = true) } }
  fun hideGradeBottomSheet() = intent { reduce { state.copy(showSchoolLevelBottomSheet = false) } }
  fun popBackStack() = intent { postSideEffect(AddTimetableCellSideEffect.PopBackStack) }
}