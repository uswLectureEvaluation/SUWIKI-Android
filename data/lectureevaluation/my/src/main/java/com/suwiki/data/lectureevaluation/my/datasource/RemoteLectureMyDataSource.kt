package com.suwiki.data.lectureevaluation.my.datasource

import com.suwiki.core.model.lectureevaluation.Evaluation

interface RemoteLectureMyDataSource {

  suspend fun getLectureMyPosts(page: Int): List<Evaluation>
}
