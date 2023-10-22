package com.suwiki.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface LocalRefreshTokenProviderDataSource {

  fun getRefreshToken(): Flow<String>
}
