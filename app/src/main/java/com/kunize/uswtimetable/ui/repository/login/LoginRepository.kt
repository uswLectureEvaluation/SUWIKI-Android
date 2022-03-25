package com.kunize.uswtimetable.ui.repository.login

import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.ui.login.User

class LoginRepository(private val dataSource: LoginRemoteDataSource) {

    fun logout() {
        User.logout()
    }

    suspend fun login(id: String, pw: String) = dataSource.login(id, pw)

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        User.setUser(loggedInUser)
    }
}