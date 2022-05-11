package com.kunize.uswtimetable.repository.my_post

import com.kunize.uswtimetable.retrofit.IRetrofit

class MyExamInfoRemoteDataSource: MyExamInfoDataSource {
    override suspend fun getMyExamInfoData(page: Int) = IRetrofit.getInstance().getExamPosts(page)
}