package com.suwiki.feature.myinfo.myevaluation.examevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.enums.ExamLevel
import com.suwiki.core.model.enums.ExamType
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.user.User
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.DeleteExamEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.UpdateExamEvaluationUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyExamEvaluationEditViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  val getUserInfoUseCase: GetUserInfoUseCase,
  val deleteExamEvaluationUseCase: DeleteExamEvaluationUseCase,
  val updateExamEvaluationUseCase: UpdateExamEvaluationUseCase,
) : ContainerHost<MyExamEvaluationEditState, MyExamEvaluationEditSideEffect>, ViewModel() {
  override val container: Container<MyExamEvaluationEditState, MyExamEvaluationEditSideEffect> =
    container(MyExamEvaluationEditState())

  private val myExamEvaluation = savedStateHandle.get<String>(MyInfoRoute.myExamEvaluation)!!
  private val myExamEvaluationItem: MyExamEvaluation = Json.decodeFromString(myExamEvaluation)

  private var selectedSemester: String = ""
  private var examInfo: String = ""
  private var examType: String = ""
  private var examDifficulty: String = ""
  private var content: String = ""

  suspend fun setInitData() = intent {
    with(myExamEvaluationItem) {
      showLoadingScreen()
      /* TODO 에러 처리 */
      reduce { state.copy(semesterList = semesterList!!.split(", ")) }
      reduce { state.copy(selectedSemester = selectedSemester!!) }
      reduce { state.copy(selectedExamInfo = examInfo) }
      updateExamLevel(
        when (examDifficulty) {
          "쉬움" -> ExamLevel.EASY
          "보통" -> ExamLevel.NORMAL
          "어려움" -> ExamLevel.DIFFICULT
          else -> ExamLevel.EASY
        },
      )
      updateExamType(
        when (examType) {
          "족보" -> ExamType.FAMILY_TREE
          "교재" -> ExamType.TEXTBOOK
          "PPT" -> ExamType.PPT
          "필기" -> ExamType.WRITING
          "응용" -> ExamType.APPLY
          "실습" -> ExamType.PRACTICE
          "과제" -> ExamType.HOMEWORK
          else -> ExamType.FAMILY_TREE
        },
      )
      updateMyExamEvaluationValue(content)
      getUserInfoUseCase().collect(::setPoint)
      hideLoadingScreen()
    }
  }

  fun clickReviseButton() = intent {
    viewModelScope.launch {
      updateExamEvaluationUseCase(
        UpdateExamEvaluationUseCase.Param(
          lectureId = myExamEvaluationItem.id!!,
          selectedSemester = selectedSemester,
          examInfo = examInfo,
          examType = examType,
          examDifficulty = examDifficulty,
          content = content,
        )
      )
        .onSuccess {
          showReviseToast()
          popBackStack()
        }
        .onFailure {
          postSideEffect(MyExamEvaluationEditSideEffect.HandleException(it))
        }
    }
  }

  fun clickDeleteButton() = intent {
    viewModelScope.launch {
      deleteExamEvaluationUseCase(myExamEvaluationItem.id!!)
        .onSuccess {
          showDeleteToast()
          popBackStack()
        }
        .onFailure {
          postSideEffect(MyExamEvaluationEditSideEffect.HandleException(it))
        }
    }
  }

  fun clickSemesterItem(semester: String) = intent {
    selectedSemester = semester
    reduce { state.copy(selectedSemester = semester) }
    hideSemesterBottomSheet()
  }

  fun clickExamInfoItem(info: String) = intent {
    examInfo = info
    reduce { state.copy(selectedExamInfo = info) }
    hideExamTypeBottomSheet()
  }

  private fun setPoint(user: User) = intent { reduce { state.copy(point = user.point) } }
  fun updateExamLevel(examLevel: ExamLevel) = intent {
    examDifficulty = examLevel.value
    reduce { state.copy(examLevel = examLevel) }
  }
  fun updateExamType(type: ExamType) = intent {
    examType = type.value
    reduce { state.copy(examType = type) }
  }

  fun updateMyExamEvaluationValue(examEvaluationValue: String) = intent {
    content = examEvaluationValue
    reduce { state.copy(examEvaluation = examEvaluationValue) }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun showMyExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = true) } }
  fun hideMyExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showExamTypeBottomSheet() = intent { reduce { state.copy(showExamInfoBottomSheet = true) } }
  fun hideExamTypeBottomSheet() = intent { reduce { state.copy(showExamInfoBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(MyExamEvaluationEditSideEffect.PopBackStack) }
  private fun showDeleteToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationDeleteToast) }
  private fun showReviseToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationReviseToast) }
}
