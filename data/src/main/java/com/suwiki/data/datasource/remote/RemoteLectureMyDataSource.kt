package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Evaluation

interface RemoteLectureMyDataSource {

    suspend fun getLectureMyPosts(page: Int): List<Evaluation>
}
