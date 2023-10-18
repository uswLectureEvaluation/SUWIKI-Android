package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Token

interface RemoteRefreshTokenDataSource {
    fun reissueRefreshToken(refresh: String): Token
}
