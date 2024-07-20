package com.suwiki.domain.lectureevaluation.usecase.lecture

import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.domain.common.runCatchingIgnoreCancelled
import com.suwiki.domain.lectureevaluation.repository.LectureMyRepository
import javax.inject.Inject

class GetMyLectureEvaluationListUseCase @Inject constructor(
  private val lectureMyRepository: LectureMyRepository,
) {
  suspend operator fun invoke(page: Int): Result<List<MyLectureEvaluation>> = runCatchingIgnoreCancelled {
    lectureMyRepository.getMyLectureEvaluationList(page = page)
  }
}
