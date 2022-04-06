package com.kunize.uswtimetable.ui.user_info

import androidx.lifecycle.MutableLiveData
import com.kunize.uswtimetable.TimeTableSelPref
import com.kunize.uswtimetable.dataclass.LoggedInUser
import com.kunize.uswtimetable.retrofit.IRetrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object User {

    var user: MutableLiveData<LoggedInUser>? = null
        private set

    val isLoggedIn = MutableLiveData(user != null)

    private fun setUser(userData: LoggedInUser) {
        if (user != null) {
            user!!.value = userData
        } else {
            user = MutableLiveData(userData)
        }
    }

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
    }
}