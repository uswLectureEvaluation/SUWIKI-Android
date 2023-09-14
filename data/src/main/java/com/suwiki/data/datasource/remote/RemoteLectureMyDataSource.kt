package com.suwiki.data.datasource.remote

import com.suwiki.model.Evaluation
import com.suwiki.model.Result

interface RemoteLectureMyDataSource {

    suspend fun getLectureMyPosts(page: Int): Result<List<Evaluation>>
}
