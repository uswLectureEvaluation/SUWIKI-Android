package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalAccessTokenProviderDataSource
import com.suwiki.local.datastore.SecurityPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalAccessTokenProviderDataSourceImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : LocalAccessTokenProviderDataSource {
    override fun getAccessToken(): Flow<String> = securityPreferences.flowAccessToken()
}
