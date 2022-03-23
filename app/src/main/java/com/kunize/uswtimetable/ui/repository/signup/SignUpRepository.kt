package com.kunize.uswtimetable.ui.repository.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.OverlapCheckDto
import com.kunize.uswtimetable.dataclass.SuccessCheckDto
import com.kunize.uswtimetable.ui.signup.SignUpViewModel.SignUpState
import com.kunize.uswtimetable.util.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository(private val dataSource: SignUpRemoteDataSource) {
    private val idCheckResult = MutableLiveData<SignUpState>()
    private val emailCheckResult = MutableLiveData<SignUpState>()
    private val signUpResult = MutableLiveData<SignUpState>()

    fun checkId(id: String) {
        dataSource.checkId(id).enqueue(object : Callback<OverlapCheckDto> {
            override fun onResponse(call: Call<OverlapCheckDto>, response: Response<OverlapCheckDto>) {
                idCheckResult.value = if (response.isSuccessful && response.body() != null) {
                    Log.d(TAG, "SignUpRepository - Check Id onResponse() success called / ${response.code()}: msg->${response.message()} body->${response.body()}")
                    SignUpState.SUCCESS
                } else {
                    Log.d(TAG, "SignUpRepository - Check Id onResponse() called / ${response.code()}: msg->${response.message()} body->${response.body()}")
                    SignUpState.INVALID_ID
                }
            }

            override fun onFailure(call: Call<OverlapCheckDto>, t: Throwable) {
                Log.d(TAG, "SignUpRepository - Check Id onFailure() called / ${t.message}")
                idCheckResult.value = SignUpState.ERROR
            }
        })
    }

    fun checkEmail(email: String) {
        dataSource.checkEmail(email).enqueue(object : Callback<OverlapCheckDto> {
            override fun onResponse(call: Call<OverlapCheckDto>, response: Response<OverlapCheckDto>) {
                emailCheckResult.value = if (response.isSuccessful && response.body() != null) {
                    SignUpState.SUCCESS
                } else {
                    Log.d(TAG, "SignUpRepository - Check Email onResponse() called / ${response.code()}: ${response.body()}")
                    SignUpState.INVALID_EMAIL
                }
            }

            override fun onFailure(call: Call<OverlapCheckDto>, t: Throwable) {
                Log.d(TAG, "SignUpRepository - Check Email onFailure() called / ${t.message}")
                emailCheckResult.value = SignUpState.ERROR
            }
        })
    }

    fun signUp(id: String, pw: String, email: String) {
        when {
            idCheckResult.value == SignUpState.INVALID_ID -> signUpResult.value = SignUpState.INVALID_ID
            emailCheckResult.value == SignUpState.INVALID_EMAIL -> signUpResult.value = SignUpState.INVALID_EMAIL
            idCheckResult.value == SignUpState.SUCCESS && emailCheckResult.value == SignUpState.SUCCESS -> {
                dataSource.signup(id, pw, email).enqueue(object: Callback<SuccessCheckDto> {
                    override fun onResponse(
                        call: Call<SuccessCheckDto>,
                        response: Response<SuccessCheckDto>
                    ) {
                        signUpResult.value = if (response.isSuccessful && response.body() != null) {
                            SignUpState.SUCCESS
                        } else {
                            SignUpState.ERROR
                        }
                    }

                    override fun onFailure(call: Call<SuccessCheckDto>, t: Throwable) {
                        signUpResult.value = SignUpState.ERROR
                    }
                })
            }
            else -> signUpResult.value = SignUpState.ERROR
        }
    }

    fun getIdCheckResult() = idCheckResult
    fun getEmailCheckResult() = emailCheckResult
    fun getSignUpResult() = signUpResult

    fun resetIdResult() {
        idCheckResult.value = SignUpState.DEFAULT
    }
    fun resetEmailResult() {
        emailCheckResult.value = SignUpState.DEFAULT
    }
}