package com.suwiki.data.datasource.remote

import com.suwiki.core.model.lectureevaluation.Evaluation

interface RemoteLectureMyDataSource {

    suspend fun getLectureMyPosts(page: Int): List<Evaluation>
}
