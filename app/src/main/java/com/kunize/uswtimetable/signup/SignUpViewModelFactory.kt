package com.kunize.uswtimetable.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SignUpViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(
                certificateEmail = CertificateEmail()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}