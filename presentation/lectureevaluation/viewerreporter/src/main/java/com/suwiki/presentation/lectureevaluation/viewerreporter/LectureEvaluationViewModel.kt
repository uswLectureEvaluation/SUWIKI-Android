package com.suwiki.presentation.lectureevaluation.viewerreporter

import androidx.lifecycle.ViewModel
import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationAverage
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.RetrieveLectureEvaluationAverageListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.lastOrNull
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
class LectureEvaluationViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val getLectureEvaluationListUseCase: RetrieveLectureEvaluationAverageListUseCase,
) : ContainerHost<LectureEvaluationState, LectureEvaluationSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationState, LectureEvaluationSideEffect> =
    container(LectureEvaluationState())

  private val mutex: Mutex = Mutex()

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

    reduce {
      state.copy(
        selectedOpenMajor = openMajor,
      )
    }

    getLectureEvaluationList(
      needClear = true,
    )
  }

  fun updateAlignItem(position: Int) = intent {
    reduce {
      state.copy(
        selectedAlignPosition = position,
      )
    }

    getLectureEvaluationList(
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
    needClear: Boolean,
  ) = intent {
    mutex.withLock {
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
          option = LectureAlign.entries[currentState.selectedAlignPosition].query,
          page = page,
          majorType = currentState.selectedOpenMajor,
        ),
      ).onSuccess { newList ->
        handleGetLectureEvaluationListSuccess(
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
  }

  private suspend fun SimpleSyntax<LectureEvaluationState, LectureEvaluationSideEffect>.handleGetLectureEvaluationListSuccess(
    currentList: List<LectureEvaluationAverage?>,
    newList: List<LectureEvaluationAverage?>,
  ) = reduce {
    page++
    state.copy(
      lectureEvaluationList = currentList
        .plus(newList)
        .distinctBy { it?.id }
        .toPersistentList(),
    )
  }

  private suspend fun checkLoggedIn() {
    isLoggedIn = getUserInfoUseCase().catch { }.lastOrNull()?.isLoggedIn == true
  }

  fun navigateOpenMajor(selectedOpenMajor: String) = intent { postSideEffect(LectureEvaluationSideEffect.NavigateOpenMajor(selectedOpenMajor)) }
  fun navigateLogin() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateLogin) }
  fun navigateSignup() = intent { postSideEffect(LectureEvaluationSideEffect.NavigateSignUp) }
  fun navigateLectureEvaluationDetailIfLoggedIn(id: String) = intent {
    if (isLoggedIn.not()) {
      postSideEffect(LectureEvaluationSideEffect.NavigateLogin)
    } else {
      postSideEffect(LectureEvaluationSideEffect.NavigateLectureEvaluationDetail(id))
    }
  }

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
