package com.suwiki.data.datasource.remote

import com.suwiki.model.Result
import com.suwiki.model.Suspension
import com.suwiki.model.Token
import com.suwiki.model.User

interface RemoteRefreshTokenDataSource {
    fun reissueRefreshToken(refresh: String): Result<Token>
}
