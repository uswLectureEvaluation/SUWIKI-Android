package com.suwiki.domain.usecase

import com.suwiki.domain.model.LoggedInUser
import com.suwiki.domain.repository.UserRepository
import com.suwiki.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUsecase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<Result<LoggedInUser>> {
        return userRepository.userInfo
    }
}
