package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Evaluation
import com.suwiki.core.model.Result

interface RemoteLectureMyDataSource {

    suspend fun getLectureMyPosts(page: Int): com.suwiki.core.model.Result<List<com.suwiki.core.model.Evaluation>>
}
