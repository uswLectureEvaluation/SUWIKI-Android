package com.kunize.uswtimetable.ui.repository

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.login.User.user
import com.kunize.uswtimetable.ui.repository.login.LoginRemoteDataSource
import com.kunize.uswtimetable.util.Result

class LoginRepository(private val remoteDataSource: LoginRemoteDataSource) {

    init {
        user = null
    }

    fun logout() {
        user = null
        remoteDataSource.logout()
    }

    fun login(id: String, pw: String): Result<LoggedInUser> {
        val result = remoteDataSource.login(id, pw)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        user = loggedInUser
    }
}