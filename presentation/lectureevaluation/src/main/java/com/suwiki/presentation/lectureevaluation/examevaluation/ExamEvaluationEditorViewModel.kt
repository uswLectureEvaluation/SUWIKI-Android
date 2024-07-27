package com.suwiki.presentation.lectureevaluation.examevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.common.model.enums.ExamInfo
import com.suwiki.common.model.enums.ExamLevel
import com.suwiki.common.model.enums.ExamType
import com.suwiki.common.model.lectureevaluation.exam.MyExamEvaluation
import com.suwiki.domain.lectureevaluation.usecase.exam.PostExamEvaluationUseCase
import com.suwiki.domain.lectureevaluation.usecase.exam.UpdateExamEvaluationUseCase
import com.suwiki.presentation.common.ui.extension.decodeFromUri
import com.suwiki.presentation.lectureevaluation.navigation.EvaluationEditorRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.json.Json
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ExamEvaluationEditorViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  val postExamEvaluationUseCase: PostExamEvaluationUseCase,
  val updateExamEvaluationUseCase: UpdateExamEvaluationUseCase,
) : ContainerHost<ExamEvaluationEditorState, ExamEvaluationEditorSideEffect>, ViewModel() {
  override val container: Container<ExamEvaluationEditorState, ExamEvaluationEditorSideEffect> =
    container(ExamEvaluationEditorState())

  private val myExamEvaluation = savedStateHandle.get<String>(EvaluationEditorRoute.examEvaluation)!!
  private val myExamEvaluationItem: MyExamEvaluation = Json.decodeFromUri(myExamEvaluation)
  private val isEditMode = myExamEvaluationItem.content.isNotEmpty()

  suspend fun initData() = intent {
    showLoadingScreen()

    with(myExamEvaluationItem) {
      reduce {
        state.copy(
          semesterList = semesterList.toPersistentList(),
          selectedSemester = selectedSemester,
          selectedExamType = examType,
        )
      }
      examInfo.forEach { info ->
        ExamInfo.entries.forEach {
          if (it.value == info) {
            updateExamInfo(it)
          }
        }
      }
      ExamLevel.entries.forEach {
        if (it.value == examDifficulty) {
          updateExamLevel(it)
        }
      }
      updateMyExamEvaluationValue(content)
    }
    hideLoadingScreen()
  }

  fun postOrUpdateExamEvaluation() = intent {
    if (state.examEvaluation.length < 30) {
      postSideEffect(ExamEvaluationEditorSideEffect.ShowInputMoreTextToast)
      return@intent
    }

    if (state.selectedSemester.isNullOrEmpty()) {
      postSideEffect(ExamEvaluationEditorSideEffect.ShowSelectSemesterToast)
      return@intent
    }

    if (state.selectedExamType.isNullOrEmpty()) {
      postSideEffect(ExamEvaluationEditorSideEffect.ShowSelectExamTypeToast)
      return@intent
    }

    if (isEditMode) {
      updateExamEvaluation()
    } else {
      postExamEvaluation()
    }
  }

  private fun postExamEvaluation() = intent {
    postExamEvaluationUseCase(
      PostExamEvaluationUseCase.Param(
        lectureId = myExamEvaluationItem.id,
        lectureName = myExamEvaluationItem.lectureName,
        professor = myExamEvaluationItem.professor,
        selectedSemester = state.selectedSemester,
        examInfo = state.examInfo.filter { it.isNotBlank() }.joinToString(", "),
        examType = state.selectedExamType,
        examDifficulty = state.examLevel!!.value,
        content = state.examEvaluation,
      ),
    )
      .onSuccess {
        popBackStack()
      }
      .onFailure {
        postSideEffect(ExamEvaluationEditorSideEffect.HandleException(it))
      }
  }

  private fun updateExamEvaluation() = intent {
    updateExamEvaluationUseCase(
      UpdateExamEvaluationUseCase.Param(
        lectureId = myExamEvaluationItem.id,
        selectedSemester = state.selectedSemester,
        examInfo = state.examInfo.filter { it.isNotBlank() }.joinToString(", "),
        examType = state.selectedExamType,
        examDifficulty = state.examLevel!!.value,
        content = state.examEvaluation,
      ),
    )
      .onSuccess {
        popBackStack()
      }
      .onFailure {
        postSideEffect(ExamEvaluationEditorSideEffect.HandleException(it))
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

  fun updateExamLevel(examLevel: ExamLevel) = intent {
    reduce { state.copy(examLevel = examLevel) }
  }

  fun updateExamInfo(info: ExamInfo) = intent {
    val examInfoList = state.examInfo.toMutableList()

    if (info.value in state.examInfo) {
      examInfoList.remove(info.value)
    } else {
      examInfoList.add(info.value)
    }
    reduce { state.copy(examInfo = examInfoList.toPersistentList()) }
  }

  @OptIn(OrbitExperimental::class)
  fun updateMyExamEvaluationValue(examEvaluationValue: String) = blockingIntent {
    reduce { state.copy(examEvaluation = examEvaluationValue) }
  }

  private fun showLoadingScreen() = intent { reduce { state.copy(isLoading = true) } }
  private fun hideLoadingScreen() = intent { reduce { state.copy(isLoading = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = true) } }
  fun hideExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = false) } }

  fun popBackStack() = intent { postSideEffect(ExamEvaluationEditorSideEffect.PopBackStack) }
}
