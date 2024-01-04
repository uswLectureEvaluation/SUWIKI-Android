package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.RetrieveLectureEvaluationAverageListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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
  private var loadMoreCounter: Int = 1

  private fun incrementLoadMoreCounter() {
    loadMoreCounter++
  }

  @OptIn(OrbitExperimental::class)
  fun updateSearchValue(searchValue: String) = blockingIntent {
    reduce { state.copy(searchValue = searchValue) }
    setFilterLectureEvaluationList()
  }

  @OptIn(OrbitExperimental::class)
  fun updateSelectedOpenMajor(openMajor: String) = blockingIntent {
    reduce { state.copy(selectedOpenMajor = openMajor) }
    setFilterLectureEvaluationList()
  }

  @OptIn(OrbitExperimental::class)
  fun updateAlignItem(selectedFilter: String) = blockingIntent {
    reduce { state.copy(selectedFilter = selectedFilter) }
    setFilterLectureEvaluationList()
  }

  private fun setFilterLectureEvaluationList() = intent {
    if (loadMoreCounter > 1) loadMoreCounter = 1
    reduce { state.copy(lectureEvaluationList = persistentListOf()) }
  }

  fun checkLoggedInShowBottomSheetIfNeed() = viewModelScope.launch {
    checkLoggedIn()
    if (isLoggedIn.not() && isFirstVisit) {
      isFirstVisit = false
      showOnboardingBottomSheet()
    }
  }

  fun getLectureEvaluationList() = intent {
    getLectureEvaluationListUseCase(
      RetrieveLectureEvaluationAverageListUseCase.Param(
        container.stateFlow.value.searchValue,
        container.stateFlow.value.selectedFilterValue(),
        loadMoreCounter,
        container.stateFlow.value.selectedOpenMajor,
      ),
    )
      .onSuccess {
        reduce {
          state.copy(lectureEvaluationList = (container.stateFlow.value.lectureEvaluationList + it).distinctBy { it?.id }.toPersistentList())
        }
        incrementLoadMoreCounter()
      }
      .onFailure {
      }
  }

  private suspend fun checkLoggedIn() {
    isLoggedIn = getUserInfoUseCase().catch { }.lastOrNull()?.isLoggedIn == true
  }

  fun navigateLogin() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateLogin) }

  private fun showOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = true) } }
  fun hideOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = false) } }

  fun showAlignBottomSheet() = intent { reduce { state.copy(showAlignBottomSheet = true) } }
  fun hideAlignBottomSheet() = intent { reduce { state.copy(showAlignBottomSheet = false) } }
}
