package com.suwiki.data.datasource.local

import com.suwiki.core.model.user.Token

interface LocalTokenStorageDataSource {

  suspend fun saveToken(token: Token)

  suspend fun clearToken()
}
