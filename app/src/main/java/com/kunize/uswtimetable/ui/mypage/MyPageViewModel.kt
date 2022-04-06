package com.kunize.uswtimetable.ui.mypage

import androidx.lifecycle.ViewModel
import com.kunize.uswtimetable.ui.repository.mypage.MyPageRepository
import com.kunize.uswtimetable.ui.user_info.User

class MyPageViewModel(private val repository: MyPageRepository) : ViewModel() {
    var user: User = User

    fun refresh() {
        User.login()
        user = User
    }
}