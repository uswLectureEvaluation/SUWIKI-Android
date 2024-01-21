package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.RetrieveLectureEvaluationAverageListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.lastOrNull
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

  private val currentState
    get() = container.stateFlow.value

  private var isLoggedIn: Boolean = false
  private var isFirstVisit: Boolean = true
  private var page: Int = 1
  private var searchQuery: String = ""

  fun searchLectureEvaluation(search: String) {
    searchQuery = search
    getLectureEvaluationList(search = search, needClear = true)
  }

  @OptIn(OrbitExperimental::class)
  fun updateSearchValue(searchValue: String) = blockingIntent {
    reduce { state.copy(searchValue = searchValue) }
  }

  fun updateSelectedOpenMajor(openMajor: String) = intent {
    if (openMajor == state.selectedOpenMajor) return@intent
    getLectureEvaluationList(
      majorType = openMajor,
      needClear = true,
    )
  }

  fun updateAlignItem(position: Int) {
    getLectureEvaluationList(
      alignPosition = position,
      needClear = true,
    )
  }

  fun initData() = intent {
    checkLoggedIn()
    if (isLoggedIn.not() && isFirstVisit) {
      showOnboardingBottomSheet()
    }

    if (isFirstVisit) {
      reduce { state.copy(isLoading = true) }
      getLectureEvaluationList(needClear = false).join()
      reduce { state.copy(isLoading = false) }
    }

    isFirstVisit = false
  }

  fun getLectureEvaluationList(
    search: String = searchQuery,
    alignPosition: Int = currentState.selectedAlignPosition,
    majorType: String = currentState.selectedOpenMajor,
    needClear: Boolean,
  ) = intent {
    val currentList = if (needClear) {
      page = 1
      reduce { state.copy(isLoading = true) }
      emptyList()
    } else {
      state.lectureEvaluationList
    }

    getLectureEvaluationListUseCase(
      RetrieveLectureEvaluationAverageListUseCase.Param(
        search = search,
        option = LectureAlign.entries[alignPosition].query,
        page = page,
        majorType = majorType,
      ),
    ).onSuccess { newList ->
      handleGetLectureEvaluationListSuccess(
        alignPosition = alignPosition,
        majorType = majorType,
        currentList = currentList,
        newList = newList,
      )
    }.onFailure {
      postSideEffect(LectureEvaluationSideEffect.HandleException(it))
    }

    if (needClear) {
      postSideEffect(LectureEvaluationSideEffect.ScrollToTop)
      reduce { state.copy(isLoading = false) }
    }
  }

  private fun handleGetLectureEvaluationListSuccess(
    alignPosition: Int,
    majorType: String,
    currentList: List<LectureEvaluationAverage?>,
    newList: List<LectureEvaluationAverage?>,
  ) = intent {
    reduce {
      page++
      state.copy(
        selectedAlignPosition = alignPosition,
        selectedOpenMajor = majorType,
        lectureEvaluationList = currentList
          .plus(newList)
          .distinctBy { it?.id }
          .toPersistentList(),
      )
    }
  }

  private suspend fun checkLoggedIn() {
    isLoggedIn = getUserInfoUseCase().catch { }.lastOrNull()?.isLoggedIn == true
  }

  fun navigateOpenMajor(selectedOpenMajor: String) = intent { postSideEffect(LectureEvaluationSideEffect.NavigateOpenMajor(selectedOpenMajor)) }
  fun navigateLogin() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateLogin) }
  fun navigateSignup() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateSignUp) }
  fun navigateLectureEvaluationDetail(id: String) = intent { postSideEffect(LectureEvaluationSideEffect.NavigateLectureEvaluationDetail(id)) }
  private fun showOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = true) } }
  fun hideOnboardingBottomSheet() = intent { reduce { state.copy(showOnboardingBottomSheet = false) } }

  fun showAgreementBottomSheet() = intent { reduce { state.copy(showAgreementBottomSheet = true) } }
  fun hideAgreementBottomSheet() = intent { reduce { state.copy(showAgreementBottomSheet = false) } }

  fun toggleTermChecked() = intent { reduce { state.copy(isCheckedTerm = !state.isCheckedTerm) } }
  fun togglePersonalPolicyChecked() = intent { reduce { state.copy(isCheckedPersonalPolicy = !state.isCheckedPersonalPolicy) } }

  fun openTermWebSite() = intent { postSideEffect(LectureEvaluationSideEffect.OpenTermWebSite) }
  fun openPersonalPolicyWebSite() = intent { postSideEffect(LectureEvaluationSideEffect.OpenPersonalPolicyWebSite) }

  fun showAlignBottomSheet() = intent { reduce { state.copy(showAlignBottomSheet = true) } }
  fun hideAlignBottomSheet() = intent { reduce { state.copy(showAlignBottomSheet = false) } }
}
