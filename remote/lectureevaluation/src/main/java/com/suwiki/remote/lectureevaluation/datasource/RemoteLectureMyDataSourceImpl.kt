package com.suwiki.remote.lectureevaluation.datasource

import com.suwiki.common.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.data.lectureevaluation.datasource.RemoteLectureMyDataSource
import com.suwiki.remote.lectureevaluation.api.LectureMyApi
import com.suwiki.remote.lectureevaluation.response.toModel
import javax.inject.Inject

class RemoteLectureMyDataSourceImpl @Inject constructor(
  private val lectureApi: LectureMyApi,
) : RemoteLectureMyDataSource {
  override suspend fun getMyLectureEvaluationList(page: Int): List<MyLectureEvaluation> {
    return lectureApi.getMyLectureEvaluationList(page).getOrThrow().data.map { it.toModel() }
  }
}
