package com.suwiki.domain.lectureevaluation.editor.usecase.exam

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.editor.repository.ExamEditorRepository
import javax.inject.Inject

class DeleteExamEvaluationUseCase @Inject constructor(
  private val examEditorRepository: ExamEditorRepository,
) {
  suspend operator fun invoke(id: Long): Result<Unit> = runCatchingIgnoreCancelled {
    examEditorRepository.deleteExamEvaluation(
      id = id,
    )
  }
}
