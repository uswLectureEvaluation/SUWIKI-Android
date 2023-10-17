package com.suwiki.data.datasource.remote

import com.suwiki.core.model.Result
import com.suwiki.core.model.Suspension
import com.suwiki.core.model.Token
import com.suwiki.core.model.User

interface RemoteRefreshTokenDataSource {
    fun reissueRefreshToken(refresh: String): Result<Token>
}
