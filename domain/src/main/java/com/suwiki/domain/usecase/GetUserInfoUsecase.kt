package com.suwiki.domain.usecase

import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.repository.UserRepository
import com.suwiki.core.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUsecase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<com.suwiki.core.model.Result<LoggedInUser>> {
        return userRepository.userInfo
    }
}
