package com.suwiki.domain.lectureevaluation.usecase.exam

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.ExamEditorRepository
import javax.inject.Inject

class UpdateExamEvaluationUseCase @Inject constructor(
  private val examEditorRepository: ExamEditorRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    param.run {
      examEditorRepository.updateExamEvaluation(
        lectureId = lectureId,
        selectedSemester = selectedSemester,
        examInfo = examInfo,
        examType = examType,
        examDifficulty = examDifficulty,
        content = content,
      )
    }
  }

  data class Param(
    val lectureId: Long,
    val selectedSemester: String?,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
  )
}
