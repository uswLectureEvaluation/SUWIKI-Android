package com.suwiki.data.user.datasource

import com.suwiki.core.model.user.User
import kotlinx.coroutines.flow.Flow

interface LocalUserProviderDataSource {
  val user: Flow<User>
}
