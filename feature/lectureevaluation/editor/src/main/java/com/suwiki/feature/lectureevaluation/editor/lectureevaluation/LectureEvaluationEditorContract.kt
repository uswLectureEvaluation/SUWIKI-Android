package com.suwiki.feature.lectureevaluation.editor.lectureevaluation

import com.suwiki.core.model.enums.GradeLevel
import com.suwiki.core.model.enums.HomeworkLevel
import com.suwiki.core.model.enums.TeamLevel
import com.suwiki.feature.lectureevaluation.editor.examevaluation.ExamEvaluationEditorSideEffect
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationEditorState(
  val isLoading: Boolean = false,
  val point: Int = 0,
  val semesterList: PersistentList<String> = persistentListOf(""),
  val selectedSemester: String = "",
  val selectedSemesterPosition: Int? = null,
  val totalAvg: Float = 0f,
  val honeyRating: Float = 0f,
  val learningRating: Float = 0f,
  val satisfactionRating: Float = 0f,
  val gradeLevel: GradeLevel? = null,
  val homeworkLevel: HomeworkLevel? = null,
  val teamLevel: TeamLevel? = null,
  val lectureEvaluation: String = "",
  val showSemesterBottomSheet: Boolean = false,
) {
  val buttonEnabled = gradeLevel != null &&
    homeworkLevel != null &&
    teamLevel != null &&
    lectureEvaluation.isNotEmpty()
}

sealed interface LectureEvaluationEditorSideEffect {
  data object ShowInputMoreTextToast : LectureEvaluationEditorSideEffect
  data object ShowSelectSemesterToast : LectureEvaluationEditorSideEffect
  data object PopBackStack : LectureEvaluationEditorSideEffect
  data object ShowLectureEvaluationDeleteToast : LectureEvaluationEditorSideEffect
  data class HandleException(val throwable: Throwable) : LectureEvaluationEditorSideEffect
}
