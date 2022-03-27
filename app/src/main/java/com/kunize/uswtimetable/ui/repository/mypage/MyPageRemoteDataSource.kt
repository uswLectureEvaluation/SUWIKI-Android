package com.kunize.uswtimetable.ui.repository.mypage

import com.kunize.uswtimetable.retrofit.IRetrofit

class MyPageRemoteDataSource: MyPageDataSource {
    override suspend fun getUserData() = IRetrofit.getInstance().getUserData()
}