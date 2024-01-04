package com.suwiki.feature.myinfo.myreview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.domain.lectureevaluation.my.usecase.GetMyExamEvaluationListUseCase
import com.suwiki.domain.lectureevaluation.my.usecase.GetMyLectureEvaluationListUseCase
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.joinAll
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyReviewViewModel @Inject constructor(
  private val getMyLectureEvaluationListUseCase: GetMyLectureEvaluationListUseCase,
  private val getMyExamEvaluationListUseCase: GetMyExamEvaluationListUseCase,
  savedStateHandle: SavedStateHandle,
) : ContainerHost<MyReviewState, MyReviewSideEffect>, ViewModel() {
  override val container: Container<MyReviewState, MyReviewSideEffect> = container(MyReviewState())

  private val point: Int = savedStateHandle.get<String>(MyInfoRoute.myPoint)!!.toInt()

  // TODO("인자에 페이지 추가")
  fun loadMyLectureEvaluations() = intent {
    Timber.d("$point")
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
    reduce { state.copy(point = point) }
    joinAll(loadMyLectureEvaluations(), loadMyExamEvaluations())
    hideLoadingScreen()
  }

  fun syncPager(currentPage: Int) = intent { reduce { state.copy(currentPage = currentPage) } }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }

  fun popBackStack() = intent { postSideEffect(MyReviewSideEffect.PopBackStack) }
  fun navigateMyClassReview(point: Int) = intent { postSideEffect(MyReviewSideEffect.NavigateMyClassReview(point)) }
  fun navigateMyTestReview(point: Int) = intent { postSideEffect(MyReviewSideEffect.NavigateMyTestReview(point)) }
}
