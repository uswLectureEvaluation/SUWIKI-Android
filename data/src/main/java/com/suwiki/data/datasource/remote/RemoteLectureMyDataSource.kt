package com.suwiki.data.datasource.remote

import com.suwiki.core.model.lectureevaluation.lecture.MyLectureEvaluation

interface RemoteLectureMyDataSource {

  suspend fun getLectureMyPosts(page: Int): List<MyLectureEvaluation>
}
