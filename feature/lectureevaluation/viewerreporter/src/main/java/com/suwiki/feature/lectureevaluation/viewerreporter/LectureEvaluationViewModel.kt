package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.GetLectureEvaluationAverageListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.lectureevaluation.viewerreporter.model.toLectureEvaluation
import dagger.hilt.android.lifecycle.HiltViewModel
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
  private val getLectureEvaluationListUseCase: GetLectureEvaluationAverageListUseCase,
) : ContainerHost<LectureEvaluationState, LectureEvaluationSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationState, LectureEvaluationSideEffect> =
    container(LectureEvaluationState())

  private var isLoggedIn: Boolean = false
  private var isFirstVisit: Boolean = true
  private val lectureEvaluationInfoList = mutableListOf<LectureEvaluationAverage?>()

  @OptIn(OrbitExperimental::class)
  fun updateSearchValue(searchValue: String) = blockingIntent {
    reduce { state.copy(searchValue = searchValue) }
    reduceLectureEvaluationInfoList(searchValue = searchValue)
  }

  fun updateSelectedOpenMajor(openMajor: String) = intent {
    reduce { state.copy(selectedOpenMajor = openMajor) }
  }

  fun updateSelectedFilter(selectedFilter: String) = intent {
    reduce { state.copy(selectedFilter = selectedFilter) }
  }
  fun checkLoggedInShowBottomSheetIfNeed() = viewModelScope.launch {
    checkLoggedIn()
    if (isLoggedIn.not() && isFirstVisit) {
      isFirstVisit = false
      showOnboardingBottomSheet()
    }
  }

  fun getLectureEvaluationList(page: Int , majorType:String) = intent {
    getLectureEvaluationListUseCase(GetLectureEvaluationAverageListUseCase.Param("", page, majorType))
      .onSuccess {
        lectureEvaluationInfoList.addAll(it)
        reduceLectureEvaluationInfoList()
      }
      .onFailure {

      }
  }

  private fun reduceLectureEvaluationInfoList(
    searchValue: String = container.stateFlow.value.searchValue,
  ) = intent {
    reduce {
      state.copy(
        filteredLectureEvaluationList = lectureEvaluationInfoList.toLectureEvaluation(
          searchValue = searchValue,
        ),
      )
    }
  }

  private suspend fun checkLoggedIn() {
    isLoggedIn = getUserInfoUseCase().catch { }.lastOrNull()?.isLoggedIn == true
  }

  fun navigateLogin() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateLogin) }

  private fun showOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = true) } }
  fun hideOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = false) } }
}
