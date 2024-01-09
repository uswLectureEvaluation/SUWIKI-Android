package com.suwiki.feature.myinfo.myevaluation.examevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.user.User
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyExamEvalutionEditViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  val getUserInfoUseCase: GetUserInfoUseCase,
) : ContainerHost<MyExamEvaluationEditState, MyExamEvaluationEditSideEffect>, ViewModel() {
  override val container: Container<MyExamEvaluationEditState, MyExamEvaluationEditSideEffect> =
    container(MyExamEvaluationEditState())

  private val myExamEvaluation = savedStateHandle.get<String>(MyInfoRoute.myExamEvaluation)!!

  suspend fun loadMyPoint() {
    showLoadingScreen()
    /* TODO 에러 처리 */
    Timber.d(myExamEvaluation)
    getUserInfoUseCase().collect(::setPoint)
    hideLoadingScreen()
  }

  fun clickReviseButton() {
    showReviseToast()
    // TODO("내 강의평가 수정 API 호출")
    popBackStack()
  }

  fun clickDeleteButton() {
    showDeleteToast()
    // TODO("내 강의평가 삭제 API 호출")
    popBackStack()
  }

  fun getSemester(semester: String) = intent { reduce { state.copy(selectedSemester = semester) } }
  fun getExamType(testType: String) = intent { reduce { state.copy(selectedExamType = testType) } }

  private fun setPoint(user: User) = intent { reduce { state.copy(point = user.point) } }
  fun setExamDifficultyEasy() = intent { reduce { state.copy(examDifficulty = "easy") } }
  fun setExamDifficultyNormal() = intent { reduce { state.copy(examDifficulty = "normal") } }
  fun setExamDifficultyHard() = intent { reduce { state.copy(examDifficulty = "hard") } }
  fun setExamInfoExamGuides() = intent { reduce { state.copy(examInfo = "exam_guides") } }
  fun setExamInfoBook() = intent { reduce { state.copy(examInfo = "book") } }
  fun setExamInfoNotes() = intent { reduce { state.copy(examInfo = "notes") } }
  fun setExamInfoPPT() = intent { reduce { state.copy(examInfo = "ppt") } }
  fun setExamInfoApply() = intent { reduce { state.copy(examInfo = "apply") } }
  fun setExamTypePractice() = intent { reduce { state.copy(examType = "practice") } }
  fun setExamTypeHomework() = intent { reduce { state.copy(examType = "homework") } }

  fun updateMyExamEvalutionValue(examEvalutionValue: String) = intent {
    reduce { state.copy(examEvalution = examEvalutionValue) }
  }

  fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun showMyExamEvalutionDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvalutionDialog = true) } }
  fun hideMyExamEvalutionDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvalutionDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = true) } }
  fun hideExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(MyExamEvaluationEditSideEffect.PopBackStack) }
  private fun showDeleteToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationDeleteToast) }
  private fun showReviseToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationReviseToast) }
}
