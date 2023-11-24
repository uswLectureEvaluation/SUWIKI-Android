package com.suwiki.remote.lectureevaluation.my.datasource

import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation
import com.suwiki.data.lectureevaluation.my.datasource.RemoteLectureMyDataSource
import com.suwiki.remote.lectureevaluation.my.api.LectureMyApi
import com.suwiki.remote.lectureevaluation.my.response.toModel
import javax.inject.Inject

class RemoteLectureMyDataSourceImpl @Inject constructor(
  private val lectureApi: LectureMyApi,
) : RemoteLectureMyDataSource {
  override suspend fun getMyLectureEvaluationList(page: Int): List<MyLectureEvaluation> {
    return lectureApi.getMyLectureEvaluationList(page).getOrThrow().data.map { it.toModel() }
  }
}
