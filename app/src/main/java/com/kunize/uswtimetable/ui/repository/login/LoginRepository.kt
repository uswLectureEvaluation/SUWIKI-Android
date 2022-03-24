package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.dataclass.LoginIdPassword
import com.kunize.uswtimetable.dataclass.Token
import com.kunize.uswtimetable.retrofit.ApiClient
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.ui.login.User
import com.kunize.uswtimetable.ui.repository.common.BaseRepository

class LoginRepository: BaseRepository() {

    private val retrofit: IRetrofit by lazy { ApiClient.getClientWithNoToken().create(IRetrofit::class.java) }

    fun logout() {
        User.logout()
    }

    suspend fun login(id: String, pw: String): Token {

        val result = safeApiCall(
            call = { retrofit.login(LoginIdPassword(id, pw)).await() },
            error = "Login error"
        )
        if (result != null) {
            // TODO 내 정보 API 호출해서 정보 받아오기
            setLoggedInUser(LoggedInUser(id, 0, 0, 0, 0))
        }
        return result?:Token("", "")
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        User.setUser(loggedInUser)
    }
}