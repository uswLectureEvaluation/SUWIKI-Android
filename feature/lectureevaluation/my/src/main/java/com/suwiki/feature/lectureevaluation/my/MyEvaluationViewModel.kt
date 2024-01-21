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

  private var isFirstVisit = true
  private var lectureEvaluationPage = 1
  private var isLastLectureEvaluation = false
  private var examEvaluationPage = 1
  private var isLastExamEvaluation = false

  fun getMyLectureEvaluations() = intent {
    if (isLastLectureEvaluation) return@intent

    getMyLectureEvaluationListUseCase(lectureEvaluationPage)
      .onSuccess {
        reduce {
          lectureEvaluationPage++
          isLastLectureEvaluation = it.isEmpty()
          state.copy(myLectureEvaluationList = state.myLectureEvaluationList.addAll(it.toPersistentList()))
        }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
  }

  fun getMyExamEvaluations() = intent {
    if (isLastExamEvaluation) return@intent

    getMyExamEvaluationListUseCase(examEvaluationPage)
      .onSuccess {
        reduce {
          examEvaluationPage++
          isLastExamEvaluation = it.isEmpty()
          state.copy(myExamEvaluationList = state.myExamEvaluationList.addAll(it.toPersistentList()))
        }
      }
      .onFailure {
        postSideEffect(MyEvaluationSideEffect.HandleException(it))
      }
  }

  fun initData() = intent {
    if (isFirstVisit.not()) return@intent
    showLoadingScreen()
    joinAll(getMyLectureEvaluations(), getMyExamEvaluations())
    hideLoadingScreen()
    isFirstVisit = false
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
