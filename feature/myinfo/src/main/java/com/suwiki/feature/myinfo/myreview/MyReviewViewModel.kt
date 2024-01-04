package com.suwiki.feature.myinfo.myreview

import androidx.lifecycle.ViewModel
import com.suwiki.domain.lectureevaluation.my.usecase.GetMyExamEvaluationListUseCase
import com.suwiki.domain.lectureevaluation.my.usecase.GetMyLectureEvaluationListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.joinAll
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyReviewViewModel @Inject constructor(
  private val getMyLectureEvaluationListUseCase: GetMyLectureEvaluationListUseCase,
  private val getMyExamEvaluationListUseCase: GetMyExamEvaluationListUseCase,
) : ContainerHost<MyReviewState, MyReviewSideEffect>, ViewModel() {
  override val container: Container<MyReviewState, MyReviewSideEffect> = container(MyReviewState())

  // TODO("인자에 페이지 추가")
  fun loadMyLectureEvaluations() = intent {
    showLoadingScreen()
    getMyLectureEvaluationListUseCase(1)
      .onSuccess {
        reduce { state.copy(myLectureEvaluationList = it.toPersistentList()) }
      }
      .onFailure {
      }
    hideLoadingScreen()
  }

  // TODO("인자에 페이지 추가")
  fun loadMyExamEvaluations() = intent {
    showLoadingScreen()
    getMyExamEvaluationListUseCase(1)
      .onSuccess {
        reduce { state.copy(myExamEvaluationList = it.toPersistentList()) }
      }
      .onFailure {
      }
    hideLoadingScreen()
  }

  // TODO("인자에 페이지 추가")
  fun loadInitList() = intent {
    showLoadingScreen()
    joinAll(loadMyLectureEvaluations(), loadMyExamEvaluations())
    hideLoadingScreen()
  }

  fun syncPager(currentPage: Int) = intent { reduce { state.copy(currentPage = currentPage) } }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }

  fun popBackStack() = intent { postSideEffect(MyReviewSideEffect.PopBackStack) }
  fun navigateMyClassReview() = intent { postSideEffect(MyReviewSideEffect.NavigateMyClassReview) }
  fun navigateMyTestReview() = intent { postSideEffect(MyReviewSideEffect.NavigateMyTestReview) }
}
