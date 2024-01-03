package com.suwiki.feature.myinfo.myreview.testreview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyTestReviewEditViewModel @Inject constructor() : ContainerHost<MyTestReviewEditState, MyTestReviewEditSideEffect>, ViewModel() {
  override val container: Container<MyTestReviewEditState, MyTestReviewEditSideEffect> = container(MyTestReviewEditState())

  fun getSemester(semester: String) = intent { reduce { state.copy(selectedSemester = semester) } }
  fun getTestType(testType: String) = intent { reduce { state.copy(selectedTestType = testType) } }

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

  fun updateMyClassReviewValue(testReviewValue: String) = intent {
    reduce { state.copy(testReview = testReviewValue) }
  }

  fun showMyTestReviewDeleteDialog() = intent { reduce { state.copy(showDeleteTestReviewDialog = true) } }
  fun hideMyTestReviewDeleteDialog() = intent { reduce { state.copy(showDeleteTestReviewDialog = false) } }
  fun showSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = true) } }
  fun hideSemesterBottomSheet() = intent { reduce { state.copy(showSemesterBottomSheet = false) } }
  fun showTestTypeBottomSheet() = intent { reduce { state.copy(showTestTypeBottomSheet = true) } }
  fun hideTestTypeBottomSheet() = intent { reduce { state.copy(showTestTypeBottomSheet = false) } }
  fun popBackStack() = intent { postSideEffect(MyTestReviewEditSideEffect.PopBackStack) }
}
