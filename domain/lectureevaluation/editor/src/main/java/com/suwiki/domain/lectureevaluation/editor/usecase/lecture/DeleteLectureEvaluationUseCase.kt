package com.suwiki.domain.lectureevaluation.editor.usecase.lecture

import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.editor.repository.LectureEditorRepository
import javax.inject.Inject

class DeleteLectureEvaluationUseCase @Inject constructor(
  private val lectureEditorRepository: LectureEditorRepository,
) {
  suspend operator fun invoke(id: Long): Result<Unit> = runCatchingIgnoreCancelled {
    lectureEditorRepository.deleteLectureEvaluation(
      id = id,
    )
  }
}
