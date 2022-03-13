package com.kunize.uswtimetable.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kunize.uswtimetable.ui.repository.login.LoginRemoteDataSource
import com.kunize.uswtimetable.ui.repository.login.LoginRepository

class LoginViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(LoginRemoteDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}