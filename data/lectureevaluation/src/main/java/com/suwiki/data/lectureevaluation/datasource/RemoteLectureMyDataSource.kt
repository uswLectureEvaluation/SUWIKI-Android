package com.suwiki.data.lectureevaluation.datasource

import com.suwiki.common.model.lectureevaluation.lecture.MyLectureEvaluation

interface RemoteLectureMyDataSource {

  suspend fun getMyLectureEvaluationList(page: Int): List<MyLectureEvaluation>
}
