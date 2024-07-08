package com.suwiki.presentation.lectureevaluation.viewerrepoter.detail

import com.suwiki.core.model.lectureevaluation.exam.ExamEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluation
import com.suwiki.core.model.lectureevaluation.lecture.LectureEvaluationExtraAverage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class LectureEvaluationDetailState(
  val isLoading: Boolean = false,
  val currentTabPage: Int = 0,
  val lectureEvaluationExtraAverage: LectureEvaluationExtraAverage = LectureEvaluationExtraAverage(),
  val lectureEvaluationList: PersistentList<LectureEvaluation> = persistentListOf(),
  val isLectureEvaluationWritten: Boolean = false,
  val examEvaluationList: PersistentList<ExamEvaluation> = persistentListOf(),
  val needBuyExam: Boolean = false,
  val isExamEvaluationWritten: Boolean = false,
  val showLectureReportDialog: Boolean = false,
  val showExamReportDialog: Boolean = false,
)
sealed interface LectureEvaluationDetailSideEffect {
  data class ShowLackPointToast(val msg: String) : LectureEvaluationDetailSideEffect
  data object PopBackStack : LectureEvaluationDetailSideEffect
  data class HandleException(val throwable: Throwable) : LectureEvaluationDetailSideEffect
  data class NavigateLectureEvaluationEditor(val argument: String) : LectureEvaluationDetailSideEffect
  data class NavigateExamEvaluationEditor(val argument: String) : LectureEvaluationDetailSideEffect
  data object ShowAlreadyWriteToast : LectureEvaluationDetailSideEffect
}
