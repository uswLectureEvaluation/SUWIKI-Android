package com.kunize.uswtimetable.dataclass

import java.io.IOException

class LoginDataSource {

    fun login(id: String, pw: String): Result<LoggedInUser> {
        return try {
            val dumpUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "mangbaam")
            Result.Success(dumpUser)
        } catch (e: Throwable) {
            Result.Error(IOException("로그인 중 에러 발생", e))
        }
    }

    fun logout() {
        // TODO: 로그아웃 구현 해야 함
    }
}