package com.suwiki.data.lectureevaluation.repository

import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureMyDataSource
import com.suwiki.domain.lectureevaluation.repository.LectureMyRepository
import javax.inject.Inject

class LectureMyRepositoryImpl @Inject constructor(
  private val remoteLectureMyDataSource: RemoteLectureMyDataSource,
) : LectureMyRepository {
  override suspend fun getMyLectureEvaluationList(page: Int): List<MyLectureEvaluation> {
    return remoteLectureMyDataSource.getMyLectureEvaluationList(page = page)
  }
}
