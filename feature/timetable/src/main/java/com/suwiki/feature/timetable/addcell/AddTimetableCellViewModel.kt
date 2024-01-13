package com.suwiki.feature.timetable.addcell

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.timetable.OpenLecture
import com.suwiki.core.model.timetable.OpenLectureData
import com.suwiki.domain.timetable.usecase.GetOpenLectureListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AddTimetableCellViewModel @Inject constructor(
  private val getOpenLectureListUseCase: GetOpenLectureListUseCase,
) : ViewModel(), ContainerHost<AddTimetableCellState, AddTimetableCellSideEffect> {
  override val container: Container<AddTimetableCellState, AddTimetableCellSideEffect> = container(AddTimetableCellState())

  private val currentState
    get() = container.stateFlow.value

  private var cursorId: Long = 0
  private var isLast: Boolean = false
  private var searchQuery: String = ""
  private var isFirstVisit: Boolean = true

  fun initData() = intent {
    if (isFirstVisit) getOpenLectureList(needClear = false)
  }

  fun getOpenLectureList(
    search: String = searchQuery,
    // alignPosition: Int = currentState.selectedAlignPosition,
    // majorType: String = currentState.selectedOpenMajor,
    needClear: Boolean,
  ) = intent {
    val currentList = if (needClear) {
      reduce { state.copy(isLoading = true) }
      cursorId = 0
      isLast = false
      emptyList()
    } else {
      state.openLectureList
    }

    getOpenLectureListUseCase(
      GetOpenLectureListUseCase.Param(
        cursorId = cursorId,
        keyword = search,
        major = null,
        grade = null,
      ),
    ).onSuccess { newData ->
      handleGetOpenLectureListSuccess(
        // alignPosition = alignPosition,
        // majorType = majorType,
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
    // alignPosition: Int,
    // majorType: String,
    currentList: List<OpenLecture>,
    newData: OpenLectureData,
  ) = intent {
    reduce {
      isLast = newData.isLast
      cursorId = newData.content.lastOrNull()?.id ?: 0L
      state.copy(
        openLectureList = currentList
          .plus(newData.content)
          .distinctBy { it.id }
          .toPersistentList(),
      )
    }
  }
}
