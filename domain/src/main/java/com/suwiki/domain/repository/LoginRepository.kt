package com.suwiki.domain.repository

import com.suwiki.domain.model.Result

interface LoginRepository {
  suspend fun login(id: String, pw: String): Result<Unit>
}
