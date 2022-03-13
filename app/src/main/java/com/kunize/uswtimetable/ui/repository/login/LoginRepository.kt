package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.ui.login.User
import com.kunize.uswtimetable.util.Result

class LoginRepository(private val remoteDataSource: LoginRemoteDataSource) {

    fun logout() {
        User.logout()
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
        User.setUser(loggedInUser)
    }
}