package com.suwiki.feature.lectureevaluation.editor.examevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.core.model.enums.ExamType
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.user.User
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.DeleteExamEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.UpdateExamEvaluationUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.lectureevaluation.editor.lectureevaluation.MINIMUM_DELETE_POINT
import com.suwiki.feature.lectureevaluation.editor.navigation.MyEvaluationEditRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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

  private val myExamEvaluation = savedStateHandle.get<String>(MyEvaluationEditRoute.myExamEvaluation)!!
  private val myExamEvaluationItem: MyExamEvaluation = Json.decodeFromString(myExamEvaluation)

  suspend fun initData() = intent {
    showLoadingScreen()
    with(myExamEvaluationItem) {
      getUserInfoUseCase().collect(::getPoint).runCatching {}
        .onFailure {
          postSideEffect(MyExamEvaluationEditSideEffect.HandleException(it))
        }
      reduce {
        state.copy(
          semesterList = semesterList!!.split(", ").toPersistentList(),
          selectedSemester = selectedSemester,
          selectedExamType = examType,
        )
      }
      examInfo.forEach { info ->
        updateExamInfo(info)
      }
      updateExamLevel(examDifficulty)
      updateMyExamEvaluationValue(content)
    }
    hideLoadingScreen()
  }

  private fun getPoint(user: User) = intent { reduce { state.copy(point = user.point) } }

  fun updateExamEvaluation() = intent {
    updateExamEvaluationUseCase(
      UpdateExamEvaluationUseCase.Param(
        lectureId = myExamEvaluationItem.id!!,
        selectedSemester = state.selectedSemester,
        examInfo = state.examInfo.filter { it.isNotBlank() }.joinToString(", "),
        examType = state.selectedExamType,
        examDifficulty = state.examLevel,
        content = state.examEvaluation,
      ),
    )
      .onSuccess {
        showReviseToast()
        popBackStack()
      }
      .onFailure {
        postSideEffect(MyExamEvaluationEditSideEffect.HandleException(it))
      }
  }

  fun deleteExamEvaluation() = intent {
    deleteExamEvaluationUseCase(myExamEvaluationItem.id!!)
      .onSuccess {
        showDeleteToast()
        popBackStack()
      }
      .onFailure {
        postSideEffect(MyExamEvaluationEditSideEffect.HandleException(it))
      }
  }

  fun updateSemester(selectedPosition: Int) = intent {
    reduce { state.copy(selectedSemester = state.semesterList[selectedPosition]) }
    hideSemesterBottomSheet()
  }

  fun updateExamType(selectedPosition: Int) = intent {
    ExamType.entries.forEach { examType ->
      if (examType.ordinal == selectedPosition) {
        reduce { state.copy(selectedExamType = examType.value) }
      }
    }
    hideExamTypeBottomSheet()
  }
  fun updateExamLevel(examLevel: String) = intent {
    reduce { state.copy(examLevel = examLevel) }
  }
  fun updateExamInfo(info: String) = intent {
    val examInfoList = state.examInfo.toMutableList()

    if (info in state.examInfo) {
      examInfoList.remove(info)
    } else {
      examInfoList.add(info)
    }
    reduce { state.copy(examInfo = examInfoList.toPersistentList()) }
  }

  fun updateMyExamEvaluationValue(examEvaluationValue: String) = intent {
    reduce { state.copy(examEvaluation = examEvaluationValue) }
  }

  fun showDeleteOrLackPointDialog() = intent {
    if (state.point > MINIMUM_DELETE_POINT) {
      showExamEvaluationDeleteDialog()
    } else {
      showLackPointDialog()
    }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  private fun showExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = true) } }
  fun hideExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = false) } }
  private fun showLackPointDialog() = intent { reduce { state.copy(showLackPointDialog = true) } }
  fun hideLackPointDialog() = intent { reduce { state.copy(showLackPointDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = true) } }
  fun hideExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(MyExamEvaluationEditSideEffect.PopBackStack) }
  private fun showDeleteToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationDeleteToast) }
  private fun showReviseToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationReviseToast) }
}
