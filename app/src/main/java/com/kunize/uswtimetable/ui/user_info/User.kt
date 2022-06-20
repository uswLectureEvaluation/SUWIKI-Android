package com.kunize.uswtimetable.ui.user_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.Constants.TAG
import com.kunize.uswtimetable.util.TimeTableSelPref
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object User {

    var user: MutableLiveData<LoggedInUser>? = null
        private set

    val isLoggedIn = MutableLiveData(user != null)

    fun login() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = IRetrofit.getInstance().getUserData()
            response.suspendOnSuccess {
                withContext(Dispatchers.Main) {
                    setUser(
                        LoggedInUser(
                            userId = data.userId,
                            point = data.point,
                            writtenEvaluation = data.writtenEvaluation,
                            writtenExam = data.writtenExam,
                            viewExam = data.viewExam,
                            email = data.email
                        )
                    )
                    isLoggedIn.value = true
                }
            }.onError {
                Log.d(TAG, "User - login() Error: ${message()}")
            }.onException {
                Log.d(TAG, "User - login() Exception: $message")
            }
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            isLoggedIn.value = false
            user = null
        }
        TimeTableSelPref.encryptedPrefs.saveAccessToken("")
        TimeTableSelPref.encryptedPrefs.saveRefreshToken("")
        Log.d(TAG, "User - logout Success!")
    }

    private fun setUser(userData: LoggedInUser) {
        if (user != null) {
            user!!.value = userData
        } else {
            user = MutableLiveData(userData)
        }
    }
}