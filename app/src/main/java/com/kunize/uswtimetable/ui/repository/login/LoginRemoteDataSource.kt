package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.repository.common.BaseRepository
import com.kunize.uswtimetable.util.Result
import java.io.IOException

class LoginRemoteDataSource(private val api: IRetrofit) : LoginDataSource, BaseRepository() {

    override suspend fun login(userId: String, userPassword: String): Result<LoggedInUser> {
        return try {
            // TODO 서버 통신 응답 값 받아서 LoggedInUser 에 저장
            val loggedInUser = LoggedInUser(userId, 120, 3, 2, 6)
            Result.Success(loggedInUser)
        } catch (e: Throwable) {
            // TODO 에러 코드 확인해 아이디, 비밀번호 에러 판별
            Result.Error(IOException("로그인 실패"))
        }
    }

    override suspend fun logout() {

    }
}