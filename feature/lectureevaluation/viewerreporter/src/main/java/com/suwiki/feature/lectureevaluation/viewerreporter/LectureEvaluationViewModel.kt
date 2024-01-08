package com.suwiki.feature.lectureevaluation.viewerreporter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.enums.LectureAlign
import com.suwiki.domain.lectureevaluation.viewerreporter.usecase.lecture.RetrieveLectureEvaluationAverageListUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
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
class LectureEvaluationViewModel @Inject constructor(
  private val getUserInfoUseCase: GetUserInfoUseCase,
  private val getLectureEvaluationListUseCase: RetrieveLectureEvaluationAverageListUseCase,
) : ContainerHost<LectureEvaluationState, LectureEvaluationSideEffect>, ViewModel() {
  override val container: Container<LectureEvaluationState, LectureEvaluationSideEffect> =
    container(LectureEvaluationState())

  private var isLoggedIn: Boolean = false
  private var isFirstVisit: Boolean = true
  private var page: Int = 1

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

  fun initData()  = intent {
    checkLoggedIn()
    if (isLoggedIn.not() && isFirstVisit) {
      showOnboardingBottomSheet()
    }

    if(isFirstVisit) {
      reduce { state.copy(isLoading = true) }
      getLectureEvaluationList(needClear = false).join()
      reduce { state.copy(isLoading = false) }
    }

    isFirstVisit = false
  }

  fun getLectureEvaluationList(
    search: String? = null,
    alignPosition: Int? = null,
    majorType: String? = null,
    needClear: Boolean,
  ) = intent {
    if (needClear) {
      reduce {
        page = 1
        state.copy(
          isLoading = true,
          lectureEvaluationList = persistentListOf()
        )
      }
    }

    val alignValue = alignPosition?.let {
      LectureAlign.entries[it]
    }

    getLectureEvaluationListUseCase(
      RetrieveLectureEvaluationAverageListUseCase.Param(
        search = search ?: state.searchValue,
        option = alignValue?.query ?: state.alignValue.query,
        page = page,
        majorType = majorType ?: state.selectedOpenMajor,
      ),
    )
      .onSuccess {
        reduce {
          page++
          state.copy(
            searchValue = search ?: state.searchValue,
            selectedAlignPosition = alignPosition ?: state.selectedAlignPosition,
            selectedOpenMajor = majorType ?: state.selectedOpenMajor,
            lectureEvaluationList = state.lectureEvaluationList
              .plus(it)
              .distinctBy { it?.id }
              .toPersistentList(),
          )
        }
      }
      .onFailure {
      }

    reduce { state.copy(isLoading = false) }
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
