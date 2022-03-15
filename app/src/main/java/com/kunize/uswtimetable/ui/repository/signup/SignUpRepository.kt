package com.kunize.uswtimetable.ui.repository.signup

import com.kunize.uswtimetable.ui.signup.SignUpViewModel.SignUpState

class SignUpRepository(private val dataSource: SignUpRemoteDataSource) {

    fun checkId(id: String): SignUpState = if (dataSource.checkId(id)) SignUpState.INVALID_ID else SignUpState.SUCCESS

    fun checkEmail(email: String): SignUpState = if(dataSource.checkEmail(email)) SignUpState.INVALID_EMAIL else SignUpState.SUCCESS

    fun signUp(id: String, pw: String, email: String): SignUpState {

        if (dataSource.checkId(id)) return SignUpState.INVALID_ID
        if (dataSource.checkEmail(email)) return SignUpState.INVALID_EMAIL

        dataSource.signup(id, pw, email)
        return SignUpState.SUCCESS
    }
}