package com.suwiki.data.lectureevaluation.my.datasource

import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation

interface RemoteLectureMyDataSource {

  suspend fun getMyLectureEvaluationList(page: Int): List<MyLectureEvaluation>
}
