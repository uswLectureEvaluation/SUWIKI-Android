package com.suwiki.feature.myinfo.myevaluation.examevaluation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.suwiki.feature.myinfo.myevaluation.lectureevaluation.SHOW_TOAST_LENGTH
import com.suwiki.feature.myinfo.navigation.MyInfoRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyExamEvalutionEditViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
) : ContainerHost<MyExamEvaluationEditState, MyExamEvalutionEditSideEffect>, ViewModel() {
  override val container: Container<MyExamEvaluationEditState, MyExamEvalutionEditSideEffect> =
    container(MyExamEvaluationEditState())

  private val point: Int = savedStateHandle.get<String>(MyInfoRoute.myPoint)!!.toInt()
  private val mutex = Mutex()

  fun getSemester(semester: String) = intent { reduce { state.copy(selectedSemester = semester) } }
  fun getExamType(testType: String) = intent { reduce { state.copy(selectedExamType = testType) } }

  fun setPoint() = intent { reduce { state.copy(point = point) } }
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

  fun showMyExamEvalutionDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvalutionDialog = true) } }
  fun hideMyExamEvalutionDeleteDialog() = intent { reduce { state.copy(showDeleteExamEvalutionDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = true) } }
  fun hideExamTypeBottomSheet() = intent { reduce { state.copy(showExamTypeBottomSheet = false) } }
  fun showMyExamEvalutionToast(msg: String) = intent {
    hideMyExamEvalutionDeleteDialog()
    mutex.withLock {
      reduce { state.copy(showDeleteExamEvalutionToastMessage = msg, showDeleteExamEvalutionToastVisible = true) }
      delay(SHOW_TOAST_LENGTH)
      reduce { state.copy(showDeleteExamEvalutionToastVisible = false) }
      popBackStack()
    }
  }
  fun popBackStack() = intent { postSideEffect(MyExamEvalutionEditSideEffect.PopBackStack) }
}
