package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.ui.user_info.User

class MyPageViewModel : ViewModel() {
    var user = MutableLiveData(User)

    fun refresh() {
        User.login()
        user.postValue(User)
    }
}