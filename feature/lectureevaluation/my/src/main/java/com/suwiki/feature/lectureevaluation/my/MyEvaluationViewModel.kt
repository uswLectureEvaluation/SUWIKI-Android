package com.suwiki.feature.lectureevaluation.my

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
class MyEvaluationViewModel @Inject constructor(
  private val getMyLectureEvaluationListUseCase: GetMyLectureEvaluationListUseCase,
  private val getMyExamEvaluationListUseCase: GetMyExamEvaluationListUseCase,
) : ContainerHost<MyEvaluationState, MyEvaluationSideEffect>, ViewModel() {
  override val container: Container<MyEvaluationState, MyEvaluationSideEffect> = container(MyEvaluationState())

  fun loadMyLectureEvaluations(page: Int) = intent {
    showLoadingScreen()
    getMyLectureEvaluationListUseCase(page)
      .onSuccess {
        reduce { state.copy(myLectureEvaluationList = it.toPersistentList()) }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
    hideLoadingScreen()
  }

  fun loadMyExamEvaluations(page: Int) = intent {
    showLoadingScreen()
    getMyExamEvaluationListUseCase(page)
      .onSuccess {
        reduce { state.copy(myExamEvaluationList = it.toPersistentList()) }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
    hideLoadingScreen()
  }

  fun loadInitList() = intent {
    showLoadingScreen()
    joinAll(loadMyLectureEvaluations(1), loadMyExamEvaluations(1))
    hideLoadingScreen()
  }

  fun syncPager(currentPage: Int) = intent { reduce { state.copy(currentPage = currentPage) } }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }

  fun popBackStack() = intent { postSideEffect(MyEvaluationSideEffect.PopBackStack) }
  fun navigateMyLectureEvaluation(lectureEvaluation: String) = intent {
    postSideEffect(MyEvaluationSideEffect.NavigateMyLectureEvaluation(lectureEvaluation))
  }
  fun navigateMyExamEvaluation(examEvaluation: String) = intent {
    postSideEffect(MyEvaluationSideEffect.NavigateMyExamEvaluation(examEvaluation))
  }
}
