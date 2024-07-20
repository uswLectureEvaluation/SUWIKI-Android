package com.suwiki.domain.lectureevaluation.repository

import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation

interface LectureMyRepository {

  suspend fun getMyLectureEvaluationList(page: Int): List<MyLectureEvaluation>
}
