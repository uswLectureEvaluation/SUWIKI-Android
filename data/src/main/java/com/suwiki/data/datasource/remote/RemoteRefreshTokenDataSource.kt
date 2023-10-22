package com.suwiki.data.datasource.remote

import com.suwiki.core.model.user.Token

interface RemoteRefreshTokenDataSource {
  fun reissueRefreshToken(refresh: String): Token
}
