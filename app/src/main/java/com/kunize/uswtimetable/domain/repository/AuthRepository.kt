package com.kunize.uswtimetable.domain.repository

interface AuthRepository {
    fun requestRefreshToken(token: String): Boolean
}
