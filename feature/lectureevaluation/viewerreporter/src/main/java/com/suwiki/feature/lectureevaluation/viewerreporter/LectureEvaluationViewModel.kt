package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.RetrieveLectureEvaluationAverageListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
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
class LectureEvaluationViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val getLectureEvaluationListUseCase: RetrieveLectureEvaluationAverageListUseCase,
) : ContainerHost<LectureEvaluationState, LectureEvaluationSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationState, LectureEvaluationSideEffect> =
    container(LectureEvaluationState())

  private var isLoggedIn: Boolean = false
  private var isFirstVisit: Boolean = true
  private val lectureEvaluationInfoList = mutableListOf<LectureEvaluationAverage?>()
  private val _loadMoreCounter = mutableIntStateOf(1)
  private val loadMoreCounter: Int by _loadMoreCounter

  private fun incrementLoadMoreCounter() {
    _loadMoreCounter.intValue++
  }

  @OptIn(OrbitExperimental::class)
  fun updateSearchValue(searchValue: String) = blockingIntent {
    reduce { state.copy(searchValue = searchValue) }
    if(loadMoreCounter>1) _loadMoreCounter.intValue = 1
    lectureEvaluationInfoList.clear()
    reduceLectureEvaluationInfoList()
  }
  @OptIn(OrbitExperimental::class)
  fun updateSelectedOpenMajor(openMajor: String) = blockingIntent {
    reduce { state.copy(selectedOpenMajor = openMajor) }
    if(loadMoreCounter>1) _loadMoreCounter.intValue = 1
    lectureEvaluationInfoList.clear()
    reduceLectureEvaluationInfoList()
  }

  fun updateAlignItem(selectedFilter: String) = intent {
    reduce { state.copy(selectedFilter = selectedFilter) }
  }

  fun checkLoggedInShowBottomSheetIfNeed() = viewModelScope.launch {
    checkLoggedIn()
    if (isLoggedIn.not() && isFirstVisit) {
      isFirstVisit = false
      showOnboardingBottomSheet()
    }
  }

  fun getLectureEvaluationList(majorType: String) = intent {
    getLectureEvaluationListUseCase(
      RetrieveLectureEvaluationAverageListUseCase.Param(
        container.stateFlow.value.searchValue,
        "",
        loadMoreCounter,
        majorType,
      ),
    )
      .onSuccess {
        lectureEvaluationInfoList.addAll(it)
        incrementLoadMoreCounter()
        reduceLectureEvaluationInfoList()
      }
      .onFailure {
      }
  }

  private fun reduceLectureEvaluationInfoList() = intent {
    reduce {
      state.copy(
        lectureEvaluationList = lectureEvaluationInfoList.toPersistentList(),
      )
    }
  }

  private suspend fun checkLoggedIn() {
    isLoggedIn = getUserInfoUseCase().catch { }.lastOrNull()?.isLoggedIn == true
  }

  fun navigateLogin() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateLogin) }

  private fun showOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = true) } }
  fun hideOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = false) } }

  fun showAlignBottomSheet() = intent { reduce { state.copy(showFilterSelectionBottomSheet = true) } }
  fun hideAlignBottomSheet() = intent { reduce { state.copy(showFilterSelectionBottomSheet = false) } }
}
