package com.kunize.uswtimetable.ui.repository.signup

interface SignUpDataSource {
    fun checkId(id: String): Boolean
    fun checkEmail(email: String): Boolean
    fun signup(id: String, pw: String, email: String)
}