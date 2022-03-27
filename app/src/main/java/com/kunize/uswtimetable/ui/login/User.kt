package com.kunize.uswtimetable.ui.login

import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.dataclass.LoggedInUser

object User {

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    fun setUser(userData: LoggedInUser) {
        user = userData
    }

    fun logout() {
        user = null
        TimeTableSelPref.encryptedPrefs.saveAccessToken("")
        TimeTableSelPref.encryptedPrefs.saveRefreshToken("")
    }

    fun getUserName(): String {
        if (isLoggedIn) {
            return user!!.userId
        } else {
            throw IllegalAccessException("not logged in yet")
        }
    }

    fun getPoint(): Int {
        if (isLoggedIn) {
            return user!!.point
        } else {
            throw IllegalAccessException("not logged in yet")
        }
    }

    fun getWrittenLecture(): Int {
        if (isLoggedIn) {
            return user!!.writtenLecture
        } else {
            throw IllegalAccessException("not logged in yet")
        }
    }

    fun getWrittenExam(): Int {
        if (isLoggedIn) {
            return user!!.writtenExam
        } else {
            throw IllegalAccessException("not logged in yet")
        }
    }
}