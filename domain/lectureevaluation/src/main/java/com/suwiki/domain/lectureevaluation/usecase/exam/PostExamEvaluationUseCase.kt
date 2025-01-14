package com.suwiki.domain.lectureevaluation.usecase.exam

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.ExamEditorRepository
import javax.inject.Inject

class PostExamEvaluationUseCase @Inject constructor(
  private val examEditorRepository: ExamEditorRepository,
) {
  suspend operator fun invoke(param: Param): Result<Unit> = runCatchingIgnoreCancelled {
    param.run {
      examEditorRepository.postExamEvaluation(
        lectureId = lectureId,
        lectureName = lectureName,
        professor = professor,
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
    val lectureName: String?,
    val professor: String?,
    val selectedSemester: String?,
    val examInfo: String,
    val examType: String?,
    val examDifficulty: String,
    val content: String,
  )
}
