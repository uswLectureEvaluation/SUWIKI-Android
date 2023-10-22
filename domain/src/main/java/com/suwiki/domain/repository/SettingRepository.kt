package com.suwiki.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
  val isRememberLogin: Flow<Boolean>
  suspend fun setRememberLogin(remember: Boolean)
}
