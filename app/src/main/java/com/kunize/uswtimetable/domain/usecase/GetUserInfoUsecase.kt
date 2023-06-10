package com.kunize.uswtimetable.domain.usecase

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUsecase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<LoggedInUser?> {
        return userRepository.userInfo
    }

    fun isLoggedIn(): Flow<Boolean> {
        return userRepository.isLoggedIn
    }
}
