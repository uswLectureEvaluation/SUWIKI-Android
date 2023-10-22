package com.suwiki.domain.repository

import com.suwiki.domain.model.Result

interface QuitRepository {
  suspend fun quit(id: String, password: String): Result<Boolean>
}
