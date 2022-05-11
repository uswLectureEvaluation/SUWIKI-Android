package com.kunize.uswtimetable.ui.user_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.util.TimeTableSelPref
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.retrofit.IRetrofit
import com.kunize.uswtimetable.util.Constants.TAG
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
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    val info = response.body()!!
                    setUser(
                        LoggedInUser(
                            userId = info.userId,
                            point = info.point,
                            writtenLecture = info.writtenEvaluation,
                            writtenExam = info.writtenExam,
                            viewExam = info.viewExam,
                            email = info.email
                        )
                    )
                    isLoggedIn.value = true
                }
                Log.d(TAG, "User - login Success!")
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