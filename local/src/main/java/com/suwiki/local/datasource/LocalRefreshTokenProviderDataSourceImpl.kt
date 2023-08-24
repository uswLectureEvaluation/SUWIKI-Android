package com.suwiki.local.datasource

import com.suwiki.data.datasource.local.LocalRefreshTokenProviderDataSource
import com.suwiki.local.datastore.SecurityPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRefreshTokenProviderDataSourceImpl @Inject constructor(
    private val securityPreferences: SecurityPreferences,
) : LocalRefreshTokenProviderDataSource {

    override fun getRefreshToken(): Flow<String> = securityPreferences.flowRefreshToken()
}
