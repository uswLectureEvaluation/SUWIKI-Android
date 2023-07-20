package com.kunize.uswtimetable.domain.usecase

import com.kunize.uswtimetable.domain.repository.LogoutRepository
import javax.inject.Inject

class LogoutUsecase @Inject constructor(
    private val logoutRepository: LogoutRepository,
) {
    suspend operator fun invoke() {
        logoutRepository.logout()
    }
}
