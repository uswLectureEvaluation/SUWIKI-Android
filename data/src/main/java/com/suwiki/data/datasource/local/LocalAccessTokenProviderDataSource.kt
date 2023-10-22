package com.suwiki.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface LocalAccessTokenProviderDataSource {

  fun getAccessToken(): Flow<String>
}
