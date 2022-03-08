package com.kunize.uswtimetable.login

import com.kunize.uswtimetable.dataclass.LoggedInUser

object User {
    var user: LoggedInUser? = null
    val isLoggedIn: Boolean
        get() = user != null

    fun getUserName(): String {
        if (isLoggedIn) {
            return user!!.userId
        } else {
            throw IllegalAccessException("not logged in yet")
        }
    }
}