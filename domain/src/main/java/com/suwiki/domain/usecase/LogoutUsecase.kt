package com.suwiki.domain.usecase

import com.suwiki.domain.repository.LogoutRepository
import javax.inject.Inject

class LogoutUsecase @Inject constructor(
    private val logoutRepository: LogoutRepository,
) {
    suspend operator fun invoke() {
        logoutRepository.logout()
    }
}
