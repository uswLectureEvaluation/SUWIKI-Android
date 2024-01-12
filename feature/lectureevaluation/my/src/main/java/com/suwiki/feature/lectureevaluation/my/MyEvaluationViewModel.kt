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

  private var currentLectureEvaluationPage = 1
  private var currentExamEvaluationPage = 1

  fun loadMyLectureEvaluations() = intent {
    showLoadingScreen()
    getMyLectureEvaluationListUseCase(currentLectureEvaluationPage)
      .onSuccess {
        reduce { state.copy(myLectureEvaluationList = state.myLectureEvaluationList.addAll(it.toPersistentList())) }
        currentLectureEvaluationPage++
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
    hideLoadingScreen()
  }

  fun loadMyExamEvaluations() = intent {
    showLoadingScreen()
    getMyExamEvaluationListUseCase(currentExamEvaluationPage)
      .onSuccess {
        reduce { state.copy(myExamEvaluationList = state.myExamEvaluationList.addAll(it.toPersistentList())) }
        currentExamEvaluationPage++
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
    hideLoadingScreen()
  }

  fun loadInitList() = intent {
    showLoadingScreen()
    joinAll(loadMyLectureEvaluations(), loadMyExamEvaluations())
    hideLoadingScreen()
  }

  fun syncPager(currentPage: Int) = intent { reduce { state.copy(currentTabPage = currentPage) } }

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
