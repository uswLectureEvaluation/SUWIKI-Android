package com.suwiki.data.datasource

import com.suwiki.data.network.ApiService
import com.suwiki.data.network.dto.NoticeDetailDto
import com.suwiki.data.network.toDomainResult
import com.suwiki.domain.di.OtherApiService
import com.suwiki.domain.model.Result
import javax.inject.Inject

class NoticeDetailRemoteDataSource @Inject constructor(
    @OtherApiService private val apiService: ApiService,
) : NoticeDetailDataSource {
    override suspend fun getNotice(id: Long): Result<NoticeDetailDto> {
        return apiService.getNotice(id).toDomainResult().map { it.data }
    }
}
