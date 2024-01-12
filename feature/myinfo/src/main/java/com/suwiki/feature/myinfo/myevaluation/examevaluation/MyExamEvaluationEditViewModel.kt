package com.suwiki.feature.myinfo.myevaluation.examevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suwiki.core.model.enums.ExamType
import com.suwiki.core.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.core.model.user.User
import com.suwiki.core.ui.extension.toInt
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.DeleteExamEvaluationUseCase
import com.suwiki.domain.lectureevaluation.editor.usecase.exam.UpdateExamEvaluationUseCase
import com.suwiki.domain.user.usecase.GetUserInfoUseCase
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
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

  private var selectedExamInfo: MutableList<String> = mutableListOf("")

  suspend fun setInitData() = intent {
    with(myExamEvaluationItem) {
      showLoadingScreen()
      getUserInfoUseCase().collect(::setPoint)
      reduce { state.copy(semesterList = semesterList!!.split(", ").toPersistentList()) }
      reduce { state.copy(selectedSemester = selectedSemester) }
      reduce { state.copy(selectedExamType = examType) }
      updateExamLevel(examDifficulty)
      examInfo.forEach { info ->
        updateExamInfo(info)
      }
      updateMyExamEvaluationValue(content)
      hideLoadingScreen()
    }
  }

  fun clickReviseButton() = intent {
    viewModelScope.launch {
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

  fun clickSemesterItem(selectedPosition: Int) = intent {
    reduce { state.copy(selectedSemester = state.semesterList[selectedPosition]) }
    hideSemesterBottomSheet()
  }

  fun clickExamTypeItem(selectedPosition: Int) = intent {
    ExamType.entries.forEach { examType ->
      if (examType.toInt() == selectedPosition) {
        reduce { state.copy(selectedExamType = examType.value) }
      }
    }
    hideExamTypeBottomSheet()
  }

  private fun setPoint(user: User) = intent { reduce { state.copy(point = user.point) } }
  fun updateExamLevel(examLevel: String) = intent {
    reduce { state.copy(examLevel = examLevel) }
  }
  fun updateExamInfo(info: String) = intent {
    if (info in selectedExamInfo) {
      selectedExamInfo.remove(info)
    } else {
      selectedExamInfo.add(info)
    }
    reduce { state.copy(examInfo = selectedExamInfo.toPersistentList()) }
  }

  fun updateMyExamEvaluationValue(examEvaluationValue: String) = intent {
    reduce { state.copy(examEvaluation = examEvaluationValue) }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun showMyExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = true) } }
  fun hideMyExamEvaluationDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvaluationDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = true) } }
  fun hideExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(MyExamEvaluationEditSideEffect.PopBackStack) }
  private fun showDeleteToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationDeleteToast) }
  private fun showReviseToast() = intent { postSideEffect(MyExamEvaluationEditSideEffect.ShowMyExamEvaluationReviseToast) }
}
