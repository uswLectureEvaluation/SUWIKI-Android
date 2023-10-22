package com.suwiki.domain.repository

import com.suwiki.domain.model.Result

interface ResetPasswordRepository {
  suspend fun resetPassword(current: String, new: String): Result<Boolean>
}
